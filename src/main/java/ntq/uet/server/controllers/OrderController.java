package ntq.uet.server.controllers;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ntq.uet.server.exceptions.GlobalException;
import ntq.uet.server.models.Delivery;
import ntq.uet.server.models.Order;
import ntq.uet.server.payload.BasePageResponse;
import ntq.uet.server.payload.ErrorMessage;
import ntq.uet.server.security.jwt.JwtUtils;
import ntq.uet.server.services.OrderService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;
    private final HttpServletRequest httpServletRequest;

    @PostMapping("/get")
    public ResponseEntity<BasePageResponse<List<Order>>> getAll() {

        RequestContext ctx = RequestContext.init((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));

        String userId = ctx.getAuthenticationId();

        List<Order> orders = service.getAllOrders(userId);

        return new ResponseEntity<>(new BasePageResponse<>(orders, ErrorMessage.StatusCode.OK.message), HttpStatus.OK);

    }

    @PostMapping("/get/{id}")
    public ResponseEntity<BasePageResponse<Order>> getById(@PathVariable("id") String id) {

        RequestContext ctx = RequestContext.init((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));

        String userId = ctx.getAuthenticationId();

        Order order = service.getOrderById(id);

        if (!order.validateUser(userId)) throw new GlobalException(ErrorMessage.StatusCode.UNAUTHORIZED.message);

        return new ResponseEntity<>(new BasePageResponse<>(order, ErrorMessage.StatusCode.OK.message), HttpStatus.OK);

    }

    @PostMapping("/get/customer/{id}")
    public ResponseEntity<BasePageResponse<List<Order>>> getByCustomerId(@PathVariable("id") String id) {

        RequestContext ctx = RequestContext.init((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));

        String userId = ctx.getAuthenticationId();

        List<Order> orders = service.getOrderByCustomerId(id);

        for (Order order: orders) {
            if (!order.validateUser(userId)) throw new GlobalException(ErrorMessage.StatusCode.UNAUTHORIZED.message);
        }

        return new ResponseEntity<>(new BasePageResponse<>(orders, ErrorMessage.StatusCode.OK.message), HttpStatus.OK);

    }

    @PostMapping("/create")
    public ResponseEntity<BasePageResponse<Order>> create(@RequestBody Order orderData) {

        RequestContext ctx = RequestContext.init((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));

        String userId = ctx.getAuthenticationId();
        
        orderData.setUserId(userId);
        BasePageResponse<Order> response = new BasePageResponse<>(service.createOrder(orderData), ErrorMessage.StatusCode.CREATED.message);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /**
     * 
     * @param userId id của tài khoản sở hữu
     * @param orderData Thông tin đơn hàng
     * @return Thông tin đơn hàng đã tạo
     */
    @PostMapping("/create/{idUser}")
    public ResponseEntity<BasePageResponse<Order>> openAPICreate(@PathVariable("idUser") String userId, @RequestBody Order orderData) {

        orderData.setUserId(userId);
        BasePageResponse<Order> response = new BasePageResponse<>(service.createOrder(orderData), ErrorMessage.StatusCode.CREATED.message);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/update/{id}")
    public ResponseEntity<BasePageResponse<Order>> update(@PathVariable("id") String id,
            @RequestBody Order newOrderData) {

        RequestContext ctx = RequestContext.init((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));

        String userId = ctx.getAuthenticationId();

        Order currOrderData = service.getOrderById(id);

        if (currOrderData == null) {
            throw new GlobalException(ErrorMessage.StatusCode.NOT_FOUND.message);
        }
        
        if (!currOrderData.validateUser(userId)) {
            throw new GlobalException(ErrorMessage.StatusCode.UNAUTHORIZED.message);
        }

        BasePageResponse<Order> response = new BasePageResponse<>(service.updateOrder(currOrderData, newOrderData), ErrorMessage.StatusCode.MODIFIED.message);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<BasePageResponse<?>> delete(@PathVariable("id") String id) {

        RequestContext ctx = RequestContext.init((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));

        String userId = ctx.getAuthenticationId();

        Order currDeliveryData = service.getOrderById(id);

        if (currDeliveryData == null) {
            throw new GlobalException(ErrorMessage.StatusCode.NOT_FOUND.message);
        }
        
        if (!currDeliveryData.validateUser(userId)) {
            throw new GlobalException(ErrorMessage.StatusCode.UNAUTHORIZED.message);
        }

        service.deleteOrder(id);
        BasePageResponse<?> response = new BasePageResponse<>(null, ErrorMessage.StatusCode.OK.message);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/delete")
    public ResponseEntity<BasePageResponse<?>> deleteAll() {

        RequestContext ctx = RequestContext.init((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));

        String userId = ctx.getAuthenticationId();

        service.deleteAllOrders(userId);
        BasePageResponse<?> response = new BasePageResponse<>(null, ErrorMessage.StatusCode.OK.message);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/delivery/{id}")
    public ResponseEntity<BasePageResponse<Order>> sendOrder(@PathVariable("id") String id,
            @RequestBody Delivery delivery) {

        RequestContext ctx = RequestContext.init((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));

        String userId = ctx.getAuthenticationId();

        Order order = service.getOrderById(id);

        if (order == null) {
            throw new GlobalException(ErrorMessage.StatusCode.NOT_FOUND.message);
        }

        if (!order.validateUser(userId)) {
            throw new GlobalException(ErrorMessage.StatusCode.UNAUTHORIZED.message);
        }

        BasePageResponse<Order> response = new BasePageResponse<>(service.sendOrder(order, delivery), ErrorMessage.StatusCode.OK.message);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/update/status/{id}")
    public ResponseEntity<BasePageResponse<?>> updateStatus(@PathVariable("id") String id,
            @RequestBody(required = false) Object request) {

        RequestContext ctx = RequestContext.init((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));

        String userId = ctx.getAuthenticationId();

        Order order = service.getOrderById(id);

        if (order == null) {
            throw new GlobalException(ErrorMessage.StatusCode.NOT_FOUND.message);
        }

        if (!order.validateUser(userId)) {
            throw new GlobalException(ErrorMessage.StatusCode.UNAUTHORIZED.message);
        }

        if (request == null) {
            service.updateOrderStatus(order, null);
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> map = objectMapper.convertValue(request, new TypeReference<Map<String, String>>() {});
            service.updateOrderStatus(order, map.get("status"));
        }
        BasePageResponse<?> response = new BasePageResponse<>(null, ErrorMessage.StatusCode.OK.message);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/delivery/print/{id}")
    public ResponseEntity<BasePageResponse<String>> printOrder(@PathVariable("id") String id) {

        RequestContext ctx = RequestContext.init((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));

        String userId = ctx.getAuthenticationId();

        Order order = service.getOrderById(id);

        if (order == null) {
            throw new GlobalException(ErrorMessage.StatusCode.NOT_FOUND.message);
        }

        if (!order.validateUser(userId)) {
            throw new GlobalException(ErrorMessage.StatusCode.UNAUTHORIZED.message);
        }

        BasePageResponse<String> response = new BasePageResponse<>(service.getPrintOrdersLink(Arrays.asList(order)),
                ErrorMessage.StatusCode.OK.message);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/delivery/print")
    public ResponseEntity<BasePageResponse<String>> printAllOrder(@RequestBody(required = false) Object request) {

        RequestContext ctx = RequestContext.init((ServiceHeader) httpServletRequest.getAttribute(CommonConstants.SERVICE_HEADER));

        String userId = ctx.getAuthenticationId();

        ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> map = objectMapper.convertValue(request, new TypeReference<Map<String, String>>() {});
        String type = map.get("type");

        List<Order> orders = service.getAllOrders(userId);

        if (orders == null) {
            throw new GlobalException(ErrorMessage.StatusCode.NOT_FOUND.message);
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

        BasePageResponse<String> response = new BasePageResponse<>(service.getPrintOrdersLink(orders),
                ErrorMessage.StatusCode.OK.message);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}