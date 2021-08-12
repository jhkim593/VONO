package my.vono.web.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import my.vono.web.entity.Folder;
import my.vono.web.entity.Meeting;
import my.vono.web.exception.FolderNotFoundException;
import my.vono.web.exception.MeetingNotFoundException;
import my.vono.web.model.folder.FolderDAO;
import my.vono.web.model.meeting.MeetingDAO;
import my.vono.web.model.meeting.MeetingDto;

@Service
@RequiredArgsConstructor
@Transactional
public class MeetingService {

	
	private final MeetingDAO meetingDAO;
	private final FolderDAO  folderDAO;
	
	public void createMeeting(MeetingDto meetingDto) {
		Folder folder=null;
		if(meetingDto.getFolder_id()!=null) {
		folder=folderDAO.findById(meetingDto.getFolder_id()).orElseThrow(FolderNotFoundException::new);
		}
		else {
			folder=folderDAO.findFolderByName("기본폴더").orElseThrow(FolderNotFoundException::new);
		}
		Meeting meeting=Meeting.createMeeting(meetingDto.getName(), meetingDto.getContent(),meetingDto.getParticipant(),folder);
		meetingDAO.save(meeting);
		
	}
	
//	public void updateMeeting(MeetingDto meetingDto) {
//		Meeting meeting = meetingDAO.findById(meetingDto.getId()).orElseThrow(MeetingNotFoundException::new);
//		meeting.
//	}
	public void deleteMeeting(Long id) {
		Meeting meeting = meetingDAO.findById(id).orElseThrow(MeetingNotFoundException::new);
		meeting.changeIs_trash();
	}
    public void recoverMeeting(Long id) {
    	Meeting meeting = meetingDAO.findById(id).orElseThrow(MeetingNotFoundException::new);
		meeting.changeIs_trash();
    	
    }
    public MeetingDto detailMeeting(Long id) {
    	Meeting meeting = meetingDAO.findById(id).orElseThrow(MeetingNotFoundException::new);
    	return new MeetingDto(meeting);
    	
    	
    }
    public void moveMeeting(String name,Long id) {
    	Meeting meeting = meetingDAO.findById(id).orElseThrow(MeetingNotFoundException::new);
    	meeting.getFolder().getMeetings().remove(meeting);
    	Folder folder=folderDAO.findFolderByName(name).orElseThrow(FolderNotFoundException::new);
    	meeting.addFolder(folder);
    }
	
	
	
}
