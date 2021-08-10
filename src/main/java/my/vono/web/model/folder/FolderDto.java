package my.vono.web.model.folder;

import lombok.Getter;
import lombok.Setter;
import my.vono.web.entity.Folder;

@Getter
@Setter
public class FolderDto {

	private Long id;
	
	private String name;
	
	private Long member_id;
	
	private Long parent_id;
	
	private Boolean is_trash;
	
	public FolderDto(Folder folder) {
	  this.id=folder.getId();
	  this.name=folder.getName();
	  this.member_id=folder.getMember().getId();
	  this.parent_id=folder.getFolder().getId();
	  this.is_trash=folder.getIs_trash();
	  
	}

}
