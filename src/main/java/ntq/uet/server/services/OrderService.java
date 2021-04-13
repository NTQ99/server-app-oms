package ntq.uet.server.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntq.uet.server.models.customer.CustomerModel;
import ntq.uet.server.models.order.OrderModel;
import ntq.uet.server.models.order.OrderView;
import ntq.uet.server.models.product.ProductModel;
import ntq.uet.server.repositories.OrderRepository;

@Service("orderService")
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductService productService;

    public OrderView createOrder(OrderView orderView) {
        OrderView orderResponse = new OrderView();
        CustomerModel customer = new CustomerModel();
        CustomerModel customerData;
        customer.setCustomerName(orderView.getCustomerName());
        customer.setCustomerPhone(orderView.getCustomerPhone());
        List<CustomerModel> customers = customerService.getCustomerByPhone(customer.getCustomerPhone());
        if (customers.size() == 0) {
            customerData = customerService.createCustomer(customer);
        } else {
            customerData = customers.get(0);
        }
        customerService.addCustomerAddress(customerData.getId(), orderView.getCustomerAddress());
        orderResponse.setCustomerDetail(customerData);

        ProductModel product = new ProductModel();
        ProductModel productData;
        product.setProductName(orderView.getProductName());
        List<ProductModel> products = productService.getProductByName(product.getProductName());
        if (products.size() == 0) {
            productData = productService.createProduct(product);
        } else {
            productData = products.get(0);
        }
        orderResponse.setProductDetail(productData);

        OrderModel order = new OrderModel();
        order.setCustomerCode(customerData.getCustomerCode());
        order.setProductCode(productData.getProductCode());
        order.setDeliveryTo(orderView.getCustomerAddress());
        order.setDeliveryUnit(orderView.getDeliveryUnit());
        order.setShipFee(orderView.getShipFee());
        order.setQuantity(orderView.getQuantity());
        order.setStatus(orderView.getStatus());
        OrderModel orderData = orderRepository.save(order);
        orderResponse.setOrderDetail(orderData);

        return orderResponse;
    }

    public OrderView getOrderById(String id) {
        OrderView orderResponse = new OrderView();
        OrderModel orderData = orderRepository.findById(id).orElse(null);
        if (orderData == null) {
            return null;
        }
        orderResponse.setOrderDetail(orderData);
        orderResponse.setCustomerDetail(customerService.getCustomerByCode(orderData.getCustomerCode()));
        orderResponse.setProductDetail(productService.getProductByCode(orderData.getProductCode()));

        return orderResponse;
    }

    public OrderView getOrderByCode(String code) {
        OrderView orderResponse = new OrderView();
        OrderModel orderData = orderRepository.findByOrderCode(code);
        if (orderData == null) {
            return null;
        }
        orderResponse.setOrderDetail(orderData);
        orderResponse.setCustomerDetail(customerService.getCustomerByCode(orderData.getCustomerCode()));
        orderResponse.setProductDetail(productService.getProductByCode(orderData.getProductCode()));
        
        return orderResponse;
    }

    public List<OrderView> getAllOrders() {
        List<OrderView> ordersResponse = new ArrayList<>();
        List<OrderModel> ordersData = orderRepository.findAll();
        if (ordersData == null) {
            return null;
        }
        for (OrderModel orderData : ordersData) {
            OrderView orderResponse = new OrderView();
            orderResponse.setOrderDetail(orderData);
            orderResponse.setCustomerDetail(customerService.getCustomerByCode(orderData.getCustomerCode()));
            orderResponse.setProductDetail(productService.getProductByCode(orderData.getProductCode()));
            ordersResponse.add(orderResponse);
        }
        
        return ordersResponse;
    }

    public OrderView updateOrder(String id, OrderView newOrderViewData) {
        OrderModel orderData = orderRepository.findById(id).orElse(null);
        if (orderData == null) {
            return null;
        }

        CustomerModel customerData = customerService.getCustomerByCode(orderData.getCustomerCode());
        customerService.addCustomerAddress(customerData.getId(), newOrderViewData.getCustomerAddress());

        orderData.setDeliveryTo(newOrderViewData.getCustomerAddress());
        orderRepository.save(orderData);
        return newOrderViewData;
    }

    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }

    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }

}
