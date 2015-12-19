package captiom.server.displays;

import captiom.core.model.device.CharacterHeightCalculator;
import captiom.core.model.device.Eye;
import captiom.core.model.device.OptotypeCharacter;
import captiom.core.model.patient.Patient;
import captiom.core.model.test.Test;
import captiom.core.use_cases.device.RefreshCharacterAction;
import captiom.server.infrastructure.OptotypeCharacterMapper;
import captiom.server.infrastructure.Services;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.List;

public class TestDisplay implements Display {

	private final Patient patient;
	private final CharacterHeightCalculator calculator;
	private final String deviceId;
	private final Services services;
	private final RefreshCharacterAction refreshCharacterAction;

	public TestDisplay(Patient patient, CharacterHeightCalculator calculator, String deviceId, Services services) {
		this.patient = patient;
		this.calculator = calculator;
		this.deviceId = deviceId;
		this.services = services;
		this.refreshCharacterAction = new RefreshCharacterAction(services.deviceService());
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
		object.addProperty("min", (int) calculator.range().min);
		object.addProperty("max", (int) calculator.range().max);
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

	public void showChar(String character, double degrees, String measure, String eye) {
		refreshCharacterAction.using(deviceId)
				.show(OptotypeCharacterMapper.fromString(character))
				.withDetail(toPixels(degrees, measure))
				.in(Eye.valueOf(eye.toUpperCase()));
	}

	private long toPixels(double degrees, String measure) {
		return (long) calculator.imageHeightForMinutes((int) degrees);
	}

	public void clearDevice() {
		refreshCharacterAction.clear(deviceId);
	}

	public void addRecord(String character, double degrees, String measure, String eye, boolean success) {

	}

	public void finishTest() {

	}
}
