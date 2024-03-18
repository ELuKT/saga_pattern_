package product.bean.message;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class CreateSaleRecordMessage implements Serializable {

    private String productId;
    private Long amount;
    private Long orderId;
}
