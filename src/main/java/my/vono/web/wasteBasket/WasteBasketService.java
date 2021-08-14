package my.vono.web.wasteBasket;

import java.util.List;

import org.springframework.data.repository.query.Param;

public interface WasteBasketService {
	
	//파일 전체 조회
	List<WasteBasket> getAllFiles();
	
	//파일 삭제
	void deleteAllById(@Param("id") List<Long> id);
	
	//파일 수정(이동_파일 목록으로 이동)
	void redoById(@Param("id") List<Long> id);
	
	//검색
	//List<WasteBasket> findByNameContaining();

	//List<WasteBasket> findByNameContaining(@Param("name") List<String> name);
}
