package orchestrator.bean.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class CreateSaleRecordMessage implements Serializable {

    private String productId;
    private Long amount;
    private Long orderId;
}
