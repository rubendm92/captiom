package captiom.core.model.device;

public class Device {

	private final String id;
	private Height height;

	public Device(String id) {
		this.id = id;
	}

	public Device withHeight(Height height) {
		this.height = height;
		return this;
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
