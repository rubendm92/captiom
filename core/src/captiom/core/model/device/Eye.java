package captiom.core.model.device;

public enum Eye {
	LEFT, RIGHT;

	public static Eye fromString(String eye) {
		return Eye.valueOf(eye.toUpperCase());
	}
}
