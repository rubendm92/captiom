package captiom.core.model.test;

import java.util.List;
import java.util.Optional;

public class TestService {

	private final TestRepository testRepository;

	public TestService(TestRepository testRepository) {
		this.testRepository = testRepository;
	}

	public List<Test> availableTests() {
		return testRepository.availableTests();
	}

	public void register(String patientId, List<Record> testResult) {
		testRepository.save(patientId, testResult);
	}

	public History historyForPatient(String patientId) {
		return testRepository.history(patientId);
	}

	public Optional<Test> testFor(String testName) {
		return availableTests().stream().filter(t -> t.name().equals(testName)).findFirst();
	}
}
