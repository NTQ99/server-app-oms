package ntq.uet.server.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import ntq.uet.server.exceptions.ResourceNotFoundException;
import ntq.uet.server.models.order.Order;
import ntq.uet.server.payload.BasePageRequest;
import ntq.uet.server.payload.BasePageResponse;
import ntq.uet.server.services.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping(consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasePageResponse<List<Order>>> getAll(
            @RequestBody MultiValueMap<String, String> requestObject) {

        BasePageRequest request = BasePageRequest.getObject(requestObject);
        List<Order> allOrders = new ArrayList<>();
        Pageable paging = PageRequest.of(request.getPagination().getPage() - 1, request.getPagination().getPerpage());
        Page<Order> pageOrders;

        if (request.getQuery().getGeneralSearch() == "" && request.getQuery().getStatus() == "") {
            if (request.getQuery().getCustomerCode() == "") {
                pageOrders = service.getAllOrders(paging);
            } else {
                pageOrders = service.getOrdersByCustomerCode(request.getQuery().getCustomerCode(), paging);
            }
        } else {
            pageOrders = service.getAllOrdersCondition(request.getQuery().getGeneralSearch(),
                    request.getQuery().getStatus(), paging);
        }

        allOrders = pageOrders.getContent();

        return new ResponseEntity<>(new BasePageResponse<>(allOrders, pageOrders.getNumber(),
                pageOrders.getTotalPages(), pageOrders.getSize(), pageOrders.getTotalElements()), HttpStatus.OK);

    }

    @GetMapping("{id}")
    public ResponseEntity<Order> getById(@PathVariable("id") String id) {

        Order order = service.getOrderById(id);

        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Not found Order for id " + id);
        }

    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order order) {

        Order newOrder = service.createOrder(order);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);

    }

    @PutMapping("{id}")
    public ResponseEntity<Order> update(@PathVariable("id") String id, @RequestBody Order newOrderData) {

        Order currOrderData = service.getOrderById(id);
        if (currOrderData == null) {
            throw new ResourceNotFoundException("Not found Order for id " + id);
        }

        if (currOrderData.equals(newOrderData)) {
            return new ResponseEntity<>(currOrderData, HttpStatus.NOT_MODIFIED);
        }
        Order newOrderModified = service.updateOrder(id, newOrderData);
        return new ResponseEntity<>(newOrderModified, HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {

        service.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {

        service.deleteAllOrders();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}