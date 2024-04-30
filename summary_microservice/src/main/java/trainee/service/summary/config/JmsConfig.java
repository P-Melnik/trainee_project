package trainee.service.summary.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.jms.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import trainee.service.summary.handler.JmsErrorHandler;
import trainee.service.summary.models.WorkloadDTO;

import java.util.HashMap;
import java.util.Map;

@EnableJms
@Configuration
public class JmsConfig {

    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        Map<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put("JMS_type", WorkloadDTO.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        converter.setTypeIdMappings(typeIdMappings);
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("JMS_type");
        converter.setObjectMapper(mapper);
        return converter;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(
            ConnectionFactory connectionFactory, JmsErrorHandler handler) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter());
        factory.setErrorHandler(handler);
        return factory;
    }
}
