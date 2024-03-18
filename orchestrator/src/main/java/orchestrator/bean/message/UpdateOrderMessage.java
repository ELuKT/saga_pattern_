package orchestrator.bean.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateOrderMessage implements Serializable {
    private Long orderId;
}
