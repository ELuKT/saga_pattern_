package order.dao;

import order.entity.OrderRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface OrderRecordMapper {

    @Insert("INSERT INTO SAGA_ORDER_RECORD(order_id, status) VALUES(#{orderId}, #{orderStatus})")
    @Options(useGeneratedKeys = true, keyProperty = "orderRecordId,createTime", keyColumn = "id,create_time")
    int insertRecordOrder(OrderRecord orderRecord);
}
