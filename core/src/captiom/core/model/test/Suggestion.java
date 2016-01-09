package captiom.core.model.test;

import captiom.core.model.device.Eye;
import captiom.core.model.device.OptotypeCharacter;

public class Suggestion {

	public final long degrees;
	public final OptotypeCharacter character;
	public final Eye eye;

	public Suggestion(long degrees, OptotypeCharacter character, Eye eye) {
		this.degrees = degrees;
		this.character = character;
		this.eye = eye;
	}
}
