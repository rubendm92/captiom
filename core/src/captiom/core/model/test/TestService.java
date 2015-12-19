package captiom.core.model.test;

import captiom.core.infrastructure.test.TestRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class TestService {

	private final TestRepository testRepository;

	public TestService(TestRepository testRepository) {
		this.testRepository = testRepository;
	}

	public List<Test> availableTests() {
		return testRepository.availableTests();
	}

	public void register(List<Record> testResult) {
		testRepository.save(testResult);
	}

	public Map<LocalDateTime, List<Record>> resultsForPatient(String patientId) {
		return testRepository.testResultsByDate(patientId);
	}
}
