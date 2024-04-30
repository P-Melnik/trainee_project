package trainee.service.summary.handler;

import jakarta.jms.Destination;
import jakarta.jms.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

@Slf4j
@Component
public class JmsErrorHandler implements ErrorHandler {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void handleError(Throwable t) {
        log.error(t.getCause().getMessage(), t.getLocalizedMessage());
        sendToDLQ(t);
    }

    private void sendToDLQ(Throwable t) {
        try {
            Destination dlq = new ActiveMQQueue("DLQ");
            jmsTemplate.send(dlq, session -> {
                TextMessage message = session.createTextMessage();
                message.setText(t.getMessage() + "\n" + t.getCause().getMessage());
                return message;
            });
            log.info("Sent message to DLQ");
        } catch (Exception ex) {
            log.error("Failed to send message to DLQ");
        }
    }
}
