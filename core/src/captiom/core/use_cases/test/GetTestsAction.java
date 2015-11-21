package captiom.core.use_cases.test;

import captiom.core.model.test.Test;
import captiom.core.model.test.TestService;

import java.util.List;

public class GetTestsAction {

	private final TestService service;

	public GetTestsAction(TestService service) {
		this.service = service;
	}

	public List<Test> allTests() {
		return service.availableTests();
	}
}
