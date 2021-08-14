package my.vono.web.model.folder;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.vono.web.model.meeting.MeetingSimpleDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WasteBasketDto {
	
	List<FolderSimpleDto>folders;
	List<MeetingSimpleDto>meeting;

}
