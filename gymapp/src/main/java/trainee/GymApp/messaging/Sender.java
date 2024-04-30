package trainee.GymApp.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Sender {

    private final JmsTemplate jmsTemplate;

    public void sendTrainerWorkloadMessage(String queueName, Object payload) {
        jmsTemplate.convertAndSend(queueName, payload);
        log.info("Sending message to queue {}", queueName);
    }
}
