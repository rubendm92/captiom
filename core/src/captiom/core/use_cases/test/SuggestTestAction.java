package captiom.core.use_cases.test;

import captiom.core.model.device.CharacterHeightCalculator;
import captiom.core.model.device.Eye;
import captiom.core.model.device.OptotypeCharacter;
import captiom.core.model.test.Record;
import captiom.core.model.test.Suggestion;

import java.util.List;

public class SuggestTestAction {

	private final CharacterHeightCalculator.Range range;

	public SuggestTestAction(CharacterHeightCalculator.Range range) {
		this.range = range;
	}

	public Suggestion suggestGiven(List<Record> records, List<OptotypeCharacter> characters) {
		return new Suggestion(30, OptotypeCharacter.Snellen.C, Eye.LEFT);
	}
}
