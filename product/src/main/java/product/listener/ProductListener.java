package product.listener;

import lombok.AllArgsConstructor;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import product.bean.message.CreateSaleRecordMessage;
import product.config.RabbitmqResource;
import product.service.IProductService;


@Component
@AllArgsConstructor
public class ProductListener {

    private final IProductService productService;

    @RabbitListener(queues = RabbitmqResource.PRODUCT_QUEUE)
    public void productListener(Message<CreateSaleRecordMessage> message) throws AmqpRejectAndDontRequeueException {
        try {
            CreateSaleRecordMessage messagePayload = message.getPayload();
            productService.createSaleRecord(messagePayload);
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
