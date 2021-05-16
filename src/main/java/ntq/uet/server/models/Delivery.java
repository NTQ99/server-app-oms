package ntq.uet.server.models;

import java.util.ArrayList;
import java.util.List;

import ntq.uet.server.exceptions.GlobalException;

public class Delivery {
    public static class Item {
        public Item(String name, String code, int quantity, int price) {
            this.name = name;
            this.code = code;
            this.quantity = quantity;
            this.price = price;
        }
        private String name; // Tên của sản phẩm.
        private String code; // Mã của sản phẩm.
        private int quantity; // Số lượng của sản phẩm.
        private int price; // Giá của sản phẩm.
        public String getName() {
            return name;
        }
        public int getPrice() {
            return price;
        }
        public void setPrice(int price) {
            this.price = price;
        }
        public int getQuantity() {
            return quantity;
        }
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
        public String getCode() {
            return code;
        }
        public void setCode(String code) {
            this.code = code;
        }
        public void setName(String name) {
            this.name = name;
        }
    }

    private String deliveryUnitName;//  #
    private String to_name; // Tên người nhận hàng. #
    private String to_phone; // Số điện thoại người nhận hàng.  #
    private String to_address; // Địa chỉ Shiper tới giao hàng.
    private String to_ward_code; // Phường của người nhận hàng.
    private int to_district_id; // Quận của người nhận hàng.
    private String client_order_code; // Mã đơn hàng riêng của khách hàng. Giá trị mặc định: null   #
    private int cod_amount; // Tiền thu hộ cho người gửi. Maximum :50.000.000. Giá trị mặc định: 0  #
    private String content; // Nội dung của đơn hàng.   #
    private int weight; // Khối lượng của đơn hàng (gram). Tối đa : 1600000 gram   #
    private int length; // Chiều dài của đơn hàng (cm). Tối đa : 200 cm    #
    private int width; // Chiều rộng của đơn hàng (cm). Tối đa : 200 cm    #
    private int height; // Chiều cao của đơn hàng (cm). Tối đa : 200 cm    #
    private int pick_station_id; // Mã bưu cục để gửi hàng tại điểm. Giá trị mặc định : null
    private int insurance_value; // Giá trị của đơn hàng ( Trường hợp mất hàng , bể hàng sẽ đền theo giá trị của    #
    private String coupon; // Mã giảm giá.
    private int service_type_id; // Mã loại dịch vụ cố định 1:Bay , 2:Đi Bộ , 3:Cồng kềnh. Nếu đã truyền
    private int service_id; // Mã loại dịch vụ.Để lấy thông tin chính xác từng tuyến và thời gian dự kiến
    private int payment_type_id; // Mã người thanh toán phí dịch vụ. 1: Người bán/Người gửi. 2: Người mua/Người
    private String note; // Người gửi ghi chú cho tài xế.   #
    private String required_note; // Ghi chú bắt buộc, Bao gồm: CHOTHUHANG, CHOXEMHANGKHONGTHU, KHONGCHOXEMHANG
    private List<Integer> pick_shift; // Dùng để truyền ca lấy hàng , Sử dụng API Lấy danh sách ca lấy
    private List<Item> items; // Thông tin sản phẩm.    #
    
    public String getDeliveryUnitName() {
        return deliveryUnitName;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Integer> getPick_shift() {
        return pick_shift;
    }

    public void setPick_shift(List<Integer> pick_shift) {
        this.pick_shift = pick_shift;
    }

    public String getRequired_note() {
        return required_note;
    }

    public void setRequired_note(String required_note) {
        this.required_note = required_note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPayment_type_id() {
        return payment_type_id;
    }

    public void setPayment_type_id(int payment_type_id) {
        this.payment_type_id = payment_type_id;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public int getService_type_id() {
        return service_type_id;
    }

    public void setService_type_id(int service_type_id) {
        this.service_type_id = service_type_id;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public int getInsurance_value() {
        return insurance_value;
    }

    public void setInsurance_value(int insurance_value) {
        this.insurance_value = insurance_value;
    }

    public int getPick_station_id() {
        return pick_station_id;
    }

    public void setPick_station_id(int pick_station_id) {
        this.pick_station_id = pick_station_id;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCod_amount() {
        return cod_amount;
    }

    public void setCod_amount(int cod_amount) {
        this.cod_amount = cod_amount;
    }

    public String getClient_order_code() {
        return client_order_code;
    }

    public void setClient_order_code(String client_order_code) {
        this.client_order_code = client_order_code;
    }

    public int getTo_district_id() {
        return to_district_id;
    }

    public void setTo_district_id(int to_district_id) {
        this.to_district_id = to_district_id;
    }

    public String getTo_ward_code() {
        return to_ward_code;
    }

    public void setTo_ward_code(String to_ward_code) {
        this.to_ward_code = to_ward_code;
    }

    public String getTo_address() {
        return to_address;
    }

    public void setTo_address(String to_address) {
        this.to_address = to_address;
    }

    public String getTo_phone() {
        return to_phone;
    }

    public void setTo_phone(String to_phone) {
        this.to_phone = to_phone;
    }

    public String getTo_name() {
        return to_name;
    }

    public void setTo_name(String to_name) {
        this.to_name = to_name;
    }

    public void setDeliveryUnitName(String deliveryUnitName) {
        this.deliveryUnitName = deliveryUnitName;
    }

    public void setRequest(List<Product> products, Order order) {
        List<Item> tmpItems = new ArrayList<Item>();
        int totalWeight = 0;
        for (int i = 0; i < products.size(); i ++) {
            Product product = products.get(i);
            Order.Item productOrder = order.getProducts().get(i);
            Item item = new Item(product.getProductName(), product.getProductCode(), productOrder.getQuantity(), product.getPrice()[order.getPriceType()]);
            tmpItems.add(item);
            totalWeight += productOrder.getQuantity() * product.getWeight();
        }
        this.setItems(tmpItems);
        this.setWeight(totalWeight);

        this.setTo_name(order.getCustomerName());
        this.setTo_phone(order.getCustomerPhone());
        this.setTo_address(order.getDeliveryTo().getDetail());
        this.setClient_order_code(order.getOrderCode());
        this.setCod_amount(order.getCodAmount());
        this.setInsurance_value(order.getTotalPrice());
        this.setNote(order.getNote());
    }

    public void validateRequest() {
        if (this.getDeliveryUnitName() == null) throw new GlobalException("delivery unit name not null");
        if (this.getRequired_note() == null) throw new GlobalException("required note not null");
    }
}
