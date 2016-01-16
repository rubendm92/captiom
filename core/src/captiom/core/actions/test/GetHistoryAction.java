package captiom.core.actions.test;

import captiom.core.model.test.History;
import captiom.core.model.test.TestService;

public class GetHistoryAction {

	private final TestService testService;

	public GetHistoryAction(TestService testService) {
		this.testService = testService;
	}

	public History forPatient(String patientId) {
		return testService.historyForPatient(patientId);
	}
}
