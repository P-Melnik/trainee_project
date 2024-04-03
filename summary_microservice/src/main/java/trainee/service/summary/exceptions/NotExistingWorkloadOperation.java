package trainee.service.summary.exceptions;

public class NotExistingWorkloadOperation extends RuntimeException {

    public NotExistingWorkloadOperation(String username) {
        super("Failed to delete not existing trainer workload for trainer: " + username);
    }
}
