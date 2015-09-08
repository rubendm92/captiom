package captiom.core.model.device;

import captiom.core.model.device.dimension.Dimension;
import captiom.core.model.device.dimension.Height;
import captiom.core.model.device.dimension.Width;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.IsCloseTo.closeTo;

public class AcceptedCharacterHeightCalculator {

	@Test
	public void should_calculate_range_of_measurable_values_for_device_with_lenses_and_distance() {
		CharacterHeightCalculator calculator = new CharacterHeightCalculator();
		CharacterHeightCalculator.Range range = calculator.rangeForDevice(device()).withDiopters(20).atDistance(0.045);
		assertThat(range.min, closeTo(4.88, 0.1));
		assertThat(range.max, closeTo(743, 0.1));
	}

	private Device device() {
		return new Device("id").withDimension(new Dimension(new Width(1920, 0.13784), new Height(1080, 0.06917)));
	}
}
