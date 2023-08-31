package ntq.uet.server.common.base;

import lombok.Getter;

import java.util.Date;

@Getter
public class Error {

    private final int statusCode;
    private final Date timestamp;
    private final String message;
    private final String description;

    public Error(int statusCode, Date timestamp, String message, String description) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
        this.description = description;
    }

    public Error(int statusCode, String message, String description) {
        this.statusCode = statusCode;
        this.timestamp = new Date();
        this.message = message;
        this.description = description;
    }

}