package my.vono.web.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import my.vono.web.config.auth.CustomUserDetails;
import my.vono.web.entity.Folder;
import my.vono.web.entity.Meeting;
import my.vono.web.entity.Member;
import my.vono.web.excelUtile.ExcelMaker;
import my.vono.web.excelUtile.ExcelReader;
import my.vono.web.excelUtile.MeetingLogVO;
import my.vono.web.exception.FolderNotFoundException;
import my.vono.web.exception.MeetingNotFoundException;
import my.vono.web.exception.MemberNotFoundException;
import my.vono.web.model.folder.FolderDAO;
import my.vono.web.model.folder.FolderSimpleDto;
import my.vono.web.model.meeting.MeetingDAO;
import my.vono.web.model.meeting.MeetingDetailDto;
import my.vono.web.model.meeting.MeetingDto;
import my.vono.web.model.meeting.MeetingSimpleDto;
import my.vono.web.model.user.MemberDAO;

@Service
@RequiredArgsConstructor
@Transactional
public class MeetingService {

	private final MeetingDAO meetingDAO;
	private final FolderDAO folderDAO;
	private final MemberDAO memberDAO;

	
	public void createMeeting(MeetingDto meetingDto , Long memberId) {
		Folder folder=null;
		
		if(meetingDto.getFolder_id()!=null) {
		folder=folderDAO.findById(meetingDto.getFolder_id()).orElseThrow(FolderNotFoundException::new);
		}
		else {
			folder=folderDAO.findFolderByName("Basic" ,memberId).orElseThrow(FolderNotFoundException::new);

		}
		System.out.println(folder.getName());

		Meeting meeting = Meeting.createMeeting(meetingDto.getName(), meetingDto.getContent(),
				meetingDto.getParticipant(), folder,meetingDto.getRecToTextUrl(),meetingDto.getRecFileUrl());

		meetingDAO.save(meeting);

	}

//	public void updateMeeting(MeetingDto meetingDto) {
//		Meeting meeting = meetingDAO.findById(meetingDto.getId()).orElseThrow(MeetingNotFoundException::new);
//		meeting.
//	}
	public void deleteMeeting(Long id) {
		Meeting meeting = meetingDAO.findById(id).orElseThrow(MeetingNotFoundException::new);
		meeting.changeIs_trashTrue();
		meeting.addMember(meeting.getFolder().getMember());
		meeting.getFolder().getMeetings().remove(meeting);
		meeting.removeFolder();
	}

    public MeetingDto detailMeeting(Long id) {
    	Meeting meeting = meetingDAO.findById(id).orElseThrow(MeetingNotFoundException::new);
    	return new MeetingDto(meeting);
    	
    	
    }
    
    public void moveMeeting(String name,Long id,CustomUserDetails custom) {
    	Meeting meeting = meetingDAO.findById(id).orElseThrow(MeetingNotFoundException::new);
    	meeting.getFolder().getMeetings().remove(meeting);
    	Folder folder=folderDAO.findFolderByName(name, custom.getMember().getId()).orElseThrow(FolderNotFoundException::new);
    	meeting.addFolder(folder);
    }
    
    //검색
    public List<MeetingSimpleDto> searchMeeting(Long memberId,String name){
    	Member member=memberDAO.findById(memberId).orElseThrow(MemberNotFoundException::new);
        return meetingDAO.findMeetingWithNameAndMemberId(memberId, name).stream().map(m->new MeetingSimpleDto(m)).collect(Collectors.toList());
    	
    }
    

    //폴더 속 회의록 조회

	public Object findFolerView() {
		return null;
	}

	public MeetingDetailDto meetingReader(String url) throws Exception {

		return ExcelReader.excelReader(url);
	}
	public void meetingWrite(List<MeetingLogVO>list,List<String>memoList,String url)throws Exception{
		ExcelMaker.writeExcelFile(list, memoList,url);
	}
	
	//폴더별 미팅로드 불러오기 삭제되지않은
	public List<MeetingSimpleDto> folderMeetingList(Long fileID){
		 meetingDAO.findFolderByFolderId(fileID);
		return meetingDAO.findFolderByFolderId(fileID).stream().map(m -> new MeetingSimpleDto(m)).collect(Collectors.toList());
	}
	 public void moveMeeting1(Long fileid,Long afterId) {
	    	Meeting meeting = meetingDAO.findById(fileid).orElseThrow(MeetingNotFoundException::new);
	    	meeting.getFolder().getMeetings().remove(meeting);
	    	Folder folder=folderDAO.findById(afterId).orElseThrow(FolderNotFoundException::new);
	    	meeting.addFolder(folder);
	    }

}
