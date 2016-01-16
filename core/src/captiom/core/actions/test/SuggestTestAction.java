package captiom.core.actions.test;

import captiom.core.model.device.CharacterHeightCalculator;
import captiom.core.model.device.Eye;
import captiom.core.model.device.OptotypeCharacter;
import captiom.core.model.test.Record;
import captiom.core.model.test.Suggestion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class SuggestTestAction {

	private static final Random RANDOM = new Random();
	private static final int TEST_THRESHOLD = 3;
	private final CharacterHeightCalculator.Range range;
	private final Map<Integer, SuggestionBuilder> actionBuilder = new HashMap<>();

	public SuggestTestAction(CharacterHeightCalculator.Range range) {
		this.range = range;
		actionBuilder.put(0, this::suggestionWithSameDegrees);
		actionBuilder.put(1, this::suggestionWithTwoThirdsMoreDegrees);
		actionBuilder.put(2, this::suggestionWithOneThirdMoreDegrees);
	}

	public Suggestion suggestGiven(List<Record> records, List<OptotypeCharacter> characters) {
		if (records.isEmpty()) {
			return suggestionWithMaximumValue(characters);
		}
		if (recordsForLastTestedEye(records).count() < TEST_THRESHOLD) {
			return suggestionWithSameDegrees(records, characters);
		}
		return actionBuilder.getOrDefault(rightRecordsForLastTest(records), this::suggestionWithLowerValue)
				.apply(records, characters);
	}

	private int rightRecordsForLastTest(List<Record> records) {
		return (int) recordsForLastTestedEye(records)
				.filter(r -> lastTest(records).detail == r.detail)
				.filter(r -> r.success)
				.count();
	}

	private Suggestion suggestionWithTwoThirdsMoreDegrees(List<Record> records, List<OptotypeCharacter> characters) {
		long degrees = (long) (lastTest(records).detail + 2 * (range.max - lastTest(records).detail) / 3);
		return new Suggestion(degrees, randomCharacter(characters), lastTest(records).eye);
	}

	private Suggestion suggestionWithMaximumValue(List<OptotypeCharacter> characters) {
		return new Suggestion((long) range.max, randomCharacter(characters), Eye.LEFT);
	}

	private Suggestion suggestionWithOneThirdMoreDegrees(List<Record> records, List<OptotypeCharacter> characters) {
		long degrees = (long) (lastTest(records).detail + (range.max - lastTest(records).detail) / 3);
		return new Suggestion(degrees, randomCharacter(characters), lastTest(records).eye);
	}

	private OptotypeCharacter randomCharacter(List<OptotypeCharacter> characters) {
		return characters.get(RANDOM.nextInt(characters.size()));
	}

	private Record lastTest(List<Record> records) {
		return records.get(records.size() - 1);
	}

	private Suggestion suggestionWithLowerValue(List<Record> records, List<OptotypeCharacter> characters) {
		long minValue = minValue(records);
		long degrees = (lastTest(records).detail - minValue) / 2 + minValue;
		return new Suggestion(degrees, randomCharacter(characters), lastTest(records).eye);
	}

	private long minValue(List<Record> records) {
		return recordsForLastTestedEye(records)
				.filter(r -> lastTest(records).detail > r.detail)
				.map(r -> r.detail)
				.findFirst()
				.orElse((long) range.min);
	}

	private Stream<Record> recordsForLastTestedEye(List<Record> records) {
		return records.stream()
				.filter(r -> lastTest(records).eye == r.eye);
	}

	private Suggestion suggestionWithSameDegrees(List<Record> records, List<OptotypeCharacter> characters) {
		return new Suggestion(lastTest(records).detail, randomCharacter(characters), lastTest(records).eye);
	}

	@FunctionalInterface
	private interface SuggestionBuilder extends BiFunction<List<Record>, List<OptotypeCharacter>, Suggestion> {
	}
}
