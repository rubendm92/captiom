package captiom.core.model.test;

import captiom.core.infrastructure.test.TestRepository;

import java.util.List;

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
}
