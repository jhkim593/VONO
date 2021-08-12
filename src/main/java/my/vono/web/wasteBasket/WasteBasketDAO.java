package my.vono.web.wasteBasket;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WasteBasketDAO extends JpaRepository<WasteBasket, Long> {


}
