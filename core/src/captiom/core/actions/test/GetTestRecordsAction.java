package captiom.core.actions.test;

import captiom.core.model.test.Record;
import captiom.core.model.test.TestService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class GetTestRecordsAction {

	private final TestService testService;

	public GetTestRecordsAction(TestService testService) {
		this.testService = testService;
	}

	public Map<LocalDate, List<Record>> forPatient(String patientId) {
		return testService.resultsForPatient(patientId);
	}
}
