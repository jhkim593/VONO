package my.vono.web.exception;

public class FolderNotFoundException extends RuntimeException{
	
	public FolderNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public FolderNotFoundException(String msg) {
        super(msg);
    }

    public FolderNotFoundException() {
        super();
    }

}
