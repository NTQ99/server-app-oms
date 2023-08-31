package ntq.uet.server.common.base;

import lombok.Getter;
import ntq.uet.server.common.exception.ErrorCode;
import org.springframework.web.context.request.WebRequest;

import ntq.uet.server.common.exception.GlobalException;

@Getter
public class BaseResponse<T> extends BaseObject {

    private MetaData meta;
    private Error error;
    private T data;

    public BaseResponse() {
    }

    public void setError(Error error) {
        this.error = error;
    }

    public void setMeta(MetaData meta) {
        this.meta = meta;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BaseResponse(T data, ErrorCode errorCode) {
        this.setData(data);
        this.setMeta(null);
        this.setError(
                new Error(errorCode.getCode(), errorCode.getMessage(), ""));
    }

    public BaseResponse(T data, int code, String msg, String desc) {
        this.setData(data);
        this.setMeta(null);
        this.setError(
                new Error(code, msg, desc));
    }

    public BaseResponse(T data, int currentPage, int totalPages, int perPage, long totalItems) {
        MetaData meta = new MetaData(currentPage + 1, totalPages, perPage, totalItems);
        this.setData(data);
        this.setMeta(meta);
        this.setError(new Error(100, "", ""));
    }

    public BaseResponse(GlobalException ex, WebRequest request) {
        this.setData(null);
        this.setMeta(null);
        Error message = new Error(ex.getErrorCode(),
                ex.getMessage(), request.getDescription(false));
        this.setError(message);
    }

}
