package captiom.core.use_cases;

import captiom.core.model.Gender;
import captiom.core.model.Patient;

import java.time.LocalDate;

public class SearchPatientCommand {

	public Patient searchPatient(String id) {
		return new Patient(id, LocalDate.of(1992, 6, 22), Gender.MALE);
	}
}
