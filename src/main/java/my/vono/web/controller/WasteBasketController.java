package my.vono.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import my.vono.web.wasteBasket.WasteBasketVO;

@Controller
public class WasteBasketController {

	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
//	@RequestMapping("/deleteWB")
//	public String getdeleteWB(){
//		
//		return "demohtml/viewFiles";
//	}
	
	@RequestMapping("/viewFiles")
	public String getviewFiles(){
		
		return "demohtml/viewFiles";
	}
	
	@RequestMapping("/wasteBasketDemo")
	public String getDemo(Model m) {
		
		List<WasteBasketVO> list = new ArrayList<>();
		list.add(new WasteBasketVO("VONO 회의록"));
		list.add(new WasteBasketVO("보노보보보보오오오오농"));
		list.add(new WasteBasketVO("Test_FileName_VONO3"));
		list.add(new WasteBasketVO("qq"));
		list.add(new WasteBasketVO("Test_FileName_VONO4"));
		list.add(new WasteBasketVO("VONO5ㅋㅋㅋㅋㅋㅋㅋㅋ"));
		list.add(new WasteBasketVO("회의록이라고오오오ㅗ오오오오오오오오ㅗ오오오오오ㅗ오오오오오오오"));
		list.add(new WasteBasketVO("Test_FileName_VONO7"));
		
		System.out.println("list: "+list);
		m.addAttribute("FileVo",list);
		return "demohtml/wasteBasketDemo";
	}
	 


	@RequestMapping("/wasteBasket")
	public String getWasteBasket(Model m){
		WasteBasketVO wvo=new WasteBasketVO("파일테스트a");
		m.addAttribute("wasteBasketVO",wvo);
		System.out.println("wvo: "+wvo);
		return "html/wasteBasket";
	}
}
