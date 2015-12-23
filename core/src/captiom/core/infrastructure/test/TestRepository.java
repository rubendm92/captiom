package captiom.core.infrastructure.test;

import captiom.core.model.test.Record;
import captiom.core.model.test.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TestRepository {

	List<Test> availableTests();
	Map<LocalDate, List<Record>> testResultsByDate(String patientId);
	void save(String patientId, List<Record> testResults);
}
