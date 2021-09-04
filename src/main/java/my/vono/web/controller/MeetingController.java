package my.vono.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import my.vono.web.config.auth.CustomUserDetails;
import my.vono.web.excelUtile.MeetingLogVO;
import my.vono.web.gspeech.InfiniteStreamRecognize;
import my.vono.web.model.meeting.MeetingDetailDto;
import my.vono.web.model.meeting.MeetingDto;
import my.vono.web.model.response.DefaultResponseDto;
import my.vono.web.service.MeetingService;
import my.vono.web.service.WasteBasketService;

@Controller
@RequiredArgsConstructor
public class MeetingController {

	private final MeetingService meetingService;
	private final WasteBasketService wasteBasketService;

	@RequestMapping("newMeeting")
	public String newMeeting() {
		System.out.println("newMeeting호출");
		return "meeting/newMeeting";
	}

	@RequestMapping("startMeeting")
	public String startMeeting(HttpServletRequest request, Model model) {
		System.out.println("startMeeting호출");
		String name = request.getParameter("mt_name");
		String date = request.getParameter("mt_date");
		String participant = request.getParameter("mt_participant");
		String content = request.getParameter("mt_content");
		model.addAttribute("name", name).addAttribute("date", date).addAttribute("participant", participant)
				.addAttribute("content", content);
		System.out.println(model);
		return "meeting/startMeeting";
	}

	@ResponseBody
	@PostMapping("startRecording")
	public String startRecording(HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println("startRecording호출");
		InfiniteStreamRecognize.StreamStart(model, "");

		return "Recording";// 녹음중=Recording

	}

