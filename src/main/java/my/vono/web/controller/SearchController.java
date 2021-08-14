package my.vono.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import my.vono.web.wasteBasket.WasteBasketService;

@Controller
@RequiredArgsConstructor
public class SearchController {

	private final WasteBasketService wbService;


	// 휴지통 > 삭제 폴더 모음(조회)
	//String searchStr="";
	@RequestMapping("search")
	public String searchAll(Model m, @RequestParam(required = false, defaultValue = "") String searchText) {
		m.addAttribute("wbList",wbService.getAllFiles());
		System.out.println("searchMsg 도착, 검색한 단어는: "+searchText);

		return "demohtml/search";
	}
//		@RequestMapping("searchMsg")
//		public String searchAll(Model m,@RequestParam(value="searchName") String searchName) {
//			
//			searchStr=searchName;
//			System.out.println("searchMsg 도착, 검색한 단어는: "+searchStr);
//
//			return "demohtml/search";
//		}
		
		@RequestMapping("searchView")
		public String search(Model m) {
			
			m.addAttribute("wbList",wbService.getAllFiles());
			m.addAttribute("searchName","");//searchStr
			return "demohtml/search";
		}

}
