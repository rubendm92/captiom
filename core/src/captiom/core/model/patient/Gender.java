package captiom.core.model.patient;

public enum Gender {

	MALE, FEMALE;


	@Override
	public String toString() {
		return toTitleCase(super.toString());
	}

	private String toTitleCase(String string) {
		return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
	}
}
