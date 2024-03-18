package product.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import product.entity.ProductSaleRecord;

public interface ProductSaleRecordMapper {

    @Insert(
            """
            INSERT INTO SAGA_PRODUCT_SALE_RECORD(
                product_id, amount, status, order_id
            )
            VALUES(
                #{productId}, #{amount}, #{saleStatus}, #{orderId}
            )""")
    @Options(useGeneratedKeys = true, keyProperty = "productSaleRecordId")
    int insertProductSaleRecord(ProductSaleRecord productSaleRecord);
}
