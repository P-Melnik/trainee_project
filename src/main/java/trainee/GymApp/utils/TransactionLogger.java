package trainee.GymApp.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.UUID;

@Slf4j
public class TransactionLogger {

    public static void logTransactionStart() {
        String transactionId = UUID.randomUUID().toString();
        MDC.put("transactionId", transactionId);
        log.info("Transaction started with ID: {}" + transactionId);
    }

    public static void logTransactionEnd() {
        log.info("Transaction ended with ID: {}" + MDC.get("transactionId"));
        MDC.clear();
    }

}
