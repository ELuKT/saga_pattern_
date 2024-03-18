package order.entity;

import lombok.Builder;
import lombok.Getter;
import order.status.OrderStatus;

@Builder
@Getter
public class Order {
    private Long orderId;
    private String productId;
    private Long amount;
    private String userId;
    private OrderStatus orderStatus;
}
