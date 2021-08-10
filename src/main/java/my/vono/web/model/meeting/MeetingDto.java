package my.vono.web.model.meeting;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

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
	
	
	
	
	

}
