package ntq.uet.server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ntq.uet.server.models.customer.CustomerModel;
import ntq.uet.server.models.order.OrderModel;
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

    public OrderModel createOrder(OrderModel orderData) {
        CustomerModel customer = new CustomerModel(orderData.getCustomerName(), orderData.getCustomerPhone());
        CustomerModel customerData = customerService.getCustomerByPhone(customer.getCustomerPhone());
        if (customerData == null) {
            customerData = customerService.createCustomer(customer);
        }
        customerService.addCustomerAddress(customerData.getId(), orderData.getDeliveryTo());
        orderData.setCustomerCode(customerData.getCustomerCode());

        ProductModel product = new ProductModel();
        ProductModel productData = productService.getProductByName(product.getProductName());
        if (productData == null) {
            productData = productService.createProduct(product);
        }
        product.setProductName(orderData.getProductName());
        orderData.setProductCode(productData.getProductCode());

        return orderRepository.save(orderData);
    }

    public OrderModel getOrderById(String id) {
        return orderRepository.findById(id).orElse(null);
    }

    public OrderModel getOrderByCode(String code) {
        return orderRepository.findByOrderCode(code);
    }

    public Page<OrderModel> getOrderByCreatedDateBetween(String createdDateFrom, String createdDateTo, Pageable paging) {
        return orderRepository.findByCreatedAtBetween(createdDateFrom, createdDateTo, paging);
    }

    public Page<OrderModel> getOrderByStatus(String status, Pageable paging) {
        return orderRepository.findByStatus(status, paging);
    }

    public Page<OrderModel> findOrderByCustomerName(String name, Pageable paging) {
        return orderRepository.findByCustomerNameContainingIgnoreCase(name, paging);
    }

    public Page<OrderModel> findOrderByCustomerPhone(String phone, Pageable paging) {
        return orderRepository.findByCustomerPhoneContaining(phone, paging);
    }

    public Page<OrderModel> findOrderByCustomerNameAndStatus(String name, String status, Pageable paging) {
        return orderRepository.findByCustomerNameContainingIgnoreCaseAndStatus(name, status, paging);
    }

    public Page<OrderModel> findOrderByCustomerPhoneAndStatus(String phone, String status, Pageable paging) {
        return orderRepository.findByCustomerPhoneContainingAndStatus(phone, status, paging);
    }

    public Page<OrderModel> getAllOrders(Pageable paging) {
        return orderRepository.findAll(paging);
    }

    public Page<OrderModel> getAllOrdersCondition(String searchValue, String status, Pageable paging) {
        if (searchValue == null) {
            return this.getOrderByStatus(status, paging);
        } else {
            try {
                Long.parseLong(searchValue);
                if (status == null) {
                    return this.findOrderByCustomerPhone(searchValue, paging);
                } else {
                    return this.findOrderByCustomerPhoneAndStatus(searchValue, status, paging);
                }
            } catch (NumberFormatException nfe) {
                if (status == null) {
                    return this.findOrderByCustomerName(searchValue, paging);
                } else {
                    return this.findOrderByCustomerNameAndStatus(searchValue, status, paging);
                }
            }
            
        }
    }

    public OrderModel updateOrder(String id, OrderModel newOrderModelData) {
        OrderModel orderData = orderRepository.findById(id).orElse(null);
        if (orderData == null) {
            return null;
        }

        if (!orderData.getCustomerPhone().equals(newOrderModelData.getCustomerPhone())) {
            CustomerModel newCustomerData = customerService.getCustomerByPhone(newOrderModelData.getCustomerPhone());
            if (newCustomerData == null) {
                newCustomerData = new CustomerModel(newOrderModelData.getCustomerName(), newOrderModelData.getCustomerPhone());
                orderData.setCustomerCode(customerService.createCustomer(newCustomerData).getCustomerCode());
            } else {
                orderData.setCustomerCode(newCustomerData.getCustomerCode());
            }
            orderData.setCustomerName(newOrderModelData.getCustomerName());
            orderData.setCustomerPhone(newOrderModelData.getCustomerPhone());
        }

        if (!orderData.getDeliveryTo().equals(newOrderModelData.getDeliveryTo())) {
            CustomerModel customerData = customerService.getCustomerByCode(orderData.getCustomerCode());
            customerService.addCustomerAddress(customerData.getId(), newOrderModelData.getDeliveryTo());
            orderData.setDeliveryTo(newOrderModelData.getDeliveryTo());
        }

        if (!orderData.getProductName().equals(newOrderModelData.getProductName())) {
            ProductModel newProductData = productService.getProductByName(newOrderModelData.getProductName());
            if (newProductData == null) {
                newProductData = new ProductModel();
                newProductData.setProductName(newOrderModelData.getProductName());
                orderData.setProductCode(productService.createProduct(newProductData).getProductCode());
            } else {
                orderData.setProductCode(newProductData.getProductCode());
            }
            orderData.setProductName(newOrderModelData.getProductName());
        }

        orderData.setDeliveryUnit(newOrderModelData.getDeliveryUnit());
        orderData.setQuantity(newOrderModelData.getQuantity());
        orderData.setShipFee(newOrderModelData.getShipFee());
        orderData.setStatus(newOrderModelData.getStatus());

        return orderRepository.save(orderData);
    }

    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }

    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }

}
