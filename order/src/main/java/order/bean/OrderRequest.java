package order.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderRequest {

    private String productId;
    private Long amount;
    private String userId;
}
