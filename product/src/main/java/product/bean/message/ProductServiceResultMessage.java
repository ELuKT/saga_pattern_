package product.bean.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import product.status.ProductServiceStatus;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductServiceResultMessage implements Serializable {
    private Long saleRecordId;
    private Long orderId;
    private ProductServiceStatus productServiceStatus;
}
