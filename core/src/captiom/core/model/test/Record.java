package captiom.core.model.test;

import captiom.core.model.device.Eye;
import captiom.core.model.device.OptotypeCharacter;

public class Record {

	public final OptotypeCharacter character;
	public final String testName;
	public final long detail;
	public final Eye eye;
	public final boolean success;

	public Record(OptotypeCharacter character, String testName, long detail, Eye eye, boolean success) {
		this.character = character;
		this.testName = testName;
		this.detail = detail;
		this.eye = eye;
		this.success = success;
	}
}
