package captiom.core.actions.test;

import captiom.core.model.test.TestRepository;
import captiom.core.model.test.TestService;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class AcceptedGetTestRecordsAction {

	@Test
	public void should_retrieve_all_records_for_given_patient() {
		TestRepository testRepository = mock(TestRepository.class);
		GetTestRecordsAction action = new GetTestRecordsAction(new TestService(testRepository));

		String patientId = "1";
		action.forPatient(patientId);

		verify(testRepository, times(1)).testResultsByDate(patientId);
	}
}
