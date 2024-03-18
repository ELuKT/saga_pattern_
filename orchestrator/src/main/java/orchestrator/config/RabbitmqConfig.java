package orchestrator.config;

import com.rabbitmq.client.ConnectionFactory;
import orchestrator.bean.message.ProductServiceResultMessage;
import orchestrator.bean.message.StartSagaMessage;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitmqConfig {

    @Value("${spring.mq.url}")
    private String URI;

    @Bean
    public CachingConnectionFactory ccf() throws Exception {
        ConnectionFactory cf = new ConnectionFactory();
        cf.setUri(URI);
        cf.setVirtualHost("/");
        CachingConnectionFactory ccf = new CachingConnectionFactory(cf);
        ccf.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        return ccf;
    }

    @Bean
    public RabbitTemplate template(CachingConnectionFactory ccf){
        RabbitTemplate template = new RabbitTemplate(ccf);
        template.setMandatory(true);
//        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultClassMapper dcm = new DefaultClassMapper();
        Map<String, Class<?>> mapping = new HashMap<>();
        mapping.put("order.bean.message.StartSagaMessage", StartSagaMessage.class);
        mapping.put("product.bean.message.ProductServiceResultMessage", ProductServiceResultMessage.class);
        dcm.setTrustedPackages("order.bean.message", "product.bean.message");
        dcm.setIdClassMapping(mapping);
        converter.setClassMapper(dcm);
        return converter;
    }

    @Bean
    public TopicExchange exchange() {
        return ExchangeBuilder.topicExchange(RabbitmqResource.EXCHANGE)
                .build();
    }

    @Bean
    public Queue productQueue() {
        return QueueBuilder
                .durable(RabbitmqResource.PRODUCT_QUEUE)
                .deadLetterExchange(RabbitmqResource.EXCHANGE)
                .deadLetterRoutingKey(RabbitmqResource.DEAD_LETTER_ROUTE_KEY)
                .overflow(QueueBuilder.Overflow.rejectPublish)
                .maxLength(32)
                .ttl(1000)
                .quorum()
                .build();
    }

    @Bean
    public Binding productBinding(TopicExchange exchange, Queue productQueue) {
        return BindingBuilder
                .bind(productQueue)
                .to(exchange)
                .with(RabbitmqResource.PRODUCT_ROUTE_KEY);
    }

    @Bean
    public Queue orchQueue() {
        return QueueBuilder
                .durable(RabbitmqResource.ORCHESTRATOR_QUEUE)
                .deadLetterExchange(RabbitmqResource.EXCHANGE)
                .deadLetterRoutingKey(RabbitmqResource.DEAD_LETTER_ROUTE_KEY)
                .overflow(QueueBuilder.Overflow.rejectPublish)
                .ttl(1000) // repeating not in the trusted packages Failed to convert message go to dlq
                .maxLength(32)
                .quorum()
                .build();
    }

    @Bean
    public Binding orchBinding(TopicExchange exchange, Queue orchQueue) {
        return BindingBuilder
                .bind(orchQueue)
                .to(exchange)
                .with(RabbitmqResource.ORCHESTRATOR_ROUTE_KEY);
    }

    @Bean
    public Queue orderQueue() {
        return QueueBuilder
                .durable(RabbitmqResource.ORDER_QUEUE)
                .deadLetterExchange(RabbitmqResource.EXCHANGE)
                .deadLetterRoutingKey(RabbitmqResource.DEAD_LETTER_ROUTE_KEY)
                .overflow(QueueBuilder.Overflow.rejectPublish)
                .maxLength(32)
                .ttl(1000)
                .quorum()
                .build();
    }

    @Bean
    public Binding orderBinding(TopicExchange exchange, Queue orderQueue) {
        return BindingBuilder
                .bind(orderQueue)
                .to(exchange)
                .with(RabbitmqResource.ORDER_ROUTE_KEY);
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder
                .durable(RabbitmqResource.DEAD_LETTER_QUEUE)
                .quorum()
                .build();
    }

    @Bean
    public Binding dlBinding(TopicExchange exchange, Queue deadLetterQueue) {
        return BindingBuilder
                .bind(deadLetterQueue)
                .to(exchange)
                .with(RabbitmqResource.DEAD_LETTER_ROUTE_KEY);
    }

}
