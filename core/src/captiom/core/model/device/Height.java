package captiom.core.model.device;

public class Height {

	private final double pixels;
	private final double heightInMeters;

	public Height(double pixels, double heightInMeters) {
		this.pixels = pixels;
		this.heightInMeters = heightInMeters;
	}

	public double inMeters() {
		return heightInMeters;
	}

	public double onePixel() {
		return heightInMeters / pixels;
	}

	public double pixelsPerMeter() {
		return pixels / heightInMeters;
	}
}
