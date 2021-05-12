package ntq.uet.server.exceptions;

public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public GlobalException(String msg) {
        super(msg);
    }
}