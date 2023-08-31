package ntq.uet.server.service;

import ntq.uet.server.common.base.BaseResponse;
import ntq.uet.server.common.base.RequestContext;
import ntq.uet.server.model.entity.DeliveryUnit;

import java.util.List;
import java.util.stream.Collectors;

public interface IDeliveryUnitService {
    BaseResponse<?> createDeliveryUnit(RequestContext ctx);
    BaseResponse<?> getAllDeliveryUnits(RequestContext ctx);
    BaseResponse<?> getDeliveryUnits(RequestContext ctx);
    BaseResponse<?> filterDeliveryUnits(RequestContext ctx);
    BaseResponse<?> getDeliveryUnit(RequestContext ctx, String deliveryUnitId);
    BaseResponse<?> updateDeliveryUnit(RequestContext ctx, String deliveryUnitId);
    BaseResponse<?> deleteDeliveryUnit(RequestContext ctx, String deliveryUnitId);
    BaseResponse<?> deleteAllDeliveryUnits(RequestContext ctx);
}
