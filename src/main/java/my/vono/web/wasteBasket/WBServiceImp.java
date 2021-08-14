package my.vono.web.wasteBasket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WBServiceImp implements WasteBasketService {
	
	@Autowired
	private WasteBasketDAO wbRepository;
	
	@Override
	public List<WasteBasket> getAllFiles(){
		return wbRepository.findAll(); 
	}

	@Override
	public void deleteAllById(List<Long> id) {
		wbRepository.deleteAllById(id);
		
	}

	@Override
	public void redoById(List<Long> id) {
		System.out.println("redoById: "+id);
		//wbRepository.saveAll(id);
	}

//	@Override
//	public List<WasteBasket> findByNameContaining(List<String> name) {
//		System.out.println("findByNameContaining: "+name);
//		return wbRepository.findByNameContaining(name);
//	}


}
