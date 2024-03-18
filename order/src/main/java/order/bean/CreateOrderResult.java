package order.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import order.status.OrderStatus;


@AllArgsConstructor
@Getter
public class CreateOrderResult {
    private Long orderId;
    private OrderStatus orderStatus;
}
