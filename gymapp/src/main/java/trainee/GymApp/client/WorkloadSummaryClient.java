package trainee.GymApp.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import trainee.GymApp.dto.WorkloadDTO;

@FeignClient(value = "summary-microservice", url = "localhost:8082/")
@CircuitBreaker(name = "summaryMicroservice", fallbackMethod = "manageWorkloadFallback")
public interface WorkloadSummaryClient {
    Logger log = LoggerFactory.getLogger(WorkloadSummaryClient.class);

    @PostMapping("/workload/{username}")
    void manageWorkload(@PathVariable(value = "username") String username,
                        @RequestBody WorkloadDTO workloadDTO);

    default void manageWorkloadFallback(String username, WorkloadDTO workloadDTO, Throwable throwable) {
        log.error("inside fallback method -> summary-service is down: error: " + throwable.getMessage()
                + " training date: " + workloadDTO.getTrainingDate() + " training duration: " + workloadDTO.getTrainingDuration());
    }
}
