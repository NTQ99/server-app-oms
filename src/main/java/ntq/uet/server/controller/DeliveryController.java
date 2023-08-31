package ntq.uet.server.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import ntq.uet.server.common.base.RequestContext;
import ntq.uet.server.common.base.ServiceHeader;
import ntq.uet.server.common.core.constant.CommonConstants;
import ntq.uet.server.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ntq.uet.server.common.exception.GlobalException;
import ntq.uet.server.model.entity.DeliveryUnit;
import ntq.uet.server.common.base.BaseResponse;
import ntq.uet.server.service.impl.DeliveryUnitService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryUnitService service;
    private final HttpServletRequest httpServletRequest;

    @PostMapping("/get")
    public ResponseEntity<?> getAll() {

        RequestContext ctx = RequestContext.init(httpServletRequest);

        String userId = ctx.getAuthenticationId();

        List<DeliveryUnit> deliveries = service.getAllDeliveryUnits(userId);

        return new ResponseEntity<>(new BaseResponse<>(deliveries, ErrorCode.OK), HttpStatus.OK);

    }

    @PostMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable("id") String id) {

        RequestContext ctx = RequestContext.init(httpServletRequest);

        String userId = ctx.getAuthenticationId();

        DeliveryUnit deliveryUnit = service.getDeliveryUnitById(id);

        if (!deliveryUnit.validateUser(userId)) throw new GlobalException(ErrorCode.UNAUTHORIZED);

        return new ResponseEntity<>(new BaseResponse<>(deliveryUnit, ErrorCode.OK), HttpStatus.OK);

    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody DeliveryUnit deliveryUnitData) {

        RequestContext ctx = RequestContext.init(httpServletRequest);

        String userId = ctx.getAuthenticationId();
        
        DeliveryUnit currDeliveryData = service.getDeliveryUnitByName(userId, deliveryUnitData.getDeliveryUnitName());

        if (currDeliveryData != null) {
            throw new GlobalException(ErrorCode.EXIST);
        }

        deliveryUnitData.setUserId(userId);
        BaseResponse<DeliveryUnit> response = new BaseResponse<>(service.createDeliveryUnit(deliveryUnitData), ErrorCode.CREATED);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id,
            @RequestBody DeliveryUnit newDeliveryData) {

        RequestContext ctx = RequestContext.init(httpServletRequest);

        String userId = ctx.getAuthenticationId();

        DeliveryUnit currDeliveryData = service.getDeliveryUnitById(id);

        if (currDeliveryData == null) {
            throw new GlobalException(ErrorCode.NOT_FOUND);
        }
        
        if (!currDeliveryData.validateUser(userId)) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED);
        }

        BaseResponse<DeliveryUnit> response = new BaseResponse<>(service.updateDeliveryUnit(id, newDeliveryData), ErrorCode.MODIFIED);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {

        RequestContext ctx = RequestContext.init(httpServletRequest);

        String userId = ctx.getAuthenticationId();

        DeliveryUnit currDeliveryData = service.getDeliveryUnitById(id);

        if (currDeliveryData == null) {
            throw new GlobalException(ErrorCode.NOT_FOUND);
        }
        
        if (!currDeliveryData.validateUser(userId)) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED);
        }

        service.deleteDeliveryUnit(id);
        BaseResponse<DeliveryUnit> response = new BaseResponse<>(null, ErrorCode.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteAll() {

        RequestContext ctx = RequestContext.init(httpServletRequest);

        String userId = ctx.getAuthenticationId();

        service.deleteAllDeliveryUnits(userId);
        BaseResponse<DeliveryUnit> response = new BaseResponse<>(null, ErrorCode.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}