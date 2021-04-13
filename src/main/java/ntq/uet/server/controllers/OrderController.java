package ntq.uet.server.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ntq.uet.server.models.BaseResponseModel;
import ntq.uet.server.models.order.OrderView;
import ntq.uet.server.services.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping
    public BaseResponseModel<List<OrderView>> getAll() {
        BaseResponseModel<List<OrderView>> response;
        List<OrderView> allOrders = new ArrayList<>();

        try {
            allOrders = service.getAllOrders();
        } catch (Exception e) {
            return new BaseResponseModel<>("err", "internal_server_error");
        }

        if (!allOrders.isEmpty()) {
            response = new BaseResponseModel<>("succ", "", allOrders);
        } else {
            response = new BaseResponseModel<>("err", "not_found");
        }

        return response;
    }

    @GetMapping("{id}")
    public BaseResponseModel<OrderView> getById(@PathVariable("id") String id) {
        BaseResponseModel<OrderView> response;
        OrderView order = service.getOrderById(id);

        if (order != null) {
            response = new BaseResponseModel<>("succ", "", order);
        } else {
            return new BaseResponseModel<>("err", "internal_server_error");
        }

        return response;
    }

    @PostMapping
    public BaseResponseModel<OrderView> create(@RequestBody OrderView order) {
        BaseResponseModel<OrderView> response;
        try {
            OrderView newOrder = service.createOrder(order);
            response = new BaseResponseModel<>("succ", "", newOrder);
        } catch (Exception e) {
            return new BaseResponseModel<>("err", "internal_server_error");
        }

        return response;
    }

    @PutMapping("{id}")
    public BaseResponseModel<OrderView> update(@PathVariable("id") String id, @RequestBody OrderView newOrderData) {
        BaseResponseModel<OrderView> response;

        try {
            OrderView currOrderData = service.getOrderById(id);
            if (currOrderData.equals(newOrderData)) {
                return new BaseResponseModel<>("err", "data_not_change", currOrderData);
            }
            OrderView newOrder = service.updateOrder(id, newOrderData);
            if (newOrder != null) {
                response = new BaseResponseModel<>("succ", "", newOrder);
            } else {
                response = new BaseResponseModel<>("err", "not_found");
            }
        } catch (Exception e) {
            return new BaseResponseModel<>("err", "internal_server_error");
        }

        return response;
    }

    @DeleteMapping("{id}")
    public BaseResponseModel<?> delete(@PathVariable("id") String id) {
        BaseResponseModel<?> response;

        try {
            service.deleteOrder(id);
            response = new BaseResponseModel<>("succ", "");
        } catch (Exception e) {
            return new BaseResponseModel<>("err", "internal_server_error");
        }

        return response;
    }

    @DeleteMapping
    public BaseResponseModel<?> deleteAll() {
        BaseResponseModel<?> response;

        try {
            service.deleteAllOrders();
            response = new BaseResponseModel<>("succ", "");
        } catch (Exception e) {
            return new BaseResponseModel<>("err", "internal_server_error");
        }

        return response;
    }
}