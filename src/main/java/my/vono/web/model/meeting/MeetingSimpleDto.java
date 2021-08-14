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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingSimpleDto {

	private Long id;

	private String name;

	private LocalDateTime create_date;

	private LocalDateTime edit_date;

	private String participant;

	private String content;

	private Boolean is_trash;

	public MeetingSimpleDto(Meeting meeting) {
		this.id = meeting.getId();
		this.name = meeting.getName();
	
		this.create_date = meeting.getCreate_Date();
		this.edit_date = meeting.getEdit_date();
		this.participant = meeting.getParticipant();
		this.content = meeting.getContent();
		this.is_trash = meeting.getIs_trash();
	}
}
