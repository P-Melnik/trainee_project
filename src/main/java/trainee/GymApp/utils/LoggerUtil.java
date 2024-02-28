package trainee.GymApp.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class LoggerUtil {

    private final MappedDiagnosticContextUtil MDC;

    public void setParams() {
        MDC.createId();
    }
}
