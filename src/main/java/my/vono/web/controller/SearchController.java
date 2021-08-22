package my.vono.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import my.vono.web.config.auth.CustomUserDetails;
import my.vono.web.service.FolderService;
import my.vono.web.service.MeetingService;
import my.vono.web.service.WasteBasketService;

@Controller
@RequiredArgsConstructor
public class SearchController {

	private final MeetingService meetingService;
	private final WasteBasketService wasteBasketService;


	// 검색창
	@RequestMapping("search")
	public String searchAll(Model m, @RequestParam(required = false, defaultValue = "") String searchText, @AuthenticationPrincipal CustomUserDetails custom) {
		System.out.println("search 도착, 검색한 단어는: " + searchText);
		Long memberId=custom.getMember().getId();
		m.addAttribute("searchList", meetingService.searchMeeting(memberId, searchText));
		System.out.println("searchList---->" + meetingService.searchMeeting(memberId, searchText));
		return "trash/search";
	}

	@RequestMapping("views")
	public String getMeetingView(Model m, @RequestParam(required = false, defaultValue = "") Long sendMeetingId, @AuthenticationPrincipal CustomUserDetails custom) {
		try {
			Long memberId=custom.getMember().getId();
			System.out.println("views_sendMeetingId========>" + sendMeetingId);
			m.addAttribute("meetingView", wasteBasketService.findMeetingByMeetingId(memberId, sendMeetingId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "trash/viewFiles";
	}

	// 휴지통 > 파일 보기
	@RequestMapping("folderView")
	public String getFolderViews(Model m, @RequestParam(required = false, defaultValue = "") Long sendFolderId,
			@RequestParam(required = false, defaultValue = "") String sendFolderName, @AuthenticationPrincipal CustomUserDetails custom) {
		try {
			Long memberId=custom.getMember().getId();
			System.out.println("sendFolderId========>" + sendFolderId);
			System.out.println("sendFolderName========>" + sendFolderName);
			m.addAttribute("folderView", wasteBasketService.findFolderByFolerId(memberId, sendFolderId));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "trash/folderView";
	}

}
