package captiom.core.model.device;

public enum Eye {
	LEFT, RIGHT, BOTH;

	public static Eye fromString(String eye) {
		return Eye.valueOf(eye.toUpperCase());
	}
}
