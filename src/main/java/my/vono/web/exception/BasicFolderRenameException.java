package my.vono.web.exception;

public class BasicFolderRenameException extends RuntimeException{
	
	public BasicFolderRenameException(String msg, Throwable t) {
        super(msg, t);
    }

    public BasicFolderRenameException(String msg) {
        super(msg);
    }

    public BasicFolderRenameException() {
        super();
    }

}
