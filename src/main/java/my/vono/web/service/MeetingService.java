package my.vono.web.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import my.vono.web.model.user.MemberRepository;
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
import my.vono.web.model.folder.FolderRepository;
import my.vono.web.model.meeting.MeetingRepository;
import my.vono.web.model.meeting.MeetingDetailDto;
import my.vono.web.model.meeting.MeetingDto;
import my.vono.web.model.meeting.MeetingSimpleDto;

@Service
@RequiredArgsConstructor
@Transactional
public class MeetingService {

	private final MeetingRepository meetingRepository;
	private final FolderRepository folderRepository;
	private final MemberRepository memberRepository;

	
	public Long createMeeting(MeetingDto meetingDto , Long memberId) {
		Folder folder=null;
		
		if(meetingDto.getFolder_id()!=null) {
		folder= folderRepository.findById(meetingDto.getFolder_id()).orElseThrow(FolderNotFoundException::new);
		}
		else {
			folder= folderRepository.findFolderByName("Basic" ,memberId).orElseThrow(FolderNotFoundException::new);

		}
		System.out.println(folder.getName());

		Meeting meeting = Meeting.createMeeting(meetingDto.getName(), meetingDto.getContent(),
				meetingDto.getParticipant(), folder,meetingDto.getRecToTextUrl(),meetingDto.getRecFileUrl());

		return meetingRepository.save(meeting).getId();


	}

//	public void updateMeeting(MeetingDto meetingDto) {
//		Meeting meeting = meetingDAO.findById(meetingDto.getId()).orElseThrow(MeetingNotFoundException::new);
//		meeting.
//	}
	public void deleteMeeting(Long id) {
		Meeting meeting = meetingRepository.findById(id).orElseThrow(MeetingNotFoundException::new);
		meeting.changeIs_trashTrue();
		meeting.addMember(meeting.getFolder().getMember());
		meeting.getFolder().getMeetings().remove(meeting);
		meeting.removeFolder();
	}

    public MeetingDto detailMeeting(Long id) {
    	Meeting meeting = meetingRepository.findById(id).orElseThrow(MeetingNotFoundException::new);
    	return new MeetingDto(meeting);
    	
    	
    }
    
//    public void moveMeeting(String name,Long id,CustomUserDetails custom) {
//    	Meeting meeting = meetingRepository.findById(id).orElseThrow(MeetingNotFoundException::new);
//    	meeting.getFolder().getMeetings().remove(meeting);
//    	Folder folder= folderRepository.findFolderByName(name, custom.getMember().getId()).orElseThrow(FolderNotFoundException::new);
//    	meeting.addFolder(folder);
//    }
    
    //검색
    public List<MeetingSimpleDto> searchMeeting(Long memberId,String name){
    	Member member= memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        return meetingRepository.findMeetingWithNameAndMemberId(memberId, name).stream().map(m->new MeetingSimpleDto(m)).collect(Collectors.toList());
    	
    }
    

    //폴더 속 회의록 조회


	public MeetingDetailDto meetingReader(String url) throws Exception {

		return ExcelReader.excelReader(url);
	}
	public void meetingWrite(List<MeetingLogVO>list,List<String>memoList,String url)throws Exception{
		ExcelMaker.writeExcelFile(list, memoList,url);
	}
	
	//폴더별 미팅로드 불러오기 삭제되지않은
	public List<MeetingSimpleDto> folderMeetingList(Long fileID){
		 meetingRepository.findFolderByFolderId(fileID);
		return meetingRepository.findFolderByFolderId(fileID).stream().map(m -> new MeetingSimpleDto(m)).collect(Collectors.toList());
	}

	 public void moveMeeting(Long fileid, Long afterId) {
	    	Meeting meeting = meetingRepository.findById(fileid).orElseThrow(MeetingNotFoundException::new);
	    	meeting.getFolder().getMeetings().remove(meeting);
	    	Folder folder= folderRepository.findById(afterId).orElseThrow(FolderNotFoundException::new);
	    	meeting.addFolder(folder);
	    	meeting.addOriFolderId(afterId);
	    }

}
