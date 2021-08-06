package my.vono.web.wasteBasket;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WasteBasketDAO extends JpaRepository<WasteBasket, Long> {

	List<WasteBasket> findAll();
	
	List<WasteBasket> findAllByOrderByDateDesc();
	
	Long deleteById(long id);
}
