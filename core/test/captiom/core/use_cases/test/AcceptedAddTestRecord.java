package captiom.core.use_cases.test;

import captiom.core.infrastructure.test.TestRepository;
import captiom.core.model.test.Record;
import captiom.core.model.test.TestService;
import org.junit.Test;

import static java.util.Collections.singletonList;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class AcceptedAddTestRecord {
	
	@Test
	public void should_add_new_record_for_patient_test() {
		TestRepository testRepository = mock(TestRepository.class);
		AddTestRecord addTestRecord = new AddTestRecord(new TestService(testRepository));

		addTestRecord.add(any(Record.class));

		verify(testRepository, times(1)).save(singletonList(any(Record.class)));
	}
}
