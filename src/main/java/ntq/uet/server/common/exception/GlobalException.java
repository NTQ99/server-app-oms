package ntq.uet.server.common.exception;

import lombok.Getter;

public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private ErrorCode errorCode;

    public GlobalException(String msg) {
        super(msg);
    }

    public GlobalException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return this.errorCode == null ? 500 : this.errorCode.getCode();
    }
}