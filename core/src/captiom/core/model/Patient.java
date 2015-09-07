package captiom.core.model;

import java.time.LocalDate;

public class Patient {

	public final String id;
	public final LocalDate dateOfBirth;
	public final Gender gender;

	public Patient(String id, LocalDate dateOfBirth, Gender gender) {
		this.id = id;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
	}
}
