package captiom.core.model.test;

import java.util.List;

public interface TestRepository {

	List<Test> availableTests();

	History history(String patientId);

	void save(String patientId, List<Record> testResults);
}
