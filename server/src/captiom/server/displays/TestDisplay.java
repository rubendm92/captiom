package captiom.server.displays;

import captiom.core.model.device.CharacterHeightCalculator;
import captiom.core.model.patient.Patient;
import captiom.server.infrastructure.Services;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
		return object.toString();
	}

	private JsonElement serializeRange() {
		JsonObject object = new JsonObject();
		object.addProperty("min", (int) testRange.min);
		object.addProperty("max", (int) testRange.max);
		return object;
	}
}
