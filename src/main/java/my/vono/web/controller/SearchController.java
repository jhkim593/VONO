package my.vono.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import my.vono.web.service.FolderService;
import my.vono.web.service.MeetingService;

@Controller
@RequiredArgsConstructor
public class SearchController {

	private final MeetingService meetingService;
	
	//검색창
	@RequestMapping("search")
	public String searchAll(Model m, @RequestParam(required = false, defaultValue = "") String searchText) {
		System.out.println("search 도착, 검색한 단어는: " + searchText);
		Long memberId = 1L;
		m.addAttribute("searchList", meetingService.searchMeeting(memberId, searchText));
		System.out.println("searchList---->" + meetingService.searchMeeting(memberId, searchText));
		return "trash/search";
	}

	//휴지통 회의록 조회
	@RequestMapping("viewFiles")
	public String getviewFiles() {
		return "demohtml/viewFiles";
	}

	
}
