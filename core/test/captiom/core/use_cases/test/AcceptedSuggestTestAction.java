package captiom.core.use_cases.test;

import captiom.core.model.device.CharacterHeightCalculator;
import captiom.core.model.device.Eye;
import captiom.core.model.device.OptotypeCharacter;
import captiom.core.model.device.OptotypeCharacter.Snellen;
import captiom.core.model.test.Record;
import captiom.core.model.test.Suggestion;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AcceptedSuggestTestAction {

	@Test
	public void should_give_suggestion_with_highest_degrees_for_left_eye_if_there_are_no_records() {
		SuggestTestAction action = new SuggestTestAction(range(10, 110));

		Suggestion suggestion = action.suggestGiven(emptyList(), characters());

		assertThat(suggestion.degrees, is(110L));
		assertThat(suggestion.eye, is(Eye.LEFT));
	}

	@Test
	public void should_give_suggestion_with_lower_degrees_if_three_records_for_same_eye_are_correct() {
		SuggestTestAction action = new SuggestTestAction(range(10, 110));

		Suggestion suggestion = action.suggestGiven(threeCorrectRecordsForLeftEye(), characters());

		assertThat(suggestion.degrees, is(30L));
		assertThat(suggestion.eye, is(Eye.LEFT));
	}

	private List<Record> threeCorrectRecordsForLeftEye() {
		return asList(
				new Record(Snellen.C, "Snellen", 50, Eye.LEFT, true),
				new Record(Snellen.D, "Snellen", 50, Eye.LEFT, true),
				new Record(Snellen.N, "Snellen", 50, Eye.LEFT, true)
		);
	}

	private List<OptotypeCharacter> characters() {
		return asList(Snellen.values());
	}

	private CharacterHeightCalculator.Range range(int min, int max) {
		return new CharacterHeightCalculator.Range(min, max);
	}
}
