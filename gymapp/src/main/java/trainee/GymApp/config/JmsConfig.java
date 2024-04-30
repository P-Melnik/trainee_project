package trainee.GymApp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import trainee.GymApp.dto.WorkloadDTO;

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
}
