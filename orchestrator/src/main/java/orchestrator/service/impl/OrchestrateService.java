package orchestrator.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import orchestrator.bean.message.*;
import orchestrator.config.RabbitmqResource;
import orchestrator.mware.IRabbitmqMware;
import orchestrator.service.IOrchestrateService;
import orchestrator.status.ProductServiceStatus;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class OrchestrateService implements IOrchestrateService {

    private final IRabbitmqMware rabbitmqMware;

    @Override
    public void handle(Message<?> message) {
        Object messagePayload = message.getPayload();
        if (messagePayload instanceof StartSagaMessage startSagaMessage) {
            this.createSale(startSagaMessage);
        } else if (messagePayload instanceof ProductServiceResultMessage productServiceResultMessage) {
            if (productServiceResultMessage.getProductServiceStatus() == ProductServiceStatus.SUCCESS) {
                this.updateOrder(productServiceResultMessage);
            } else {
                this.cancelOrder(productServiceResultMessage);
            }
        }
    }

    private void createSale(StartSagaMessage startSagaMessage) {
        CreateSaleRecordMessage createSaleRecordMessage =
                new CreateSaleRecordMessage(
                        startSagaMessage.getProductId(),
                        startSagaMessage.getAmount(),
                        startSagaMessage.getOrderId()
                );
        rabbitmqMware.sendMessage(createSaleRecordMessage,
                RabbitmqResource.EXCHANGE,
                RabbitmqResource.SEND_TO_PRODUCT_ROUTE_KEY
        );
    }

    private void updateOrder(ProductServiceResultMessage productServiceResultMessage) {
        UpdateOrderMessage updateOrderMessage = new UpdateOrderMessage(productServiceResultMessage.getOrderId());
        rabbitmqMware.sendMessage(updateOrderMessage,
                RabbitmqResource.EXCHANGE,
                RabbitmqResource.SEND_TO_ORDER_ROUTE_KEY
        );
    }

    private void cancelOrder(ProductServiceResultMessage productServiceResultMessage) {
        CancelOrderMessage cancelOrderMessage = new CancelOrderMessage(productServiceResultMessage.getOrderId());
        rabbitmqMware.sendMessage(cancelOrderMessage,
                RabbitmqResource.EXCHANGE,
                RabbitmqResource.SEND_TO_ORDER_ROUTE_KEY
        );
    }

}
