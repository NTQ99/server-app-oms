package ntq.uet.server.models;

class Result {
    public Result(String status2, String message2) {
        status = status2;
        message = message2;
    }
    public String status;
    public String message;
}

public class BaseResponseModel<T> {
    public BaseResponseModel(String status, String message) {
        result = new Result(status, message);
    }
    public T data;
    public Result result;
}
