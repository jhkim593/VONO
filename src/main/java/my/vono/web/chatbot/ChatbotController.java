package my.vono.web.chatbot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatbotController {

	private final ChatService chatService;
	
		@RequestMapping("sendMsg")
		@ResponseBody
		public String sendMsg(@RequestParam(value = "message", required = false) String message) {
			String returnMsg="";
			try {
				System.out.println("채팅창에 입력한 값----->" + message);
				returnMsg=chatService.voiceOrder(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return returnMsg;
		}

}
