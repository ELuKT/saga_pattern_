package order.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.bean.CreateOrderResult;
import order.bean.OrderRequest;
import order.bean.SimpleException;
import order.bean.message.CancelOrderMessage;
import order.bean.message.StartSagaMessage;
import order.bean.message.UpdateOrderMessage;
import order.config.RabbitmqResource;
import order.dao.OrderMapper;
import order.dao.OrderRecordMapper;
import order.entity.Order;
import order.entity.OrderRecord;
import order.mware.IRabbitmqMware;
import order.service.IOrderService;
import order.status.OrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class OrderService implements IOrderService {

    private final OrderMapper orderMapper;
    private final OrderRecordMapper orderRecordMapper;
    private final IRabbitmqMware rabbitmqMware;

    @Override
    public CreateOrderResult createOrder(OrderRequest orderRequest) {
        OrderStatus status = OrderStatus.PENDING;

        Order order = Order.builder()
                .amount(orderRequest.getAmount())
                .productId(orderRequest.getProductId())
                .orderStatus(status)
                .userId(orderRequest.getUserId())
                .build();
        boolean isSuccess = orderMapper.insertOrder(order) > 0;

        if (isSuccess) {
            OrderRecord orderRecord = OrderRecord.builder()
                    .orderId(order.getOrderId())
                    .orderStatus(status)
                    .build();
            isSuccess = orderRecordMapper.insertRecordOrder(orderRecord) > 0;
        }

        if (!isSuccess) {
            throw new SimpleException("Fail to create order");
        }

        StartSagaMessage message = new StartSagaMessage(
                orderRequest.getProductId(),
                orderRequest.getAmount(),
                order.getOrderId()
        );
        rabbitmqMware.sendMessage(
                message,
                RabbitmqResource.EXCHANGE,
                RabbitmqResource.TO_ORCHESTRATOR_ROUTE_KEY
        );

        return new CreateOrderResult(order.getOrderId(), status);
    }

    @Override
    public void updateOrder(UpdateOrderMessage updateOrderMessage) {
        OrderStatus status = OrderStatus.COMPLETE;
        boolean isSuccess = orderMapper.updateOrder(
                updateOrderMessage.getOrderId(),
                OrderStatus.PENDING,
                status
        ) > 0;

        if (isSuccess) {
            OrderRecord orderRecord = OrderRecord.builder()
                    .orderId(updateOrderMessage.getOrderId())
                    .orderStatus(status)
                    .build();
            isSuccess = orderRecordMapper.insertRecordOrder(orderRecord) > 0;
        }

        if (!isSuccess) {
            throw new SimpleException("Fail to update order");
        }
    }

    @Override
    public void cancelOrder(CancelOrderMessage updateOrderMessage) {
        OrderStatus status = OrderStatus.CANCEL;
        boolean isSuccess = orderMapper.updateOrder(
                updateOrderMessage.getOrderId(),
                OrderStatus.PENDING,
                status
        ) > 0;

        if (isSuccess) {
            OrderRecord orderRecord = OrderRecord.builder()
                    .orderId(updateOrderMessage.getOrderId())
                    .orderStatus(status)
                    .build();
            isSuccess = orderRecordMapper.insertRecordOrder(orderRecord) > 0;
        }

        if (!isSuccess) {
            throw new SimpleException("Fail to cancel order");
        }
    }
}
