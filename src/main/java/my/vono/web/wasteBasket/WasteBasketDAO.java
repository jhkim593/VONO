package my.vono.web.wasteBasket;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WasteBasketDAO extends JpaRepository<WasteBasket, Long> {

	//List<WasteBasket> findByNameContaining(List<String> name);
	
	//Page<WasteBasket> findByNameContainingOrContentContaining(String name, String content, Pageable pageable);
	//List<WasteBasket> findByNameContaining();

}
