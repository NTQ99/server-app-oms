package ntq.uet.server.service;

import ntq.uet.server.common.base.BaseResponse;
import ntq.uet.server.common.base.RequestContext;

public interface IAuthService {
    BaseResponse<?> authenticateUser(RequestContext ctx);
    BaseResponse<?> registerUser(RequestContext ctx);
}
