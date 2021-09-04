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
import my.vono.web.model.meeting.MeetingSimpleDto;
import my.vono.web.model.user.MemberRepository;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MeetingServiceTest {
    @Autowired
    EntityManager em;

    @Autowired
    FolderService folderService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MeetingService meetingService;

    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    FolderRepository folderRepository;


    @BeforeEach
    public void beforeEach()throws Exception{
        Member member1=Member.createMemeber("1", "1", null, "test", "1", null, null);
        em.persist(member1);
        folderService.createFolder(member1.getId(),"Basic");
    }
    @Test
    public void createMeeting()throws Exception{
       //given
        Member member = memberRepository.findByLoginId("1").orElseThrow(MemberNotFoundException::new);

        MeetingDto meetingDto=new MeetingDto();
        meetingDto.setContent("test1");
        meetingDto.setFolderName("Basic");
        meetingDto.setParticipant("testp1");
        meetingDto.setName("testn1");
        //when
        Long meetingId = meetingService.createMeeting(meetingDto, member.getId());
        em.flush();em.clear();
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(MeetingNotFoundException::new);
        //then
        assertThat(meeting.getName()).isEqualTo("testn1");
        assertThat(meeting.getFolder().getName()).isEqualTo("Basic");
        assertThat(meeting.getIs_trash()).isFalse();
        assertThat(meeting.getParticipant()).isEqualTo("testp1");
    }
    /**
     * meeting삭제시 폴더와 연관관계 끊음**/
    @Test
    public void deleteMeeting()throws Exception{
       //given
        Member member = memberRepository.findByLoginId("1").orElseThrow(MemberNotFoundException::new);

        MeetingDto meetingDto=new MeetingDto();
        meetingDto.setContent("test1");
        meetingDto.setFolderName("Basic");
        meetingDto.setParticipant("testp1");
        meetingDto.setName("testn1");
       //when
        Long meetingId = meetingService.createMeeting(meetingDto, member.getId());
        em.flush();em.clear();
        meetingService.deleteMeeting(meetingId);
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(MeetingNotFoundException::new);
        Folder folder = folderRepository.findFolderByName("Basic", member.getId()).orElseThrow(FolderNotFoundException::new);
        //then
        assertThat(meeting.getIs_trash()).isTrue();
        assertThat(meeting.getFolder()).isNull();
        assertThat(meeting.getOri_folderId()).isEqualTo(folder.getId());
        assertThat(meeting.getMember().getId()).isEqualTo(member.getId());
    }
    /**
     * meeting 다른 폴더로 이동
     */

    @Test
    public void moveMeeting()throws Exception{
        //given

        Member member = memberRepository.findByLoginId("1").orElseThrow(MemberNotFoundException::new);
        folderService.createFolder(member.getId(),"test");
        Folder moveFolder = folderRepository.findFolderByName("test", member.getId()).orElseThrow(FolderNotFoundException::new);

        MeetingDto meetingDto=new MeetingDto();
        meetingDto.setContent("test1");
        meetingDto.setFolderName("Basic");
        meetingDto.setParticipant("testp1");
        meetingDto.setName("testn1");
        Long meetingId = meetingService.createMeeting(meetingDto, member.getId());
        //when
        meetingService.moveMeeting(meetingId,moveFolder.getId());
        em.flush(); em.clear();
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(MeetingNotFoundException::new);
        Folder folder = folderRepository.findFolderByName("Basic", member.getId()).orElseThrow(FolderNotFoundException::new);
        //then
        assertThat(meeting.getFolder().getId()).isEqualTo(moveFolder.getId());
        assertThat(meeting.getOri_folderId()).isEqualTo(moveFolder.getId());
        assertThat(meeting.getIs_trash()).isFalse();
        assertThat(folder.getMeetings().size()).isEqualTo(0);

    }
    @Test
    public void searchMeeting()throws Exception{
       //given
        Member member = memberRepository.findByLoginId("1").orElseThrow(MemberNotFoundException::new);
        folderService.createFolder(member.getId(),"test");
        Folder moveFolder = folderRepository.findFolderByName("test", member.getId()).orElseThrow(FolderNotFoundException::new);

        MeetingDto meetingDto=new MeetingDto();
        meetingDto.setContent("test1");
        meetingDto.setFolderName("Basic");
        meetingDto.setParticipant("testp1");
        meetingDto.setName("testn1");
        Long meetingId1 = meetingService.createMeeting(meetingDto, member.getId());

        MeetingDto meetingDto2=new MeetingDto();
        meetingDto2.setContent("test1");
        meetingDto2.setFolderName("Basic");
        meetingDto2.setParticipant("testp1");
        meetingDto2.setName("testn2");
        Long meetingId2 = meetingService.createMeeting(meetingDto2, member.getId());

        MeetingDto meetingDto3=new MeetingDto();
        meetingDto3.setContent("test1");
        meetingDto3.setFolderName("Basic");
        meetingDto3.setParticipant("testp1");
        meetingDto3.setName("testn3");
        Long meetingId3 = meetingService.createMeeting(meetingDto3, member.getId());
       em.flush();em.clear();
       //when
        List<MeetingSimpleDto> testn1 = meetingService.searchMeeting(member.getId(), "testn1");
        List<MeetingSimpleDto> testn2 = meetingService.searchMeeting(member.getId(), "test");



        //then
        assertThat(testn1.get(0).getName()).isEqualTo("testn1");
        assertThat(testn1.size()).isEqualTo(1);
        assertThat(testn1.get(0).getId()).isEqualTo(meetingId1);
        assertThat(testn2.size()).isEqualTo(3);
        assertThat(testn2.get(0).getId()).isEqualTo(meetingId1);
        assertThat(testn2.get(1).getId()).isEqualTo(meetingId2);
        assertThat(testn2.get(2).getId()).isEqualTo(meetingId3);

    }


}