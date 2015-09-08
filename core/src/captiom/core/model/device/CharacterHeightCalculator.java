package captiom.core.model.device;

public class CharacterHeightCalculator {

	private Device device;
	private double distance;
	private double focalLength;
	private double charPosition = -1;

	public DioptersReader usingDevice(Device device) {
		this.device = device;
		return this::storeDiopters;
	}

	public Range range() {
		if (isNotSetup()) throw new CalculatorNotConfiguredException();
		return new Range(min(), max());
	}

	public double imageHeightForMinutes(int minutes) {
		if (isNotSetup()) throw new CalculatorNotConfiguredException();
		return calculateHeightInMeters(minutes) * device.pixelsPerMeter();
	}

	private boolean isNotSetup() {
		return charPosition == -1;
	}

	private DistanceReader storeDiopters(double diopters) {
		this.focalLength = 1 / diopters;
		return this::storeDistance;
	}

	private CharacterHeightCalculator storeDistance(double distance) {
		this.distance = distance;
		calculateCharPosition();
		return this;
	}

	private void calculateCharPosition() {
		this.charPosition = (float) Math.abs(-distance * focalLength / (focalLength - distance));
	}

	private double min() {
		return 60 * Math.toDegrees(Math.atan(minImageSize(device.onePixelHeight()) / charPosition));
	}

	private double minImageSize(double pixelSize) {
		return pixelSize * (charPosition / distance);
	}

	private double max() {
		return 60 * Math.toDegrees(Math.atan(maxImageSize(device.heightInMeters()) / charPosition));
	}

	private double maxImageSize(double screenHeight) {
		return (screenHeight / 7) * (charPosition / distance);
	}

	private double calculateHeightInMeters(final double minutes) {
		return calculateCharDetail(minutes) * (distance / charPosition) * 7;
	}

	private double calculateCharDetail(double minutes) {
		return (Math.tan(Math.toRadians(minutes / 60)) * charPosition);
	}

	public static class Range {
		public final double min;
		public final double max;

		public Range(double min, double max) {
			this.min = min;
			this.max = max;
		}
	}

	@FunctionalInterface
	public interface DioptersReader {
		DistanceReader withDiopters(double diopters);
	}

	@FunctionalInterface
	public interface DistanceReader {
		CharacterHeightCalculator atDistance(double distance);
	}
}
