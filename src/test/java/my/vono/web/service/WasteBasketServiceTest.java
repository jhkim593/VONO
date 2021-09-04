package my.vono.web.service;

import my.vono.web.entity.Folder;
import my.vono.web.entity.Meeting;
import my.vono.web.entity.Member;
import my.vono.web.exception.FolderNotFoundException;
import my.vono.web.exception.MeetingNotFoundException;
import my.vono.web.exception.MemberNotFoundException;
import my.vono.web.model.folder.FolderRepository;
import my.vono.web.model.meeting.MeetingDto;
import my.vono.web.model.meeting.MeetingRepository;
import my.vono.web.model.user.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class WasteBasketServiceTest {

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

    @Autowired
    WasteBasketService wasteBasketService;

    @Autowired
    MeetingRepository meetingRepository;

    @BeforeEach
    public void beforeEach()throws Exception{
        Member member1=Member.createMemeber("1", "1", null, "test", "1", null, null);
        em.persist(member1);
        folderService.createFolder(member1.getId(),"Basic");
    }

    @Test
    public void recoverFolder()throws Exception{
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
        meetingService.moveMeeting(meetingId1,folder.getId());
        folderService.trashFolder(folder.getId());
        List<Long> list=new ArrayList<>();
        list.add(folder.getId());
        //when
        wasteBasketService.recoverFolder(list);

       //then
        assertThat(folder.getIs_trash()).isFalse();
        assertThat(folder.getMeetings().get(0).getIs_trash()).isFalse();
        
    }
    @Test
    public void recoverMeeting()throws Exception{
       //given
        Member member = memberRepository.findByLoginId("1").orElseThrow(MemberNotFoundException::new);
        folderService.createFolder(member.getId(),"test");
        Folder folder = folderRepository.findFolderByName("test", member.getId()).orElseThrow(FolderNotFoundException::new);

        MeetingDto meetingDto=new MeetingDto();
        meetingDto.setContent("test1");
        meetingDto.setFolderName("Basic");
        meetingDto.setParticipant("testp1");
        meetingDto.setName("testn1");
        meetingDto.setFolder_id(folder.getId());
        Long meetingId1 = meetingService.createMeeting(meetingDto, member.getId());
        meetingService.deleteMeeting(meetingId1);
        //when
        List<Long>list=new ArrayList<>();
        list.add(meetingId1);
        wasteBasketService.recoverMeeting(list);
        em.flush();em.clear();
        Meeting meeting = meetingRepository.findById(meetingId1).orElseThrow(MeetingNotFoundException::new);
        //then
        assertThat(meeting.getIs_trash()).isFalse();
        assertThat(meeting.getOri_folderId()).isEqualTo(folder.getId());
        assertThat(meeting.getFolder().getId()).isEqualTo(folder.getId());
        assertThat(folder.getMeetings().get(0).getId()).isEqualTo(meetingId1);


    }
    @Test
    public void permanentlyDeleteMeeting()throws Exception{
       //given
        Member member = memberRepository.findByLoginId("1").orElseThrow(MemberNotFoundException::new);
        folderService.createFolder(member.getId(),"test");
        Folder folder = folderRepository.findFolderByName("test", member.getId()).orElseThrow(FolderNotFoundException::new);

        MeetingDto meetingDto=new MeetingDto();
        meetingDto.setContent("test1");
        meetingDto.setFolderName("Basic");
        meetingDto.setParticipant("testp1");
        meetingDto.setName("testn1");
        meetingDto.setFolder_id(folder.getId());
        Long meetingId1 = meetingService.createMeeting(meetingDto, member.getId());
        meetingService.deleteMeeting(meetingId1);

       //when
        List<Long>list=new ArrayList<>();
        list.add(meetingId1);
        wasteBasketService.permanentlyDeleteMeeting(list);
        em.flush();em.clear();
        Optional<Meeting> opMeeting = meetingRepository.findById(meetingId1);
        //then
        assertThat(opMeeting.isPresent()).isFalse();

    }
    @Test
    public void permanentlyDeleteFolder()throws Exception{
       //given
        Member member = memberRepository.findByLoginId("1").orElseThrow(MemberNotFoundException::new);
        folderService.createFolder(member.getId(),"test");
        Folder folder = folderRepository.findFolderByName("test", member.getId()).orElseThrow(FolderNotFoundException::new);

        MeetingDto meetingDto=new MeetingDto();
        meetingDto.setContent("test1");
        meetingDto.setParticipant("testp1");
        meetingDto.setName("testn1");
        meetingDto.setFolder_id(folder.getId());
        Long meetingId1 = meetingService.createMeeting(meetingDto, member.getId());

        MeetingDto meetingDto2=new MeetingDto();
        meetingDto2.setContent("test2");
        meetingDto2.setParticipant("testp1");
        meetingDto2.setName("testn1");
        meetingDto2.setFolder_id(folder.getId());
        Long meetingId2 = meetingService.createMeeting(meetingDto, member.getId());
        folderService.trashFolder(folder.getId());
       //when
        List<Long>list=new ArrayList<>();
        list.add(folder.getId());
        wasteBasketService.permanentlyDeleteFolder(list);
        em.flush();em.clear();
        Optional<Folder> opFolder = folderRepository.findFolderByName("test", member.getId());
        Optional<Meeting> opMeeting1 = meetingRepository.findById(meetingId1);
        Optional<Meeting> opMeeting2 = meetingRepository.findById(meetingId2);
        //then
        assertThat(opFolder.isPresent()).isFalse();
        assertThat(opMeeting1.isPresent()).isFalse();
        assertThat(opMeeting2.isPresent()).isFalse();




    }


}