	@ResponseBody
	@GetMapping("infiniteReq")
	public MeetingLogVO infiniteReq() {
		// System.out.println("infiniteReq호출");
		if (InfiniteStreamRecognize.result != null) {
			if (InfiniteStreamRecognize.result.getIsFinal()) {
				String str = InfiniteStreamRecognize.res;
				String[] strlist = str.split("/");
				MeetingLogVO meetingLogVO = new MeetingLogVO();
				meetingLogVO.setTime(strlist[0]);
				meetingLogVO.setSpeaker(strlist[1]);
				meetingLogVO.setContent(strlist[2]);
				return meetingLogVO;

			} else {
				return null;
			}
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("pauseRecording")
	public String pauseRecording(Model model) {
		System.out.println("pauseMeeting호출");
		InfiniteStreamRecognize.StreamPause(model, "");
		return "녹화중지";
	}

	@ResponseBody
	@RequestMapping("restartRecording")
	public String restartRecording(Model model) {
		System.out.println("restartMeeting호출");
		InfiniteStreamRecognize.StreamRestart(model, "");
		return "녹화재시작";
	}
	
	//질문!! DB에 Meeting이 올라가는 구문이 어느것인가?
	@RequestMapping("endRecording")
	public String endRecording(Model model, @RequestParam("inputHidden") String inputHidden,
			@RequestParam("mt_name") String mt_name, @RequestParam("mt_date") String mt_date,
			@RequestParam("mt_participant") String mt_participant, @RequestParam("mt_content") String mt_content,
			@RequestParam("mt_href") String mt_href,
			@AuthenticationPrincipal CustomUserDetails custom) {
		System.out.println(inputHidden);
		MeetingDto meetingDto = new MeetingDto();
		meetingDto.setName(mt_name);
		meetingDto.setParticipant(mt_participant);
		meetingDto.setContent(mt_content);
		meetingDto.setRecFileUrl(mt_href);
		JSONParser jsonParser = new JSONParser();
		// JSON데이터를 넣어 JSON Object 로 만들어 준다.
		JSONObject jsonObject;
		try {
			jsonObject = (JSONObject) jsonParser.parse(inputHidden);

			JSONArray array = (JSONArray) jsonObject.get("list");
			List<String> memoStr = new ArrayList<>();

			for (int i = 0; i < array.size(); i++) {
				// JSONArray 형태의 값을 가져와 JSONObject 로 풀어준다.
				JSONObject obj = (JSONObject) array.get(i);

				memoStr.add(String.valueOf(obj.get("memo")));
			}

			String filename = InfiniteStreamRecognize.StreamEnd(model, memoStr, "");
			meetingDto.setRecToTextUrl(filename);
			meetingService.createMeeting(meetingDto, custom.getMember().getId());
			System.out.println("meetingDto : "+meetingDto.getRecFileUrl());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "meeting/newMeeting"; // 목적지 바꾸기
		}

		return "meeting/newMeeting";

	}

	// 회의록 작성
//	@ResponseBody
//	@PostMapping("/meeting/write")
//	public ResponseEntity<?> meetingWrite(@RequestBody MeetingDto meetingDto,
//			@AuthenticationPrincipal CustomUserDetails custom) {
//		try {
//			meetingService.createMeeting(meetingDto, custom.getMember().getId());
//			return new ResponseEntity<>(new DefaultResponseDto<>(true, "회의록 생성에 성공하였습니다.", null), HttpStatus.CREATED);
//		} catch (Exception e) {
//			return new ResponseEntity<>(new DefaultResponseDto<>(false, "회의록 생성에 실패하였습니다.", null), HttpStatus.OK);
//		}
//	}

	// 회의록 휴지통
	@ResponseBody
	@PostMapping("/meeting/trash")
	public ResponseEntity<?> trashMeeting(@RequestBody MeetingDto meetingDto) {
		try {
			meetingService.deleteMeeting(meetingDto.getId());
			return new ResponseEntity<>(new DefaultResponseDto<>(true, "회의록을 삭제 하였습니다.", null), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new DefaultResponseDto<>(false, "회의록 삭제에 실패하였습니다.", null), HttpStatus.OK);
		}
	}
//   //회의록 조회
//   @ResponseBody
//   @GetMapping("/meeting")
//   public ResponseEntity<?>detailMeeting(@RequestParam("meetingId") Long meetingId){
//      try {
//         
//         return new ResponseEntity<>(new DefaultResponseDto<>(true, "회의록 조회 성공", meetingService.detailMeeting(meetingId)), HttpStatus.OK);
//      } catch (Exception e) {
//         return new ResponseEntity<>(new DefaultResponseDto<>(false, "회의록이 존재 하지 않습니다.", null), HttpStatus.OK);
//      }
//   }

//	@ResponseBody
//	@PostMapping("/meeting/move")
//	public ResponseEntity<?> MoveMeeting(@RequestBody MeetingDto meetingDto,
//			@AuthenticationPrincipal CustomUserDetails custom) {
//		try {
//			System.out.println(meetingDto.getFolderName());
//			meetingService.moveMeeting(meetingDto.getFolderName(), meetingDto.getId(), custom);
//			return new ResponseEntity<>(new DefaultResponseDto<>(true, "회의록 이동 성공", null), HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>(new DefaultResponseDto<>(false, "회의록 이동 실패", null), HttpStatus.OK);
//		}
//	}

//======================================   

	// 휴지통 폴더 목록 조회
	@ResponseBody
	@GetMapping("/trash")
	public ResponseEntity<?> trash(@RequestParam("id") Long id) {
		try {

			return new ResponseEntity<>(
					new DefaultResponseDto<>(true, "휴지통 조회 성공", wasteBasketService.findWasteBasket(id)), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new DefaultResponseDto<>(false, "휴지통 조회 실패", null), HttpStatus.OK);
		}
	}

	// 폴더 복구
	@ResponseBody
	@PostMapping("/folder/recover")
	public ResponseEntity<?> recoverFolder(@RequestParam("id") List<Long> id) {
		try {
			wasteBasketService.recoverFolder(id);
			return new ResponseEntity<>(new DefaultResponseDto<>(true, "폴더 복구 성공", null), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new DefaultResponseDto<>(false, "폴더 복구 실패", null), HttpStatus.OK);
		}
	}

	// 회의록 복구
	@ResponseBody
	@PostMapping("/meeting/recover")
	public ResponseEntity<?> recoverMeeting(@RequestParam("id") List<Long> id) {
		try {
			wasteBasketService.recoverMeeting(id);
			return new ResponseEntity<>(new DefaultResponseDto<>(true, "회의록 복구 성공", null), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new DefaultResponseDto<>(false, "회의록 복구 실패", null), HttpStatus.OK);
		}
	}

	// 폴더 영구삭제
	@ResponseBody
	@PostMapping("/folder/delete")
	public ResponseEntity<?> deleteFolder(@RequestParam("id") List<Long> id) {
		try {
			wasteBasketService.permanentlyDeleteFolder(id);
			return new ResponseEntity<>(new DefaultResponseDto<>(true, "폴더 영구 삭제 성공", null), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new DefaultResponseDto<>(false, "폴더 영구 삭제 실패", null), HttpStatus.OK);
		}
	}

	@ResponseBody
	@PostMapping("/meeting/delete")
	public ResponseEntity<?> deleteMeeting(@RequestParam("id") List<Long> id) {
		try {
			wasteBasketService.permanentlyDeleteFolder(id);
			return new ResponseEntity<>(new DefaultResponseDto<>(true, "회의록 영구 삭제 성공", null), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new DefaultResponseDto<>(false, "회의록 영구 삭제 실패", null), HttpStatus.OK);
		}
	}

	@GetMapping("meeting")

	public String detailMeeting(@RequestParam("id") Long meetingId, Model model) {
		try {

			MeetingDto meetingDto = meetingService.detailMeeting(meetingId);
			System.out.println("=============================" + meetingDto.getRecToTextUrl());
			System.out.println("=============================" + meetingDto.getRecToTextUrl());

			MeetingDetailDto meetLog = meetingService.meetingReader(meetingDto.getRecToTextUrl());
//   
			model.addAttribute("meetingLog", meetLog.getMList());
			model.addAttribute("memo", meetLog.getMemo());
			System.out.println(meetLog.getMList());
			model.addAttribute("meeting", meetingDto);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "meeting/detailMeeting";
	}

	@ResponseBody
	@PostMapping("meeting/update")
	public String updateMeeting(@RequestParam String list, @RequestParam String memo,
			@RequestParam("meetingId") Long meetingId) {
		System.out.println("==============");
		System.out.println(memo);
		System.out.println(list);

		JSONParser jsonParser = new JSONParser();
		String url = meetingService.detailMeeting(meetingId).getRecToTextUrl();

		// JSON데이터를 넣어 JSON Object 로 만들어 준다.
		JSONObject jsonObject;
		try {
			jsonObject = (JSONObject) jsonParser.parse(list);

			JSONArray array = (JSONArray) jsonObject.get("list");
			List<MeetingLogVO> meetingLogVOs = new ArrayList<>();
			List<String> memoList = new ArrayList<>();

			for (int i = 0; i < array.size(); i++) {
				MeetingLogVO m = new MeetingLogVO();

				// JSONArray 형태의 값을 가져와 JSONObject 로 풀어준다.
				JSONObject obj = (JSONObject) array.get(i);

				m.setSpeaker(String.valueOf(obj.get("speaker")));
				m.setContent(String.valueOf(obj.get("content")));
				m.setTime(String.valueOf(obj.get("time")));

				meetingLogVOs.add(m);

			}

			jsonObject = (JSONObject) jsonParser.parse(memo);

			JSONArray array2 = (JSONArray) jsonObject.get("memo");

			for (int i = 0; i < array2.size(); i++) {

				// JSONArray 형태의 값을 가져와 JSONObject 로 풀어준다.
				JSONObject obj = (JSONObject) array2.get(i);

				if (!obj.get("memo").equals("") && obj.get("memo") != null)
					memoList.add(String.valueOf(obj.get("memo")));

			}
			meetingService.meetingWrite(meetingLogVOs, memoList, url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "수정에 실패 하였습니다.";
		}

		return "수정에 성공 하였습니다.";
	}
    @ResponseBody
	@GetMapping("/download")
	public void download(HttpServletResponse response,@RequestParam("url")String url) {
		// 직접 파일 정보를 변수에 저장해 놨지만, 이 부분이 db에서 읽어왔다고 가정한다.
    	
    	
		String fileName = url;
		String saveFileName = "c:/vono/"+url;
//		String contentType = "image/jpg";
		File file = new File(saveFileName);
		long fileLength = file.length();
		// 파일의 크기와 같지 않을 경우 프로그램이 멈추지 않고 계속 실행되거나, 잘못된 정보가 다운로드 될 수 있다.

		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
//		response.setHeader("Content-Type", contentType);
		response.setHeader("Content-Length", "" + fileLength);
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");

		
		
		try(
                FileInputStream fis = new FileInputStream(saveFileName);
                OutputStream out = response.getOutputStream();
        ){
                int readCount = 0;
                byte[] buffer = new byte[1024];
            while((readCount = fis.read(buffer)) != -1){
                    out.write(buffer,0,readCount);
            }
        }catch(Exception ex){
            throw new RuntimeException("file Save Error");
        }

	
	}

	@RequestMapping("getmtingLogList")
	public String getAllFolders(Model m, @RequestParam(required = false, defaultValue = "") Long id) {
		m.addAttribute("listName", meetingService.folderMeetingList(id));
		return "folder/meetingList";
	}

	@RequestMapping("istrashfile")
	public String istrashfile(@RequestParam(required = false, defaultValue = "id")Long id) {
		meetingService.deleteMeeting(id);
		return "folder/meetingList";
	}
//	@RequestMapping("istrashfile")
	//public String istrashfile(@ModelAttribute("getfolderID") Long id) {
//		meetingService.deleteMeeting(id);
//		return "folder/meetingList";
//	}

    @RequestMapping("movefile")
   	public String movefile(@RequestParam(required = false , value = "id") Long folderID ,@RequestParam(required = false, value = "meetingID") Long id ) {
       	meetingService.moveMeeting(id, folderID);
   		return "redirect:/folderList";
   	}
    
    @RequestMapping("getMeetingSimple")
    public String getMeetingSimple(@RequestParam("id")Long meetingId, Model m) {
    	MeetingDto dto = meetingService.detailMeeting(meetingId);
    	System.out.println(dto.getName());
    	System.out.println(dto.getCreate_date());
    	System.out.println(dto.getId());
    	System.out.println(dto.getParticipant());
    	m.addAttribute("meetingView",meetingService.detailMeeting(meetingId));
    	return "folder/meetingSemple";
    }

}
