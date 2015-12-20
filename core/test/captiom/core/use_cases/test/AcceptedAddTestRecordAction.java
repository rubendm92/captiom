package captiom.core.use_cases.test;

import captiom.core.infrastructure.test.TestRepository;
import captiom.core.model.device.Eye;
import captiom.core.model.test.Record;
import captiom.core.model.test.TestService;
import org.junit.Test;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.*;

public class AcceptedAddTestRecordAction {
	
	@Test
	public void should_add_new_record_for_patient_test() {
		TestRepository testRepository = mock(TestRepository.class);
		AddTestRecordAction action = new AddTestRecordAction(new TestService(testRepository));
		String patientId = "1";
		Record record = new Record("C", 200, Eye.LEFT, true);

		action.add(patientId, record);

		verify(testRepository, times(1)).save(patientId, singletonList(record));
	}
}
