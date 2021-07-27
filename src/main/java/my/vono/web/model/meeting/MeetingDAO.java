package my.vono.web.model.meeting;

import org.springframework.data.jpa.repository.JpaRepository;

import my.vono.web.entity.Meeting;


public interface MeetingDAO extends JpaRepository<Meeting,Long>{
	
}
