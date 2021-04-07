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
    public BaseResponseModel(){};
    public BaseResponseModel(String status, String message) {
        this.result = new Result(status, message);
    }
    public BaseResponseModel(String status, String message, T data) {
        this.result = new Result(status, message);
        this.data = data;
    }
    public T data;
    public Result result;
}
