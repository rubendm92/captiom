package captiom.core.use_cases.test;

import captiom.core.model.device.CharacterHeightCalculator;
import captiom.core.model.device.Eye;
import captiom.core.model.device.OptotypeCharacter;
import captiom.core.model.test.Record;
import captiom.core.model.test.Suggestion;

import java.util.List;
import java.util.Random;

public class SuggestTestAction {

	private static final Random RANDOM = new Random();
	private final CharacterHeightCalculator.Range range;

	public SuggestTestAction(CharacterHeightCalculator.Range range) {
		this.range = range;
	}

	public Suggestion suggestGiven(List<Record> records, List<OptotypeCharacter> characters) {
		if (records.isEmpty()) {
			return suggestionWithMaximumValue(characters);
		}
		throw new UnsupportedOperationException("Not implemented yet");
	}

	private Suggestion suggestionWithMaximumValue(List<OptotypeCharacter> characters) {
		return new Suggestion((long) range.max, randomCharacter(characters), Eye.LEFT);
	}

	private OptotypeCharacter randomCharacter(List<OptotypeCharacter> characters) {
		return characters.get(RANDOM.nextInt(characters.size()));
	}
}
