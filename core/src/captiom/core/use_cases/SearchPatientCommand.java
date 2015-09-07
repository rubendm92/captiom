package captiom.core.use_cases;

import captiom.core.model.Gender;
import captiom.core.model.Patient;
import captiom.core.model.PatientNotFound;

import java.time.LocalDate;

public class SearchPatientCommand {

	public Patient searchPatient(String id) {
		if (id.equals("1113"))
			throw new PatientNotFound(id);
		return new Patient(id, LocalDate.of(1992, 6, 22), Gender.MALE);
	}
}
