package trainee.GymApp.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Sender {

    private final JmsTemplate jmsTemplate;

    public void sendTrainerWorkloadMessage(String queueName, Object payload,
                                           MessagePostProcessor messagePostProcessor) {
        jmsTemplate.convertAndSend(queueName, payload, messagePostProcessor);
        log.info("Send message to queue {}", queueName);
    }
}
