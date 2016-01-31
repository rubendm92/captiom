package captiom.core.model.device;

public class Device {

	private final String id;
	private Height height;
	private String modelName;

	public Device(String id) {
		this.id = id;
	}

	public Device height(Height height) {
		this.height = height;
		return this;
	}

	public Device modelName(String modelName) {
		this.modelName = modelName;
		return this;
	}

	public String id() {
		return id;
	}

	public String modelName() {
		return modelName;
	}

	public double onePixelHeight() {
		return height.onePixel();
	}

	public double heightInMeters() {
		return height.inMeters();
	}

	public double pixelsPerMeter() {
		return height.pixelsPerMeter();
	}
}
