package captiom.core.model.device.dimension;

public class Width {

	private final double pixels;
	private final double widthInMeters;

	public Width(double pixels, double widthInMeters) {
		this.pixels = pixels;
		this.widthInMeters = widthInMeters;
	}

	public double pixels() {
		return pixels;
	}

	public double inMeters() {
		return widthInMeters;
	}
}
