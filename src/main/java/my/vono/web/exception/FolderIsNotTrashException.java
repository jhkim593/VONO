package my.vono.web.exception;

public class FolderIsNotTrashException extends RuntimeException{
	
	public FolderIsNotTrashException(String msg, Throwable t) {
        super(msg, t);
    }

    public FolderIsNotTrashException(String msg) {
        super(msg);
    }

    public FolderIsNotTrashException() {
        super();
    }

}
