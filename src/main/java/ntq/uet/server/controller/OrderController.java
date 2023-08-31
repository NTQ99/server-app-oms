package ntq.uet.server.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import ntq.uet.server.common.base.RequestContext;
import ntq.uet.server.common.base.ServiceHeader;
import ntq.uet.server.common.core.constant.CommonConstants;
import ntq.uet.server.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ntq.uet.server.common.exception.GlobalException;
import ntq.uet.server.model.common.Delivery;
import ntq.uet.server.model.entity.Order;
import ntq.uet.server.common.base.BaseResponse;
import ntq.uet.server.service.impl.OrderService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;
    private final HttpServletRequest httpServletRequest;

    @PostMapping("/get")
    public ResponseEntity<BaseResponse<List<Order>>> getAll() {

        RequestContext ctx = RequestContext.init(httpServletRequest);

        String userId = ctx.getAuthenticationId();

        List<Order> orders = service.getAllOrders(userId);

        return new ResponseEntity<>(new BaseResponse<>(orders, ErrorCode.OK), HttpStatus.OK);

    }

    @PostMapping("/get/{id}")
    public ResponseEntity<BaseResponse<Order>> getById(@PathVariable("id") String id) {

        RequestContext ctx = RequestContext.init(httpServletRequest);

        String userId = ctx.getAuthenticationId();

        Order order = service.getOrderById(id);

        if (!order.validateUser(userId)) throw new GlobalException(ErrorCode.UNAUTHORIZED);

        return new ResponseEntity<>(new BaseResponse<>(order, ErrorCode.OK), HttpStatus.OK);

    }

    @PostMapping("/get/customer/{id}")
    public ResponseEntity<BaseResponse<List<Order>>> getByCustomerId(@PathVariable("id") String id) {

        RequestContext ctx = RequestContext.init(httpServletRequest);

        String userId = ctx.getAuthenticationId();

        List<Order> orders = service.getOrderByCustomerId(id);

        for (Order order: orders) {
            if (!order.validateUser(userId)) throw new GlobalException(ErrorCode.UNAUTHORIZED);
        }

        return new ResponseEntity<>(new BaseResponse<>(orders, ErrorCode.OK), HttpStatus.OK);

    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<Order>> create(@RequestBody Order orderData) {

        RequestContext ctx = RequestContext.init(httpServletRequest);

        String userId = ctx.getAuthenticationId();
        
        orderData.setUserId(userId);
        BaseResponse<Order> response = new BaseResponse<>(service.createOrder(orderData), ErrorCode.CREATED);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /**
     * 
     * @param userId id của tài khoản sở hữu
     * @param orderData Thông tin đơn hàng
     * @return Thông tin đơn hàng đã tạo
     */
    @PostMapping("/create/{idUser}")
    public ResponseEntity<BaseResponse<Order>> openAPICreate(@PathVariable("idUser") String userId, @RequestBody Order orderData) {

        orderData.setUserId(userId);
        BaseResponse<Order> response = new BaseResponse<>(service.createOrder(orderData), ErrorCode.CREATED);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/update/{id}")
    public ResponseEntity<BaseResponse<Order>> update(@PathVariable("id") String id,
                                                      @RequestBody Order newOrderData) {

        RequestContext ctx = RequestContext.init(httpServletRequest);

        String userId = ctx.getAuthenticationId();

        Order currOrderData = service.getOrderById(id);

        if (currOrderData == null) {
            throw new GlobalException(ErrorCode.NOT_FOUND);
        }
        
        if (!currOrderData.validateUser(userId)) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED);
        }

        BaseResponse<Order> response = new BaseResponse<>(service.updateOrder(currOrderData, newOrderData), ErrorCode.MODIFIED);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<?>> delete(@PathVariable("id") String id) {

        RequestContext ctx = RequestContext.init(httpServletRequest);

        String userId = ctx.getAuthenticationId();

        Order currDeliveryData = service.getOrderById(id);

        if (currDeliveryData == null) {
            throw new GlobalException(ErrorCode.NOT_FOUND);
        }
        
        if (!currDeliveryData.validateUser(userId)) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED);
        }

        service.deleteOrder(id);
        BaseResponse<?> response = new BaseResponse<>(null, ErrorCode.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/delete")
    public ResponseEntity<BaseResponse<?>> deleteAll() {

        RequestContext ctx = RequestContext.init(httpServletRequest);

        String userId = ctx.getAuthenticationId();

        service.deleteAllOrders(userId);
        BaseResponse<?> response = new BaseResponse<>(null, ErrorCode.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/delivery/{id}")
    public ResponseEntity<BaseResponse<Order>> sendOrder(@PathVariable("id") String id,
                                                         @RequestBody Delivery delivery) {

        RequestContext ctx = RequestContext.init(httpServletRequest);

        String userId = ctx.getAuthenticationId();

        Order order = service.getOrderById(id);

        if (order == null) {
            throw new GlobalException(ErrorCode.NOT_FOUND);
        }

        if (!order.validateUser(userId)) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED);
        }

        BaseResponse<Order> response = new BaseResponse<>(service.sendOrder(order, delivery), ErrorCode.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/update/status/{id}")
    public ResponseEntity<BaseResponse<?>> updateStatus(@PathVariable("id") String id,
                                                        @RequestBody(required = false) Object request) {

        RequestContext ctx = RequestContext.init(httpServletRequest);

        String userId = ctx.getAuthenticationId();

        Order order = service.getOrderById(id);

        if (order == null) {
            throw new GlobalException(ErrorCode.NOT_FOUND);
        }

        if (!order.validateUser(userId)) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED);
        }

        if (request == null) {
            service.updateOrderStatus(order, null);
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> map = objectMapper.convertValue(request, new TypeReference<Map<String, String>>() {});
            service.updateOrderStatus(order, map.get("status"));
        }
        BaseResponse<?> response = new BaseResponse<>(null, ErrorCode.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/delivery/print/{id}")
    public ResponseEntity<BaseResponse<String>> printOrder(@PathVariable("id") String id) {

        RequestContext ctx = RequestContext.init(httpServletRequest);

        String userId = ctx.getAuthenticationId();

        Order order = service.getOrderById(id);

        if (order == null) {
            throw new GlobalException(ErrorCode.NOT_FOUND);
        }

        if (!order.validateUser(userId)) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED);
        }

        BaseResponse<String> response = new BaseResponse<>(service.getPrintOrdersLink(Arrays.asList(order)),
                ErrorCode.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/delivery/print")
    public ResponseEntity<BaseResponse<String>> printAllOrder(@RequestBody(required = false) Object request) {

        RequestContext ctx = RequestContext.init(httpServletRequest);

        String userId = ctx.getAuthenticationId();

        ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> map = objectMapper.convertValue(request, new TypeReference<Map<String, String>>() {});
        String type = map.get("type");

        List<Order> orders = service.getAllOrders(userId);

        if (orders == null) {
            throw new GlobalException(ErrorCode.NOT_FOUND);
        }

        orders = orders.stream()
                .filter(order -> order.getStatus().equals(Order.Status.await_trans))
                .collect(Collectors.toList());
                
        if (type == "new") {
            orders = orders.stream()
                .filter(order -> !order.isPrinted())
                .collect(Collectors.toList());
        }

        if (orders.isEmpty()) {
            throw new GlobalException("Tất cả các đơn hàng đã được in phiếu!");
        }

        BaseResponse<String> response = new BaseResponse<>(service.getPrintOrdersLink(orders),
                ErrorCode.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}