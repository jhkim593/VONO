package my.vono.web.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import my.vono.web.entity.Folder;
import my.vono.web.entity.Meeting;
import my.vono.web.exception.FolderNotFoundException;
import my.vono.web.model.folder.FolderDAO;
import my.vono.web.model.meeting.MeetingDAO;
import my.vono.web.model.meeting.MeetingDto;

@Service
@RequiredArgsConstructor
public class MeetingService {

	
	private final MeetingDAO meetingDAO;
	private final FolderDAO  folderDAO;
	
	public void createMeeting(MeetingDto meetingDto) {
		Folder folder=null;
		if(meetingDto.getFolder_id()!=null)
		folder=folderDAO.findById(meetingDto.getFolder_id()).orElseThrow(FolderNotFoundException::new);
		Meeting meeting=Meeting.createMeeting(meetingDto.getName(), meetingDto.getContent(),folder);
		meetingDAO.save(meeting);
		
	}
	public void updateMeeting(MeetingDto meetingDto) {
		
		
	}
//	public void deleteMeeting(Meeting) {
//		
//	}
	
	
	
}
