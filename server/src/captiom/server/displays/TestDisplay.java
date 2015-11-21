package captiom.server.displays;

import captiom.core.model.device.CharacterHeightCalculator;
import captiom.core.model.device.OptotypeCharacter;
import captiom.core.model.patient.Patient;
import captiom.core.model.test.Test;
import captiom.server.infrastructure.OptotypeCharacterMapper;
import captiom.server.infrastructure.Services;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.List;

public class TestDisplay implements Display {

	private final Patient patient;
	private final CharacterHeightCalculator.Range testRange;
	private final Services services;

	public TestDisplay(Patient patient, CharacterHeightCalculator.Range testRange, Services services) {
		this.patient = patient;
		this.testRange = testRange;
		this.services = services;
	}

	@Override
	public void show() {
		services.pushService().notify("Test", serializeConfiguration());
	}

	private String serializeConfiguration() {
		JsonObject object = new JsonObject();
		object.addProperty("patientId", patient.id);
		object.add("deviceRange", serializeRange());
		object.add("tests", serializeAvailableTests(services.testService().availableTests()));
		return object.toString();
	}

	private JsonElement serializeRange() {
		JsonObject object = new JsonObject();
		object.addProperty("min", (int) testRange.min);
		object.addProperty("max", (int) testRange.max);
		return object;
	}

	private JsonElement serializeAvailableTests(List<Test> tests) {
		return tests.stream().map(this::serialize).collect(JsonArray::new, JsonArray::add, JsonArray::addAll);
	}

	private JsonElement serialize(Test test) {
		JsonObject object = new JsonObject();
		object.addProperty("name", test.name());
		object.add("characters", serializeCharacters(test.characters()));
		return object;
	}

	private JsonElement serializeCharacters(List<OptotypeCharacter> characters) {
		return characters.stream()
				.map(OptotypeCharacterMapper::toString)
				.map(JsonPrimitive::new)
				.collect(JsonArray::new, JsonArray::add, JsonArray::addAll);
	}
}
