package my.vono.web.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import my.vono.web.config.auth.CustomUserDetails;
import my.vono.web.service.WasteBasketService;

@Controller
@RequiredArgsConstructor
public class WasteBasketController {

	private final WasteBasketService wasteBasketService;

	// 휴지통 > 삭제 폴더 모음(조회)
	@RequestMapping("wasteBasket")
	public String getAllFolders(Model m, @AuthenticationPrincipal CustomUserDetails custom) {
		try {
			Long memberId=custom.getMember().getId();
			// 휴지통 목록 조회
			m.addAttribute("listName", wasteBasketService.findWasteBasket(memberId));
			System.out.println("getTrash: " + wasteBasketService.findWasteBasket(memberId));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "trash/wasteBasket";
	}

	// 영구삭제
	@RequestMapping(value = "deleteWB", method = { RequestMethod.POST })
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

	// 복구
	@RequestMapping(value = "redoWB", method = { RequestMethod.POST })
	@ResponseBody
	public String redoWasteBasket(@RequestParam(value = "meetingId", required = false) List<Long> meetingId,
			@RequestParam(value = "folderId", required = false) List<Long> folderId) {

		try {
			System.out.println("meetingIdList----->" + meetingId);
			System.out.println("folderIdList----->" + folderId);

			if (meetingId != null) {
				wasteBasketService.recoverMeeting(meetingId);
			}
			if (folderId != null) {
				wasteBasketService.recoverFolder(folderId);
			}

			System.out.println("복구완료");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

}
