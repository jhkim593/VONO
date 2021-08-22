package my.vono.web.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import my.vono.web.entity.Folder;
import my.vono.web.entity.Meeting;
import my.vono.web.entity.Member;
import my.vono.web.exception.BasicFolderRenameException;
import my.vono.web.exception.FolderAlreadyExistException;
import my.vono.web.exception.FolderIsNotTrashException;
import my.vono.web.exception.FolderNotFoundException;
import my.vono.web.exception.MeetingNotFoundException;
import my.vono.web.exception.MemberNotFoundException;
import my.vono.web.model.folder.FolderDAO;
import my.vono.web.model.folder.FolderDto;
import my.vono.web.model.folder.FolderSimpleDto;
import my.vono.web.model.meeting.MeetingDAO;
import my.vono.web.model.user.MemberDAO;

@Service
@RequiredArgsConstructor
@Transactional
public class FolderService {
	private final FolderDAO folderDAO;
	private final MemberDAO memberDAO;
	private final MeetingDAO meetingDAO;
	
	//기본폴더 안지워 지게 설정?
	// 폴더 인서트 
		
	public void createFolder(Long memberId ,String folderName) {

		if (validFolder(folderName ,memberId)) {
			throw new FolderAlreadyExistException();
		}

		Member member = memberDAO.findById(memberId).orElseThrow(MemberNotFoundException::new);

		// 확인

		folderDAO.save(Folder.createFolder(folderDto.getName(), member));

	}

	// 폴더 이름 중복확인
	public Boolean validFolder(String name , Long memberId) {
		if (folderDAO.findFolderByName(name , memberId) ==null) {
			return true;
		}
		return false;

	}

	public void trashFolder(FolderDto folderDto) {

		Folder folder = folderDAO.findById(folderDto.getId()).orElseThrow(FolderNotFoundException::new);
		folder.changeIs_trashTrue();
		for (Meeting meeting : folder.getMeetings()) {
			meeting.changeIs_trashTrue();
		}

	}

	public List<FolderSimpleDto> findFolders(Long member_id) {
		return folderDAO.findFolderByMemberId(member_id).stream().map(f -> new FolderSimpleDto(f))
				.collect(Collectors.toList());

	}

	public void renameFolder(FolderDto folderDto) {

		Folder folder = folderDAO.findById(folderDto.getId()).orElseThrow(FolderNotFoundException::new);
		if(folder.getName().equals("기본폴더")) {
			throw new BasicFolderRenameException();
		}

		if (validFolder(folderDto.getName(),folderDto.getMember_id())) {
			throw new FolderAlreadyExistException();
		}

		folder.changeFolderName(folderDto.getName());

	}

//	public void moveFolder(FolderMoveRequestDto requestDto) {
//	
//		Folder folder=folderDAO.findById(requestDto.getId()).orElseThrow(FolderNotFoundException::new);
//		Folder wantFolder=folderDAO.findFolderByName(requestDto.getName()).orElseThrow(FolderNotFoundException::new);
//		
//		
//		
//
//		
//	}

	public FolderDto detailFolder(Long folderId) {
		Folder folder = folderDAO.findById(folderId).orElseThrow(FolderNotFoundException::new);
		return new FolderDto(folder);

	}
	

    //휴지통 기능
//
//	public List<FolderDto> trashFolders(Long member_id) {
//
//		return folderDAO.findTrashFolderByMemberId(member_id).stream().map(tf->new FolderDto(tf)).collect(Collectors.toList());
//		
//
//	}
// 
	
	
}
