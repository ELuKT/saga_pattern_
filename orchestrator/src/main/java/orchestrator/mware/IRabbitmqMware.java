package orchestrator.mware;

import orchestrator.bean.SimpleException;

public interface IRabbitmqMware {

    void sendMessage(Object message, String exchange, String routingKey) throws SimpleException;
}
