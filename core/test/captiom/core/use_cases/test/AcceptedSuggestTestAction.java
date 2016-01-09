package captiom.core.use_cases.test;

import captiom.core.model.device.CharacterHeightCalculator;
import captiom.core.model.device.Eye;
import captiom.core.model.device.OptotypeCharacter;
import captiom.core.model.device.OptotypeCharacter.Snellen;
import captiom.core.model.test.Record;
import captiom.core.model.test.Suggestion;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AcceptedSuggestTestAction {

	private final List<OptotypeCharacter> characters = asList(Snellen.values());
	private static SuggestTestAction action;

	@BeforeClass
	public static void setUpClass() throws Exception {
		action = new SuggestTestAction(new CharacterHeightCalculator.Range(10, 110));
	}

	@Test
	public void should_give_suggestion_with_highest_degrees_for_left_eye_if_there_are_no_records() {
		Suggestion suggestion = action.suggestGiven(emptyList(), characters);

		assertThat(suggestion.degrees, is(110L));
	}

	@Test
	public void should_give_suggestion_with_lower_degrees_if_three_records_for_same_eye_are_correct() {
		Suggestion suggestion = action.suggestGiven(threeCorrectRecords(), characters);

		assertThat(suggestion.degrees, is(30L));
	}
	
	@Test
	public void should_give_suggestion_with_same_degrees_if_three_records_are_wrong() {
		Suggestion suggestion = action.suggestGiven(threeWrongRecords(), characters);

		assertThat(suggestion.degrees, is(50L));
	}

	@Test
	public void should_give_suggestion_with_more_degrees_if_there_is_one_wrong_record() {
		Suggestion suggestion = action.suggestGiven(oneWrongRecord(), characters);

		assertThat(suggestion.degrees, is(70L));
	}

	@Test
	public void should_give_suggestion_with_more_degrees_if_there_is_two_wrong_records() {
		Suggestion suggestion = action.suggestGiven(twoWrongRecords(), characters);

		assertThat(suggestion.degrees, is(90L));
	}

	private List<Record> oneWrongRecord() {
		return asList(
				new Record(Snellen.C, "Snellen", 50, Eye.LEFT, true),
				new Record(Snellen.D, "Snellen", 50, Eye.LEFT, false),
				new Record(Snellen.N, "Snellen", 50, Eye.LEFT, true)
		);
	}

	private List<Record> twoWrongRecords() {
		return asList(
				new Record(Snellen.C, "Snellen", 50, Eye.LEFT, true),
				new Record(Snellen.D, "Snellen", 50, Eye.LEFT, false),
				new Record(Snellen.N, "Snellen", 50, Eye.LEFT, false)
		);
	}

	private List<Record> threeCorrectRecords() {
		return threeRecordsWithSameResult(true);
	}

	private List<Record> threeWrongRecords() {
		return threeRecordsWithSameResult(false);
	}

	private List<Record> threeRecordsWithSameResult(boolean success) {
		return asList(
				new Record(Snellen.C, "Snellen", 50, Eye.LEFT, success),
				new Record(Snellen.D, "Snellen", 50, Eye.LEFT, success),
				new Record(Snellen.N, "Snellen", 50, Eye.LEFT, success)
		);
	}

}
