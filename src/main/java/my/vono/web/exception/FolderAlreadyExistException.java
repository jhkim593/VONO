package my.vono.web.exception;

public class FolderAlreadyExistException extends RuntimeException{
	
	public FolderAlreadyExistException(String msg, Throwable t) {
        super(msg, t);
    }

    public FolderAlreadyExistException(String msg) {
        super(msg);
    }

    public FolderAlreadyExistException() {
        super();
    }

}
