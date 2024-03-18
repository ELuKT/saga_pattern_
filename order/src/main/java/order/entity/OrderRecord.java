package order.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import order.status.OrderStatus;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
public class OrderRecord {
    private Long orderRecordId;
    private Timestamp createTime;
    private OrderStatus orderStatus;
    private Long orderId;
}
