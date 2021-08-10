package my.vono.web.model.folder;

import java.util.List;
import java.util.stream.Collector;
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
public class FolderDetailDto {
	private List<FolderDto>folder;
	private List<MeetingDto>meeting;
	
	
	
	public FolderDetailDto(Folder folder) {
		folder.getFolders().stream().map(f->new FolderDto(f)).collect(Collectors.toList());
		
		
	}

}
