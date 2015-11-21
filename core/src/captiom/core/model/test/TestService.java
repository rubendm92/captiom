package captiom.core.model.test;

import captiom.core.model.device.OptotypeCharacter;

import java.util.ArrayList;
import java.util.List;

import static captiom.core.model.device.OptotypeCharacter.*;
import static java.util.Arrays.asList;

public class TestService {

	private final List<Test> availableTests;

	public TestService() {
		availableTests = asList(landolt(), snellen(), tumblingE());
	}

	public List<Test> availableTests() {
		return availableTests;
	}

	private Test landolt() {
		return new Test("Landolt", toList(C.values()));
	}

	private Test snellen() {
		return new Test("Snellen", toList(Snellen.values()));
	}

	private Test tumblingE() {
		return new Test("Tumbling E", toList(TumblingE.values()));
	}

	private ArrayList<OptotypeCharacter> toList(OptotypeCharacter[] characteres) {
		return new ArrayList<>(asList(characteres));
	}
}
