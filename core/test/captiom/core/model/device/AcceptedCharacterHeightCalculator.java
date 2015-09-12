package captiom.core.model.device;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.IsCloseTo.closeTo;

public class AcceptedCharacterHeightCalculator {

	private CharacterHeightCalculator calculator;
	private Device device;

	@Before
	public void setUp() {
		calculator = new CharacterHeightCalculator();
		device = new Device("id").height(new Height(1080, 0.06917));
	}

	@Test(expected = CalculatorNotConfiguredException.class)
	public void should_throw_not_configured_exception_when_calculator_has_not_been_setup() {
		calculator.imageHeightForMinutes(743);
	}

	@Test
	public void should_calculate_range_of_measurable_values_for_device_with_lenses_and_distance() {
		CharacterHeightCalculator.Range range = configuredCalculator().range();
		assertThat(range.min, closeTo(4.88, 0.1));
		assertThat(range.max, closeTo(743, 0.1));
	}

	@Test
	public void should_calculate_image_detail_in_pixels_for_minutes() {
		assertThat(configuredCalculator().imageHeightForMinutes(743), closeTo(1080, 1));
	}

	private CharacterHeightCalculator configuredCalculator() {
		return calculator.usingDevice(device).withDiopters(20).atDistance(0.045);
	}
}
