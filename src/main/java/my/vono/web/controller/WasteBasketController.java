package my.vono.web.controller;

import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import my.vono.web.wasteBasket.WasteBasketService;

@Controller
@RequiredArgsConstructor
public class WasteBasketController {

	private final WasteBasketService wbService;
	
//	@RequestMapping("ch")
//	public String getchat() {
//		return "demohtml/chatbot";
//	}
	
//	@RequestMapping("test")
//	public String gettest() {
//		return "demohtml/test";
//	}

	// 휴지통목록 > 폴더 > 파일목록 (조회)
	@RequestMapping("folderView")
	public String getmyFolder(Model m) {
		try {
			m.addAttribute("wbList",wbService.getAllFiles());
			System.out.println("viewFiles도착: "+wbService.getAllFiles());
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return "trash/folderView";
	}

	// 폴더>파일클릭> 해당 파일 표지
	@RequestMapping("viewFiles")
	public String getviewFiles() {
		System.out.println("viewFiles도착");
		return "demohtml/viewFiles";
	}

	//영구삭제
	@RequestMapping(value = "deleteWB", method = { RequestMethod.POST })
	public String deleteWasteBasket(@RequestParam(value="id") List<Long> id) {
		try {
			System.out.println("List<Long> id: "+id);
			wbService.deleteAllById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "demohtml/wasteBasketDemo";
	}
	//복구
		@RequestMapping(value = "redoWB", method = { RequestMethod.POST })
		public String redoWasteBasket(@RequestParam(value="id") List<Long> id) {
			try {
				System.out.println("List<Long> id: "+id);
				wbService.redoById(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return "demohtml/wasteBasketDemo";
		}
	

	// 휴지통 > 삭제 폴더 모음(조회)
	@RequestMapping("wasteBasket")
	public String getAllFolders(Model m) {
		try {
			m.addAttribute("wbList",wbService.getAllFiles());
			System.out.println("wbList: "+wbService.getAllFiles());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "trash/wasteBasket";
	}
	
	
	// 휴지통 > 삭제 폴더 모음(조회)_데모 페이지
	@RequestMapping("wasteBasketDemo")
	public String getAllFiles(Model m) {
		m.addAttribute("wbList",wbService.getAllFiles());
		System.out.println("wbList: "+wbService.getAllFiles());
		return "demohtml/wasteBasketDemo";
	}
	
}
