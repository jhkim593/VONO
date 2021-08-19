package my.vono.web.chatbot;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

	public String voiceOrder(String msg) {
		String returnMsg = ChatbotProc.request(msg);
		
		return returnMsg;
	}
}
