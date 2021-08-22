package my.vono.web.wasteBasket;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import my.vono.web.entity.Folder;
import my.vono.web.entity.Meeting;
import my.vono.web.entity.Member;
import my.vono.web.exception.FolderIsNotTrashException;
import my.vono.web.exception.FolderNotFoundException;
import my.vono.web.exception.MeetingNotFoundException;
import my.vono.web.exception.MemberNotFoundException;
import my.vono.web.model.folder.FolderDAO;
import my.vono.web.model.folder.FolderSimpleDto;
import my.vono.web.model.folder.WasteBasketDto;
import my.vono.web.model.meeting.MeetingDAO;
import my.vono.web.model.meeting.MeetingDto;
import my.vono.web.model.meeting.MeetingSimpleDto;
import my.vono.web.model.user.MemberDAO;

@Service
@RequiredArgsConstructor
@Transactional
public class WBService {

	private final FolderDAO folderDAO;
	private final MeetingDAO meetingDAO;
	private final MemberDAO memberDAO;

	// 검색
	public WasteBasketDto searchAll(Long memberId, String searchText) {
		System.out.println("memberId----> "+memberId+"searchText----->"+searchText);
//		List<FolderSimpleDto> trashFolderDto = null;
//		List<MeetingSimpleDto> trashMeetingDto = null;
//
//		List<Folder> searchFolder = folderDAO.findFolderWithNameAndMemberId(memberId, searchText);
//		if (searchFolder != null) {
//			trashFolderDto = searchFolder.stream().map(tf -> new FolderSimpleDto(tf)).collect(Collectors.toList());
//		}
//
//		List<Meeting> searchMeeting = meetingDAO.findMeetingWithNameAndMemberId(memberId, searchText);
//		if (searchMeeting != null) {
//			trashMeetingDto = searchMeeting.stream().map(tm -> new MeetingSimpleDto(tm)).collect(Collectors.toList());
//		}
//		return new WasteBasketDto(trashFolderDto, trashMeetingDto);
		return null;
	}

	// 폴더 상세조회
//	public List<FolderSimpleDto> findByfolderDetail(Long member_id, Long folder_id) {
//		return folderDAO.findFolderByMemberId(folder_id).stream().map(f -> new FolderSimpleDto(f))
//				.collect(Collectors.toList());
//	}

	// 폴더 속 조회
//	public MeetingDto detailFolder(Long id, Long folderId) {
//		Meeting meeting = meetingDAO.findMeetingWithFolderIdAndMemberId(id, folderId).orElseThrow(MeetingNotFoundException::new);
//		return new MeetingDto(meeting);
//
//	}
}
