package captiom.core.model.device;

import captiom.core.model.device.dimension.Dimension;

public class Device {

	private final String id;
	private Dimension dimension;

	public Device(String id) {
		this.id = id;
	}

	public Device withDimension(Dimension dimension) {
		this.dimension = dimension;
		return this;
	}

	public double onePixelHeight() {
		return dimension.height().onePixel();
	}

	public double heightInMeters() {
		return dimension.height().inMeters();
	}
}
