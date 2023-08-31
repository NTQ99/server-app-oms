package ntq.uet.server.common.exception;

import lombok.AllArgsConstructor;
import ntq.uet.server.common.base.IErrorCode;

@AllArgsConstructor
public enum ErrorCode implements IErrorCode {
    OK(100, "Thành công"),
    NOT_FOUND(101, "Dữ liệu không tồn tại!"),
    CREATED(102, "Tạo dữ liệu thành công!"),
    EXIST(103, "Dữ liệu đã tồn tại!"),
    MODIFIED(104, "Dữ liệu đã được cập nhật!"),
    NOT_MODIFIED(105, "Dữ liệu không được cập nhật!"),
    NO_CONTENT(106, "Trống"),
    AUTH_SUCCESS(200, "Đăng nhập thành công!"),
    USER_NOT_FOUND(201, "Tài khoản không tồn tại!"),
    USER_WRONG_PASSWORD(202, "Sai mật khẩu!"),
    USER_LOCKED(203, "Tài khoản tạm thời bị khóa. Vui lòng liên hệ quản trị viên!"),
    USER_UNLOCKED(204, "Tài khoản đã được mở khóa!"),
    USER_BANNED(205, "Tài khoản đã bị chặn vĩnh viễn!"),
    USER_CREATED(206, "Tạo tài khoản thành công!"),
    USER_EXIST(207, "Tài khoản đã tồn tại!"),
    USER_MODIFIED(208, "Tài khoản đã được cập nhật!"),
    USER_NOT_MODIFIED(209, "Tài khoản không được cập nhật!"),
    USER_NOT_ROLE(210, "Tài khoản không có quyền truy cập!"),
    USER_UNAUTHORIZED(211, "Tài khoản thiếu token xác thực!"),
    OUT_OF_STOCK(300, "Số lượng sản phẩm đã hết!"),
    BAD_REQUEST(400, "Không thể xử lý!"),
    UNAUTHORIZED(401, "Mã xác thực không hợp lệ!"),
    REQUEST_TIMEOUT(408, "Quá thời gian xử lý!"),
    INTERNAL_SERVER_ERROR(500, "Lỗi không xác định");

    private final int code;
    private final String message;

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
