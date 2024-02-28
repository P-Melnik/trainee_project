package trainee.GymApp.utils;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class MappedDiagnosticContextUtil {

    public void createId() {
        String transactionId = TransactionsUUIDGenerator.generate();
        MDC.put("transactionID", transactionId);
    }

}
