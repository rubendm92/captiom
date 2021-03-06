package captiom.core.model.patient;

import java.time.LocalDate;

public class Patient {

	public final String id;
	public final String name;
	public final LocalDate birthDate;
	public final Gender gender;

	public Patient(String id, String name, LocalDate birthDate, Gender gender) {
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.gender = gender;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		return id == null ? ((Patient) o).id == null : id.equals(((Patient) o).id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
