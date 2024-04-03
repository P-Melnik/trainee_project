package trainee.GymApp.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import trainee.GymApp.dto.RequestWorkloadDTO;

@FeignClient(value = "summary-microservice", url = "localhost:8082/")
@CircuitBreaker(name = "summaryMicroservice")
public interface WorkloadSummaryClient {

    @PostMapping("/workload/{username}")
    void manageWorkload(@PathVariable(value = "username") String username,
                                              @RequestBody RequestWorkloadDTO workloadDTO);

}
