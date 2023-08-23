package ntq.uet.server.payload;

import java.util.Date;

import ntq.uet.server.common.base.BaseObject;
import org.springframework.web.context.request.WebRequest;

import ntq.uet.server.exceptions.GlobalException;

public class BasePageResponse<T> extends BaseObject {

    private MetaData meta;
    private ErrorMessage error;
    private T data;

    public BasePageResponse() {
    }

    public ErrorMessage getError() {
        return error;
    }

    public void setError(ErrorMessage error) {
        this.error = error;
    }

    public MetaData getMeta() {
        return meta;
    }

    public void setMeta(MetaData meta) {
        this.meta = meta;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BasePageResponse(T data, String msg) {
        this.setData(data);
        this.setMeta(null);
        this.setError(
                new ErrorMessage(ErrorMessage.StatusCode.findByMessage(msg), new Date(), msg, ""));
    };

    public BasePageResponse(T data, int currentPage, int totalPages, int perPage, long totalItems) {
        MetaData meta = new MetaData(currentPage + 1, totalPages, perPage, totalItems);
        this.setData(data);
        this.setMeta(meta);
        this.setError(new ErrorMessage(ErrorMessage.StatusCode.OK.code, new Date(), "", ""));
    };

    public BasePageResponse(GlobalException ex, WebRequest request) {
        this.setData(null);
        this.setMeta(null);
        ErrorMessage message = new ErrorMessage(ErrorMessage.StatusCode.findByMessage(ex.getMessage()), new Date(),
                ex.getMessage(), request.getDescription(false));
        this.setError(message);
    };

}
