package my.vono.web.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import my.vono.web.model.meeting.MeetingDAO;

@Service
@RequiredArgsConstructor
public class MeetingService {

	
	private final MeetingDAO meetingDAO;
	
	
}
