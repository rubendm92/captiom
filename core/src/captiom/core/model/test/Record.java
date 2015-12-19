package captiom.core.model.test;

import captiom.core.model.device.Eye;

public class Record {

	public final String character;
	public final Measure measure;
	public final Eye eye;
	public final boolean success;

	public Record(String character, Measure measure, Eye eye, boolean success) {
		this.character = character;
		this.measure = measure;
		this.eye = eye;
		this.success = success;
	}

	public static class Measure {
		public final double value;
		public final String unit;

		public Measure(double value, String unit) {
			this.value = value;
			this.unit = unit;
		}
	}
}
