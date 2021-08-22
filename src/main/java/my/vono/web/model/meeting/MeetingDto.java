package my.vono.web.model.meeting;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.vono.web.entity.Meeting;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class MeetingDto {
	private Long id;
	
	private String name;
	
	private String folderName;
	
	private Long folder_id;
	
	private LocalDateTime create_date;
	
	private LocalDateTime edit_date;
	
	private String participant;
	
	private String content;
	
	private Boolean is_trash;
	
	private String recToTextUrl;
	
	private String recFileUrl;
	
	public MeetingDto(Meeting meeting) {
		
	   this.id=meeting.getId();
	   this.name=meeting.getName();
	   this.folder_id=meeting.getFolder().getId();
	   this.create_date=meeting.getCreate_Date();
	   this.edit_date=meeting.getEdit_date();
	   this.participant=meeting.getParticipant();
	   this.content=meeting.getContent();
	   this.is_trash=meeting.getIs_trash();
	   this.recToTextUrl=meeting.getRecToTextUrl();
	   this.recFileUrl=meeting.getRecFileUrl();
	}
	
	
	
	
	

}
