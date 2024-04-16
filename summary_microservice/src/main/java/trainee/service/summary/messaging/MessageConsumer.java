package trainee.service.summary.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import trainee.service.summary.models.WorkloadDTO;
import trainee.service.summary.service.SummaryService;

@Slf4j
@Component
public class MessageConsumer {

    @Autowired
    private SummaryService summaryService;

    @JmsListener(destination = "trainer-workload-queue")
    public void manageMessage(@Payload WorkloadDTO workloadDTO) {
        summaryService.manage(workloadDTO);
        log.info("Received a message from main service: trainer:  " + workloadDTO.getUserName());
    }

}
