package captiom.core.use_cases.test;

import captiom.core.model.device.CharacterHeightCalculator;
import captiom.core.model.device.Eye;
import captiom.core.model.device.OptotypeCharacter;
import captiom.core.model.test.Record;
import captiom.core.model.test.Suggestion;

import java.util.List;
import java.util.Random;

public class SuggestTestAction {

	private static final int TEST_THRESHOLD = 3;
	private static final Random RANDOM = new Random();
	private final CharacterHeightCalculator.Range range;

	public SuggestTestAction(CharacterHeightCalculator.Range range) {
		this.range = range;
	}

	public Suggestion suggestGiven(List<Record> records, List<OptotypeCharacter> characters) {
		if (records.isEmpty()) {
			return suggestionWithMaximumValue(characters);
		}
		if (thereAreEnoughRecordsRightForLastEyeTested(records)) {
			return suggestionWithLowerValue(lastTest(records), characters);
		}
		if (allRecordsForLastEyeAreWrong(records)) {
			return suggestionWithSameDegrees(lastTest(records), characters);
		}
		throw new UnsupportedOperationException("Not implemented yet");
	}

	private boolean allRecordsForLastEyeAreWrong(List<Record> records) {
		return records.stream()
				.filter(r -> lastTest(records).eye == r.eye)
				.filter(r -> lastTest(records).detail == r.detail)
				.noneMatch(r -> r.success);
	}

	private Suggestion suggestionWithMaximumValue(List<OptotypeCharacter> characters) {
		return new Suggestion((long) range.max, randomCharacter(characters), Eye.LEFT);
	}

	private OptotypeCharacter randomCharacter(List<OptotypeCharacter> characters) {
		return characters.get(RANDOM.nextInt(characters.size()));
	}

	private boolean thereAreEnoughRecordsRightForLastEyeTested(List<Record> records) {
		return records.stream()
				.filter(r -> lastTest(records).eye == r.eye)
				.filter(r -> lastTest(records).detail == r.detail)
				.filter(r -> r.success)
				.count() >= TEST_THRESHOLD;
	}

	private Record lastTest(List<Record> records) {
		return records.get(records.size() - 1);
	}

	private Suggestion suggestionWithLowerValue(Record record, List<OptotypeCharacter> characters) {
		long degrees = (long) (((record.detail - range.min) / 2) + range.min);
		return new Suggestion(degrees, randomCharacter(characters), record.eye);
	}

	private Suggestion suggestionWithSameDegrees(Record record, List<OptotypeCharacter> characters) {
		return new Suggestion(record.detail, randomCharacter(characters), record.eye);
	}
}
