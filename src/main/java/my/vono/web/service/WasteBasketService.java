package my.vono.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import my.vono.web.wasteBasket.WasteBasket;
import my.vono.web.wasteBasket.WasteBasketDAO;

@Service
@RequiredArgsConstructor
public class WasteBasketService {
	
	@Autowired
	private final WasteBasketDAO wasteBasketRepo;

	public List<WasteBasket> getAllList() {
		List<WasteBasket> list =new ArrayList<>();
		wasteBasketRepo.findAllByOrderByDateDesc().forEach(addlist->list.add(addlist));
		return list;
	}
	
	public void getdeleteFile(Long id) {
		wasteBasketRepo.deleteById(id);
	}

}
