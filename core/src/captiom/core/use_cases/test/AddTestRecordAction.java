package captiom.core.use_cases.test;

import captiom.core.model.test.Record;
import captiom.core.model.test.TestService;

import static java.util.Arrays.asList;

public class AddTestRecordAction {

	private final TestService testService;

	public AddTestRecordAction(TestService testService) {
		this.testService = testService;
	}

	public void add(String patientId, Record... testResults) {
		testService.register(patientId, asList(testResults));
	}
}
