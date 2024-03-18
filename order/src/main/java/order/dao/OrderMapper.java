package order.dao;

import order.entity.Order;
import order.status.OrderStatus;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface OrderMapper {

    @Insert("INSERT INTO SAGA_ORDER(product_id, amount, user_id, status) VALUES(#{productId}, #{amount}, #{userId}, #{orderStatus})")
    @Options(useGeneratedKeys = true, keyProperty = "orderId")
    int insertOrder(Order order);

    @Update("UPDATE SAGA_ORDER SET status=#{toStatus} WHERE id=#{orderId} AND status=#{fromStatus}")
    int updateOrder(@Param("orderId") Long orderId, @Param("fromStatus") OrderStatus fromStatus, @Param("toStatus") OrderStatus toStatus);
}
