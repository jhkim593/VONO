package my.vono.web.service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import my.vono.web.entity.Folder;
import my.vono.web.exception.FolderAlreadyExistException;
import my.vono.web.exception.FolderNotFoundException;
import my.vono.web.exception.MemberNotFoundException;
import my.vono.web.model.folder.FolderDto;
import my.vono.web.model.folder.FolderRepository;
import my.vono.web.model.meeting.MeetingDto;
import my.vono.web.model.user.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import my.vono.web.entity.Member;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class FolderServiceTest {

	@Autowired
    EntityManager em;
	
	@Autowired
	FolderService folderService;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	FolderRepository folderRepository;

	@Autowired
	MeetingService meetingService;
	

	@BeforeEach
	public void beforeEach() {
		Member member1=Member.createMemeber("1", "1", null, "test", "1", null, null);
		em.persist(member1);
	}
	

	@Test
	public void createFolder()throws Exception{
	   //given
		Member member = memberRepository.findByLoginId("1").orElseThrow(MemberNotFoundException::new);
		//when
		folderService.createFolder(member.getId(),"test");
		em.flush(); em.clear();
		Optional<Folder> test = folderRepository.findFolderByName("test", member.getId());
		//then
		assertThat(folderService.validFolder("test",member.getId())).isTrue();
		assertThat(test.isPresent()).isTrue();
		assertThat(test.get().getIs_trash()).isFalse();
		assertThat(test.get().getName()).isEqualTo("test");
		assertThat(test.get().getMember().getId()).isEqualTo(member.getId());
	}
	/**
	 * Basic(기본폴더일때)삭제불가능
	 * Meeting 모두 이동 확인**/
	@Test
	public void trashFolder()throws Exception{
	   //given
		Member member = memberRepository.findByLoginId("1").orElseThrow(MemberNotFoundException::new);
		folderService.createFolder(member.getId(),"test");
		Folder folder = folderRepository.findFolderByName("test", member.getId()).orElseThrow(FolderNotFoundException::new);

		MeetingDto meetingDto=new MeetingDto();
		meetingDto.setContent("test1");
		meetingDto.setFolderName("Basic");
		meetingDto.setParticipant("testp1");
		meetingDto.setName("testn1");
		Long meetingId1 = meetingService.createMeeting(meetingDto, member.getId());

		MeetingDto meetingDto2=new MeetingDto();
		meetingDto2.setContent("test2");
		meetingDto2.setFolderName("Basic");
		meetingDto2.setParticipant("testp2");
		meetingDto2.setName("testn2");
		Long meetingId2 = meetingService.createMeeting(meetingDto2, member.getId());

		meetingService.moveMeeting(meetingId1,folder.getId());
		meetingService.moveMeeting(meetingId2,folder.getId());
		//when
		folderService.trashFolder(folder.getId());
		em.flush(); em.clear();
		Folder test = folderRepository.findFolderByName("test",member.getId()).orElseThrow(FolderNotFoundException::new);

		//then
		assertThat(test.getIs_trash()).isTrue();
		assertThat(test.getMeetings().get(0).getIs_trash()).isTrue();
		assertThat(test.getMeetings().get(1).getIs_trash()).isTrue();
		assertThat(test.getMeetings().get(0).getId()).isEqualTo(meetingId1);
		assertThat(test.getMeetings().get(1).getId()).isEqualTo(meetingId2);

	}
	@Test
	public void renameFolder()throws Exception{
	   //given
		Member member = memberRepository.findByLoginId("1").orElseThrow(MemberNotFoundException::new);
		folderService.createFolder(member.getId(),"test");

	   //when
		Folder folder = folderRepository.findFolderByName("test", member.getId()).orElseThrow(FolderNotFoundException::new);

		FolderDto folderDto=new FolderDto(folder.getId(),"change",null,null,null);
	    folderService.renameFolder(folderDto);
		em.flush();em.clear();
		Folder findFolder = folderRepository.findFolderByName("change", member.getId()).orElseThrow(FolderNotFoundException::new);

		//then
		assertThat(findFolder.getName()).isEqualTo("change");
		assertThat(findFolder.getMember().getId()).isEqualTo(member.getId());
	}


}
