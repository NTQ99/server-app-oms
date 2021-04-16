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
import ntq.uet.server.models.PageRequestModel;
import ntq.uet.server.models.PageResponseModel;
import ntq.uet.server.models.order.OrderModel;
import ntq.uet.server.services.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping(consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResponseModel<OrderModel>> getAll(
            @RequestBody MultiValueMap<String, String> requestObject) {

        PageRequestModel request = PageRequestModel.getObject(requestObject);
        List<OrderModel> allOrders = new ArrayList<>();
        Pageable paging = PageRequest.of(request.getPagination().getPage() - 1, request.getPagination().getPerpage());
        Page<OrderModel> pageOrders;

        if (request.getQuery().getGeneralSearch() == "" && request.getQuery().getStatus() == "") {
            pageOrders = service.getAllOrders(paging);
        } else {
            pageOrders = service.getAllOrdersCondition(request.getQuery().getGeneralSearch(),
                    request.getQuery().getStatus(), paging);
        }

        allOrders = pageOrders.getContent();

        return new ResponseEntity<>(new PageResponseModel<>(allOrders, pageOrders.getNumber(),
                pageOrders.getTotalPages(), pageOrders.getSize(), pageOrders.getTotalElements()), HttpStatus.OK);

    }

    @GetMapping("{id}")
    public ResponseEntity<OrderModel> getById(@PathVariable("id") String id) {

        OrderModel order = service.getOrderById(id);

        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Not found Order for id " + id);
        }

    }

    @PostMapping
    public ResponseEntity<OrderModel> create(@RequestBody OrderModel order) {

        OrderModel newOrder = service.createOrder(order);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);

    }

    @PutMapping("{id}")
    public ResponseEntity<OrderModel> update(@PathVariable("id") String id, @RequestBody OrderModel newOrderData) {

        OrderModel currOrderData = service.getOrderById(id);
        if (currOrderData == null) {
            throw new ResourceNotFoundException("Not found Order for id " + id);
        }

        if (currOrderData.equals(newOrderData)) {
            return new ResponseEntity<>(currOrderData, HttpStatus.NOT_MODIFIED);
        }
        OrderModel newOrderModified = service.updateOrder(id, newOrderData);
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