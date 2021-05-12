package ntq.uet.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ntq.uet.server.models.customer.Customer;
import ntq.uet.server.models.order.Order;
import ntq.uet.server.models.product.Product;
import ntq.uet.server.repositories.OrderRepository;

@Service("orderService")
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductService productService;

    public Order createOrder(Order orderData) {
        Customer customer = new Customer(orderData.getCustomerName(), orderData.getCustomerPhone());
        Customer customerData = customerService.getCustomerByPhone(customer.getCustomerPhone());
        if (customerData == null) {
            customerData = customerService.createCustomer(customer);
        }
        customerService.addCustomerAddress(customerData.getId(), orderData.getDeliveryTo());
        orderData.setCustomerCode(customerData.getCustomerCode());

        Product product = new Product();
        product.setProductName(orderData.getProductName());
        Product productData = productService.getProductByName(product.getProductName());
        if (productData == null) {
            productData = productService.createProduct(product);
        }
        orderData.setProductCode(productData.getProductCode());

        return orderRepository.save(orderData);
    }

    public Order getOrderById(String id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order getOrderByCode(String code) {
        return orderRepository.findByOrderCode(code);
    }

    public Page<Order> getOrdersByCustomerCode(String customerCode, Pageable paging) {
        return orderRepository.findByCustomerCode(customerCode, paging);
    }

    public Page<Order> getOrderByCreatedDateBetween(String createdDateFrom, String createdDateTo, Pageable paging) {
        return orderRepository.findByCreatedAtBetween(createdDateFrom, createdDateTo, paging);
    }

    public Page<Order> getOrderByStatus(String status, Pageable paging) {
        return orderRepository.findByStatus(status, paging);
    }

    public Page<Order> findOrderByCustomerName(String name, Pageable paging) {
        return orderRepository.findByCustomerNameContainingIgnoreCase(name, paging);
    }

    public Page<Order> findOrderByCustomerPhone(String phone, Pageable paging) {
        return orderRepository.findByCustomerPhoneContaining(phone, paging);
    }

    public Page<Order> findOrderByCustomerNameAndStatus(String name, String status, Pageable paging) {
        return orderRepository.findByCustomerNameContainingIgnoreCaseAndStatus(name, status, paging);
    }

    public Page<Order> findOrderByCustomerPhoneAndStatus(String phone, String status, Pageable paging) {
        return orderRepository.findByCustomerPhoneContainingAndStatus(phone, status, paging);
    }

    public Page<Order> getAllOrders(Pageable paging) {
        return orderRepository.findAll(paging);
    }

    public Page<Order> getAllOrdersCondition(String generalSearch, String status, Pageable paging) {
        if (generalSearch == "") {
            return this.getOrderByStatus(status, paging);
        } else {
            try {
                Long.parseLong(generalSearch);
                if (status == "") {
                    return this.findOrderByCustomerPhone(generalSearch, paging);
                } else {
                    return this.findOrderByCustomerPhoneAndStatus(generalSearch, status, paging);
                }
            } catch (NumberFormatException nfe) {
                if (status == "") {
                    return this.findOrderByCustomerName(generalSearch, paging);
                } else {
                    return this.findOrderByCustomerNameAndStatus(generalSearch, status, paging);
                }
            }
            
        }
    }

    public Order updateOrder(String id, Order newOrderModelData) {
        Order orderData = orderRepository.findById(id).orElse(null);
        if (orderData == null) {
            return null;
        }

        if (!orderData.getCustomerPhone().equals(newOrderModelData.getCustomerPhone())) {
            Customer newCustomerData = customerService.getCustomerByPhone(newOrderModelData.getCustomerPhone());
            if (newCustomerData == null) {
                newCustomerData = new Customer(newOrderModelData.getCustomerName(), newOrderModelData.getCustomerPhone());
                orderData.setCustomerCode(customerService.createCustomer(newCustomerData).getCustomerCode());
            } else {
                orderData.setCustomerCode(newCustomerData.getCustomerCode());
            }
            orderData.setCustomerName(newOrderModelData.getCustomerName());
            orderData.setCustomerPhone(newOrderModelData.getCustomerPhone());
        }

        if (!orderData.getDeliveryTo().equals(newOrderModelData.getDeliveryTo())) {
            Customer customerData = customerService.getCustomerByCode(orderData.getCustomerCode());
            customerService.addCustomerAddress(customerData.getId(), newOrderModelData.getDeliveryTo());
            orderData.setDeliveryTo(newOrderModelData.getDeliveryTo());
        }

        if (!orderData.getProductName().equals(newOrderModelData.getProductName())) {
            Product newProductData = productService.getProductByName(newOrderModelData.getProductName());
            if (newProductData == null) {
                newProductData = new Product();
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
        orderData.setNote(newOrderModelData.getNote());

        return orderRepository.save(orderData);
    }

    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }

    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }

}
