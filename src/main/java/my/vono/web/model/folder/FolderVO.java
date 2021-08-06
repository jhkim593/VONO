package my.vono.web.model.folder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FolderVO {

	private Long id;
	
	private String name;
	
	private Long member_id;
	
	private Long parent_id;
	
	private Boolean is_trash;
	

}
