package orchestrator.listener;

import lombok.AllArgsConstructor;
import orchestrator.config.RabbitmqResource;
import orchestrator.service.IOrchestrateService;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SagaListener {

    private final IOrchestrateService orchestrateService;

    @RabbitListener(queues = RabbitmqResource.ORCHESTRATOR_QUEUE)
    public void sagaListener(Message<?> message) throws AmqpRejectAndDontRequeueException {
        orchestrateService.handle(message);
    }
}
