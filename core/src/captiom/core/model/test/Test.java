package captiom.core.model.test;

import captiom.core.model.device.OptotypeCharacter;

import java.util.List;

import static java.util.Collections.unmodifiableList;

public class Test {

	private final String name;
	private final List<OptotypeCharacter> characters;

	public Test(String name, List<OptotypeCharacter> characters) {
		this.name = name;
		this.characters = characters;
	}

	public String name() {
		return name;
	}

	public List<OptotypeCharacter> characters() {
		return unmodifiableList(characters);
	}
}
