package captiom.core.model.device;

public class CharacterHeightCalculator {

	private Device device;
	private double distance;
	private double focalLength;
	private double charPosition;

	public DioptersReader rangeForDevice(Device device) {
		this.device = device;
		return this::storeDiopters;
	}

	private DistanceReader storeDiopters(double diopters) {
		this.focalLength = 1 / diopters;
		return this::calculateRange;
	}

	private Range calculateRange(double distance) {
		this.distance = distance;
		calculateCharPosition();
		return calculateRange();
	}

	private void calculateCharPosition() {
		this.charPosition = (float) Math.abs(-distance * focalLength / (focalLength - distance));
	}

	private Range calculateRange() {
		return new Range(min(), max());
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
		Range atDistance(double distance);
	}
}
