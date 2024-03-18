package orchestrator.bean.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StartSagaMessage implements Serializable {

    private String productId;
    private Long amount;
    private Long orderId;
}
