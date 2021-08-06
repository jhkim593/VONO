package my.vono.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.RequiredArgsConstructor;
import my.vono.web.service.WasteBasketService;
import my.vono.web.wasteBasket.WasteBasket;
import my.vono.web.wasteBasket.WasteBasketVO;

@Controller
@RequiredArgsConstructor
public class WasteBasketController {

	private final WasteBasketService wasteBasketService;

	//파일 클릭시, 표지 보이도록
	@RequestMapping("viewFiles")
	public String getviewFiles(){
		System.out.println("viewFiles도착");
		return "demohtml/viewFiles";
	}
	
	//영구삭제
	@RequestMapping(value = "deleteWB", method= {RequestMethod.POST}, produces = "application/text; charset=utf8")
	public String getdeleteWB(HttpServletRequest request){
//		String[] delFileName=request.getParameterValues("fileName");
//		
//		System.out.println("delFileName: "+delFileName);
//		System.out.println("con도착");
		return "demohtml/viewFiles";
	}
	
	//영구삭제
//	@DeleteMapping("deleteWB2")
//	public ResponseEntity<Void> getdeleteWasteBasket(@PathVariable("id") Long id){
//		wasteBasketService.getdeleteFile(id);
//			System.out.println("con도착");
//			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT); 
//	  }
	
	
	//데모 페이지 목록
	@RequestMapping("wasteBasketDemo")
//	public String getDemo(Model m) {
//		List<WasteBasket> list = wasteBasketService.getAllList();
//		m.addAttribute("fileList",list);
//		System.out.println("getDemoList: "+list);
//		
//		return "demohtml/wasteBasketDemo";
//	}
	
	  public ResponseEntity<List<WasteBasket>> getAllmembers() { 
		List<WasteBasket> fileList = wasteBasketService.getAllList(); 
		
		return new ResponseEntity<List<WasteBasket>>(fileList, HttpStatus.OK); 
	  }
	

	//휴지통 목록 보기
	@RequestMapping("wasteBasket")
	public String getWasteBasket(Model m){

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
		return "trash/wasteBasket";
	}
}
