package orchestrator.bean.message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import orchestrator.status.ProductServiceStatus;

import java.io.Serializable;

@NoArgsConstructor
@Getter
public class ProductServiceResultMessage implements Serializable {
    private Long orderId;
    private Long ProductId;
    private ProductServiceStatus productServiceStatus;
}
