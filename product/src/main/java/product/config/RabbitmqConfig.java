package product.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
//        DefaultClassMapper dcm = new DefaultClassMapper();
//        Map<String, Class<?>> mapping = new HashMap<>();
//        mapping.put("orchestrator.bean.message.CreateSaleRecordMessage", CreateSaleMessageRecord.class);
//        dcm.setTrustedPackages("orchestrator.bean.message");
//        dcm.setIdClassMapping(mapping);
//        converter.setClassMapper(dcm);
        return converter;
    }
}
