package order.mware;

import order.bean.SimpleException;

public interface IRabbitmqMware {

    void sendMessage(Object message, String exchange, String routingKey) throws SimpleException;
}
