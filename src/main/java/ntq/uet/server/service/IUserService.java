package ntq.uet.server.service;

import ntq.uet.server.common.base.BaseResponse;
import ntq.uet.server.common.base.RequestContext;
import ntq.uet.server.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    BaseResponse<?> createUser(RequestContext ctx);
    BaseResponse<?> getUser(RequestContext ctx, String userId);
    BaseResponse<?> getAllUsers(RequestContext ctx);
    BaseResponse<?> getUsers(RequestContext ctx);
    BaseResponse<?> filterUsers(RequestContext ctx);
    BaseResponse<?> updateUserPassword(RequestContext ctx, String userId);
    BaseResponse<?> updateUser(RequestContext ctx, String userId);
    BaseResponse<?> updateUserStatus(RequestContext ctx, String userId);
    BaseResponse<?> deleteUser(RequestContext ctx, String userId);
    BaseResponse<?> deleteAllUsers(RequestContext ctx);
}
