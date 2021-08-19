package my.vono.web.wasteBasket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import my.vono.web.service.FolderService;
import my.vono.web.service.WasteBasketService;

@Controller
@RequiredArgsConstructor
public class DemoController {

	private final WasteBasketService wasteBasketService;
	private final FolderService folderService;
	private final WBService wbService;

	// 휴지통 조회 (데모 페이지)
	@RequestMapping("wasteBasketDemo")
	public String getTrash(Model m) {

		try {
			Long memberId = 1L;
			System.out.println("getTrash: " + wasteBasketService.findWasteBasket(memberId));

			m.addAttribute("listName", wasteBasketService.findWasteBasket(memberId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "demohtml/wasteBasketDemo";
	}

	// 폴더 조회 (데모 페이지)
	@RequestMapping("listDemo")
	public String getList(Model m) {
		try {
			Long memberId = 1L;
			System.out.println("getList: " + folderService.findFolders(memberId));
			m.addAttribute("listName", folderService.findFolders(memberId));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "demohtml/listDemo";
	}

	// 영구삭제(기능 테스트)
	@RequestMapping(value = "deleteDemo", method = { RequestMethod.POST })
	@ResponseBody
	public String deleteWasteBasket(@RequestParam(value = "meetingId", required = false) List<Long> meetingId,
			@RequestParam(value = "folderId", required = false) List<Long> folderId) {

		try {
			System.out.println("meetingIdList----->" + meetingId);
			System.out.println("folderIdList----->" + folderId);

			if (meetingId != null) {
				wasteBasketService.permanentlyDeleteMeeting(meetingId);
			}
			if (folderId != null) {
				wasteBasketService.permanentlyDeleteFolder(folderId);
			}

			System.out.println("삭제완료");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 휴지통 > 파일 보기
	@RequestMapping("folderView")
	public String getmyFolder(Model m) {
		try {
			Long memberId = 1L;
			// 폴더 아이디값, 이름값줘서 해당 내용 나오도록
			System.out.println("getList: " + folderService.findFolders(memberId));
			//m.addAttribute("listName", folderService.findFolders(memberId));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "trash/folderView";
	}
}
