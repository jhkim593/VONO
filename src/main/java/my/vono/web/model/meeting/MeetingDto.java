package my.vono.web.model.meeting;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import my.vono.web.entity.Meeting;

@Getter
@Setter
public class MeetingDto {
	private Long id;
	
	private String name;
	
	private Long folder_id;
	
	private LocalDateTime create_date;
	
	private LocalDateTime edit_date;
	
	private String participant;
	
	private String content;
	
	private Boolean is_trash;
	
	public MeetingDto(Meeting meeting) {
		
	   this.id=meeting.getId();
	   this.name=meeting.getName();
	   this.folder_id=meeting.getFolder().getId();
	   this.create_date=meeting.getCreate_Date();
	   this.edit_date=meeting.getEdit_date();
	   this.participant=meeting.getParticipant();
	   this.content=meeting.getContent();
	   this.is_trash=meeting.getIs_trash();
		
	}
	
	
	
	
	

}
