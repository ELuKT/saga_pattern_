package product.mware;

import product.bean.SimpleException;

public interface IRabbitmqMware {

    void sendMessage(Object message, String exchange, String routingKey) throws SimpleException;
}
