package my.vono.web.excelUtile;

public class MeetingLogVO {
	String speaker , content ,time;
	
	public MeetingLogVO(String speaker, String content, String time) {
		super();
		this.speaker = speaker;
		this.content = content;
		this.time = time;
	}

	public MeetingLogVO() {
		// TODO Auto-generated constructor stub
	}

	public String getSpeaker() {
		return speaker;
	}

	public void setSpeaker(String speaker) {
		if(speaker!= null)this.speaker = speaker;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		if(content!= null)this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		if(time!= null) this.time = time;
	}
	
	
}
