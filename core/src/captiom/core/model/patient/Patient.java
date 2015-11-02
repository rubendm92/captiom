package captiom.core.model.patient;

public class Patient {

	public final String id;
	public final int age;
	public final Gender gender;

	public Patient(String id, int age, Gender gender) {
		this.id = id;
		this.age = age;
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
