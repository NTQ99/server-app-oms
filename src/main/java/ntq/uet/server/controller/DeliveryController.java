package ntq.uet.server.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import ntq.uet.server.common.base.RequestContext;
import ntq.uet.server.common.base.ServiceHeader;
import ntq.uet.server.common.core.constant.CommonConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ntq.uet.server.common.exception.GlobalException;
import ntq.uet.server.model.entity.DeliveryUnit;
import ntq.uet.server.model.payload.BasePageResponse;
import ntq.uet.server.model.payload.ErrorMessage;
import ntq.uet.server.service.DeliveryUnitService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryUnitService service;
    private final HttpServletRequest httpServletRequest;

    @PostMapping("/get")
    public ResponseEntity<?> getAll() {

        RequestContext ctx = RequestContext.init((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));

        String userId = ctx.getAuthenticationId();

        List<DeliveryUnit> deliveries = service.getAllDeliveryUnits(userId);

        return new ResponseEntity<>(new BasePageResponse<>(deliveries, ErrorMessage.StatusCode.OK.message), HttpStatus.OK);

    }

    @PostMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable("id") String id) {

        RequestContext ctx = RequestContext.init((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));

        String userId = ctx.getAuthenticationId();

        DeliveryUnit deliveryUnit = service.getDeliveryUnitById(id);

        if (!deliveryUnit.validateUser(userId)) throw new GlobalException(ErrorMessage.StatusCode.UNAUTHORIZED.message);

        return new ResponseEntity<>(new BasePageResponse<>(deliveryUnit, ErrorMessage.StatusCode.OK.message), HttpStatus.OK);

    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody DeliveryUnit deliveryUnitData) {

        RequestContext ctx = RequestContext.init((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));

        String userId = ctx.getAuthenticationId();
        
        DeliveryUnit currDeliveryData = service.getDeliveryUnitByName(userId, deliveryUnitData.getDeliveryUnitName());

        if (currDeliveryData != null) {
            throw new GlobalException(ErrorMessage.StatusCode.EXIST.message);
        }

        deliveryUnitData.setUserId(userId);
        BasePageResponse<DeliveryUnit> response = new BasePageResponse<>(service.createDeliveryUnit(deliveryUnitData), ErrorMessage.StatusCode.CREATED.message);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id,
            @RequestBody DeliveryUnit newDeliveryData) {

        RequestContext ctx = RequestContext.init((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));

        String userId = ctx.getAuthenticationId();

        DeliveryUnit currDeliveryData = service.getDeliveryUnitById(id);

        if (currDeliveryData == null) {
            throw new GlobalException(ErrorMessage.StatusCode.NOT_FOUND.message);
        }
        
        if (!currDeliveryData.validateUser(userId)) {
            throw new GlobalException(ErrorMessage.StatusCode.UNAUTHORIZED.message);
        }

        BasePageResponse<DeliveryUnit> response = new BasePageResponse<>(service.updateDeliveryUnit(id, newDeliveryData), ErrorMessage.StatusCode.MODIFIED.message);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {

        RequestContext ctx = RequestContext.init((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));

        String userId = ctx.getAuthenticationId();

        DeliveryUnit currDeliveryData = service.getDeliveryUnitById(id);

        if (currDeliveryData == null) {
            throw new GlobalException(ErrorMessage.StatusCode.NOT_FOUND.message);
        }
        
        if (!currDeliveryData.validateUser(userId)) {
            throw new GlobalException(ErrorMessage.StatusCode.UNAUTHORIZED.message);
        }

        service.deleteDeliveryUnit(id);
        BasePageResponse<DeliveryUnit> response = new BasePageResponse<>(null, ErrorMessage.StatusCode.OK.message);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteAll() {

        RequestContext ctx = RequestContext.init((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));

        String userId = ctx.getAuthenticationId();

        service.deleteAllDeliveryUnits(userId);
        BasePageResponse<DeliveryUnit> response = new BasePageResponse<>(null, ErrorMessage.StatusCode.OK.message);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}