package order.bean.message;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Getter
public class UpdateOrderMessage implements Serializable {
    private Long orderId;
}
