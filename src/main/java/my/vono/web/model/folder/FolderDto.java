package my.vono.web.model.folder;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.vono.web.entity.Folder;
import my.vono.web.model.meeting.MeetingDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FolderDto {

	private Long id;
	
	private String name;
	
	private Long member_id;
	
	private Boolean is_trash;
	
	private List<MeetingDto>meetigs;
	
	public FolderDto(Folder folder) {
	  this.id=folder.getId();
	  this.name=folder.getName();
	  this.member_id=folder.getMember().getId();
	  folder.getMeetings().stream().map(m->new MeetingDto(m)).collect(Collectors.toList());
	 
	  this.is_trash=folder.getIs_trash();
	  
	}

}
