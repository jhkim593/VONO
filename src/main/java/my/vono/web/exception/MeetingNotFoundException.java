package my.vono.web.exception;

public class MeetingNotFoundException extends RuntimeException{
	
	public MeetingNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public MeetingNotFoundException(String msg) {
        super(msg);
    }

    public MeetingNotFoundException() {
        super();
    }

}
