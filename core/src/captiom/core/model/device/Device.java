package captiom.core.model.device;

public class Device {

	private String id;
	private Height height;
	private String notificationId;

	public Device() {
	}

	public Device(String id) {
		this.id = id;
	}

	public Device withHeight(Height height) {
		this.height = height;
		return this;
	}

	public Device withNotificationId(String notificationId) {
		this.notificationId = notificationId;
		return this;
	}

	public String id() {
		return id;
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
