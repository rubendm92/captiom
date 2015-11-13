package captiom.server.displays;

import captiom.core.model.device.CharacterHeightCalculator;
import captiom.core.model.device.OptotypeCharacter;
import captiom.core.model.patient.Patient;
import captiom.server.infrastructure.OptotypeCharacterMapper;
import captiom.server.infrastructure.Services;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import static java.util.Arrays.asList;

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
		services.pushService().notify("Test", serializeContent());
	}

	private String serializeContent() {
		JsonObject object = new JsonObject();
		object.addProperty("patientId", patient.id);
		object.add("deviceRange", serializeRange());
		object.add("characters", serializeCharacterList());
		return object.toString();
	}

	private JsonElement serializeCharacterList() {
		return asList(OptotypeCharacter.C.values())
				.stream().map(OptotypeCharacterMapper::toString)
				.map(JsonPrimitive::new)
				.collect(JsonArray::new, JsonArray::add, JsonArray::addAll);
	}

	private JsonElement serializeRange() {
		JsonObject object = new JsonObject();
		object.addProperty("min", (int) testRange.min);
		object.addProperty("max", (int) testRange.max);
		return object;
	}
}
