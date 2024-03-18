package orchestrator.service;

import org.springframework.messaging.Message;

public interface IOrchestrateService {
    void handle(Message<?> message);
}
