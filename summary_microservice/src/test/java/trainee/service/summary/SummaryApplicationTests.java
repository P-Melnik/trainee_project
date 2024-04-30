package trainee.service.summary;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import trainee.service.summary.dao.CustomRepoImpl;
import trainee.service.summary.models.ActionType;
import trainee.service.summary.models.Workload;
import trainee.service.summary.models.WorkloadDTO;
import trainee.service.summary.service.SummaryServiceImpl;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
class SummaryApplicationTests {

	@Autowired
	private SummaryServiceImpl summaryService;

	@Autowired
	private CustomRepoImpl customRepo;

	@Test
	void saveTraining() {
		WorkloadDTO workloadDTO = new WorkloadDTO("A.A", "A", "A",
				true, LocalDate.of(2024, 4, 4), 60.0, ActionType.ADD);
		summaryService.manage(workloadDTO);
		Optional<Workload> foundWorkload = summaryService.findByUsername("A.A");
		Assertions.assertTrue(foundWorkload.isPresent());
	}

	@Test
	void deleteTraining_DeleteAction_ShouldDeleteTraining() {
		WorkloadDTO workloadDto = new WorkloadDTO("A.A", "A", "A",
				true, LocalDate.of(2024, 4, 4), 60.0, ActionType.DELETE);
		summaryService.manage(workloadDto);
		Optional<Workload> foundWorkload = summaryService.findByUsername("A.A");
		Assertions.assertTrue(foundWorkload.isEmpty());
	}

	@Test
	void notExistNameShouldReturnEmptyOptional() {
		var emptyWorkload = summaryService.findByUsername("not_exist_name");
		Assertions.assertTrue(emptyWorkload.isEmpty());
	}

}
