package my.vono.web.model.folder;

import lombok.Getter;
import lombok.Setter;
import my.vono.web.entity.Folder;

@Getter
@Setter
public class FolderSimpleDto {
	private Long id;
	private String name;
	
	public FolderSimpleDto(Folder folder) {
		this.id=folder.getId();
		this.name=folder.getName();
	}

}
