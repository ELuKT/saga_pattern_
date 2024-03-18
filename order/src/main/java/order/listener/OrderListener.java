package order.listener;

import lombok.AllArgsConstructor;
import order.bean.SimpleException;
import order.bean.message.CancelOrderMessage;
import order.bean.message.UpdateOrderMessage;
import order.config.RabbitmqResource;
import order.service.IOrderService;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class OrderListener {

    private final IOrderService orderService;

    @RabbitListener(queues = RabbitmqResource.ORDER_QUEUE)
    public void updateOrderListener(Message<?> message) throws AmqpRejectAndDontRequeueException {
        try {
            Object messagePayload = message.getPayload();
            if (messagePayload instanceof UpdateOrderMessage updateOrderMessage) {
                orderService.updateOrder(updateOrderMessage);
            } else if (messagePayload instanceof CancelOrderMessage cancelOrderMessage) {
                orderService.cancelOrder(cancelOrderMessage);
            }
        } catch (SimpleException e) {
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
