package ntq.uet.server.common.exception;

public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public GlobalException(String msg) {
        super(msg);
    }
}