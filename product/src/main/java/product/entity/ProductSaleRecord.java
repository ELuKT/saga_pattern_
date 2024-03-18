package product.entity;

import lombok.Builder;
import lombok.Getter;
import product.status.SaleStatus;

import java.sql.Timestamp;

@Builder
@Getter
public class ProductSaleRecord {

    private Long productSaleRecordId;
    private String productId;
    private Long amount;
    private Timestamp createTime;
    private SaleStatus saleStatus;
    private Long orderId;
}
