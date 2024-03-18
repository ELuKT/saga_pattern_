package order.mware.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order.bean.SimpleException;
import order.mware.IRabbitmqMware;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Slf4j
@Component
public class RabbitmqMware implements IRabbitmqMware {

    private final RabbitTemplate template;

    @Override
    public void sendMessage(Object message, String exchange, String routingKey) throws SimpleException {
        Jackson2JsonMessageConverter convert = new Jackson2JsonMessageConverter();

        MessageProperties properties = MessagePropertiesBuilder
                .newInstance()
                .setHeader(DefaultClassMapper.DEFAULT_CLASSID_FIELD_NAME, message.getClass().getName())
                .build();
        this.sendMessage(convert.toMessage(message, properties), exchange, routingKey);

    }

    private void sendMessage(Message message, String exchange, String routingKey) throws SimpleException {

        CorrelationData cd = new CorrelationData();
        template.send(exchange, routingKey, message, cd);
        CorrelationData.Confirm confirm;
        try {
            confirm = cd.getFuture().get();
        } catch (Exception e) {
            throw new SimpleException("Get publisher ack exception", e);
        }
        if (confirm.isAck()) {
            log.info("successfully send message to broker exchange: {}, routingKey: {}", exchange, routingKey);
        } else {
            throw new SimpleException("Fail to send message");
        }
    }
}
