package captiom.core.use_cases.test;

import captiom.core.infrastructure.test.TestRepository;
import captiom.core.model.test.TestService;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class AcceptedGetTestRecordsForPatient {

	@Test
	public void should_retrieve_all_records_for_given_patient() {
		TestRepository testRepository = mock(TestRepository.class);
		GetTestRecords getTestRecords = new GetTestRecords(new TestService(testRepository));

		String patientId = "1";
		getTestRecords.forPatient(patientId);

		verify(testRepository, times(1)).testResultsByDate(patientId);
	}
}
