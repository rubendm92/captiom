package captiom.core.use_cases.test;

import captiom.core.model.test.Record;
import captiom.core.model.test.TestService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class GetTestRecords {

	private final TestService testService;

	public GetTestRecords(TestService testService) {
		this.testService = testService;
	}

	public Map<LocalDateTime, List<Record>> forPatient(String patientId) {
		return testService.resultsForPatient(patientId);
	}
}
