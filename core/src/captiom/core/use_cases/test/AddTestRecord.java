package captiom.core.use_cases.test;

import captiom.core.model.test.Record;
import captiom.core.model.test.TestService;

import static java.util.Arrays.asList;

public class AddTestRecord {

	private final TestService testService;

	public AddTestRecord(TestService testService) {
		this.testService = testService;
	}

	public void add(Record... testResults) {
		testService.register(asList(testResults));
	}
}
