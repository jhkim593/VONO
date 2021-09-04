package my.vono.web.service;

import java.util.List;
import java.util.stream.Collectors;

import my.vono.web.model.folder.FolderRepository;
import my.vono.web.model.user.MemberRepository;
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
import my.vono.web.model.folder.FolderSimpleDto;
import my.vono.web.model.folder.WasteBasketDto;
import my.vono.web.model.meeting.MeetingRepository;
import my.vono.web.model.meeting.MeetingSimpleDto;

@Service
@RequiredArgsConstructor
@Transactional
public class WasteBasketService {

	private final FolderRepository folderRepository;
	private final MeetingRepository meetingRepository;
	private final MemberRepository memberRepository;

	// 회의록 복구
	public void recoverMeeting(List<Long> listId) {
		if (!listId.isEmpty()) {
			for (int i = 0; i < listId.size(); i++) {
				Long id = listId.get(i);
				Meeting meeting = meetingRepository.findById(id).orElseThrow(MeetingNotFoundException::new);
				meeting.changeIs_trashFalse();
				Folder folder = folderRepository.findById(meeting.getOri_folderId()).orElseThrow(FolderNotFoundException::new);
				meeting.removeMember();
				meeting.addFolder(folder);
				
			}
		}

	}

	// 폴더 복구
	public void recoverFolder(List<Long> listId) {
		if (!listId.isEmpty()) {
			for (int i = 0; i < listId.size(); i++) {
				Long id = listId.get(i);
				Folder folder = folderRepository.findById(id).orElseThrow(FolderNotFoundException::new);
				folder.changeIs_trashFalse();
				for (Meeting meeting : folder.getMeetings()) {
					meeting.changeIs_trashFalse();
				}

			}
		}
	}

	// 휴지통에서 영구삭제 (폴더)
	public void permanentlyDeleteFolder(List<Long> listId) {
		if (!listId.isEmpty()) {
			for (int i = 0; i < listId.size(); i++) {
				Long folderId = listId.get(i);
				Folder folder = folderRepository.findById(folderId).orElseThrow(FolderNotFoundException::new);
				if (folder.getIs_trash()) {
					folderRepository.delete(folder);
				} else {
					throw new FolderIsNotTrashException();
				}
			}
		}

	}

	// 휴지통에서 영구 삭제 회의록
	public void permanentlyDeleteMeeting(List<Long> listId) {
		if (!listId.isEmpty()) {
			for (int i = 0; i < listId.size(); i++) {
				Long meetingId = listId.get(i);
				Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(MeetingNotFoundException::new);
				if (meeting.getIs_trash() && meeting.getFolder() == null) {
					meetingRepository.delete(meeting);
				}
			}
		}

	}

	// 휴지통 회의록,폴더 조회
	public WasteBasketDto findWasteBasket(Long memberId) {
		Member findMember = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

		List<FolderSimpleDto> trashFolderDto = null;
		List<MeetingSimpleDto> trashMeetingDto = null;

		List<Folder> trashFolder = folderRepository.findTrashFolderByMemberId(memberId);
		if (trashFolder != null) {
			trashFolderDto = trashFolder.stream().map(tf -> new FolderSimpleDto(tf)).collect(Collectors.toList());
		}

		List<Meeting> trashMeeting = meetingRepository.findTrashMeetingByMemberId(memberId);
		if (trashMeeting != null) {
			trashMeetingDto = trashMeeting.stream().map(tm -> new MeetingSimpleDto(tm)).collect(Collectors.toList());
		}
		return new WasteBasketDto(trashFolderDto, trashMeetingDto);
	}

	//휴지통 회의록 조회
	public WasteBasketDto findMeetingByMeetingId(Long memberId, Long meetingId) {
		List<MeetingSimpleDto> viewMeetingDto = null;
		
		List<Meeting> viewMeeting = meetingRepository.findMeetingByMeetingId(memberId,meetingId);
		if (viewMeeting != null) {
			viewMeetingDto = viewMeeting.stream().map(tm -> new MeetingSimpleDto(tm)).collect(Collectors.toList());
		}
		return new WasteBasketDto(null, viewMeetingDto);
	}

	// 휴지통 > 폴더 속 회의록 조회
	public WasteBasketDto findFolderByFolerId(Long memberId, Long folderId) {
		List<MeetingSimpleDto> viewFolderDto = null;

		List<Meeting> viewFolder = meetingRepository.findFolderByFolderId(memberId,folderId);
		if (viewFolder != null) {
			viewFolderDto = viewFolder.stream().map(tm -> new MeetingSimpleDto(tm)).collect(Collectors.toList());
		}

		return new WasteBasketDto(null, viewFolderDto);

	}

}
