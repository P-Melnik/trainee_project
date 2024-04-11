package trainee.service.summary.exceptions;

public class WorkloadCalculationException extends RuntimeException {

    public WorkloadCalculationException(String username) {
        super("Failed to manage workload for: " + username);
    }
}
