package order.bean;

import lombok.Getter;
import order.status.OrderStatus;

@Getter
public class OrderResponse extends BaseResult {
    private Long orderId;
    private OrderStatus orderStatus;

    public OrderResponse(Long orderId, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
    }

    public OrderResponse(String returnMessage) {
        super(returnMessage);
    }
}
