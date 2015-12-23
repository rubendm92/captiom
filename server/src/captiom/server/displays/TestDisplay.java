package captiom.server.displays;

import captiom.core.model.device.CharacterHeightCalculator;
import captiom.core.model.device.Eye;
import captiom.core.model.device.OptotypeCharacter;
import captiom.core.model.patient.Patient;
import captiom.core.model.test.Record;
import captiom.core.model.test.Test;
import captiom.core.use_cases.device.RefreshCharacterAction;
import captiom.core.use_cases.test.AddTestRecordAction;
import captiom.core.use_cases.test.GetTestRecordsAction;
import captiom.server.infrastructure.OptotypeCharacterMapper;
import captiom.server.infrastructure.Services;
import com.google.gson.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class TestDisplay implements Display {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private final Patient patient;
	private final CharacterHeightCalculator calculator;
	private final String deviceId;
	private final Services services;
	private final RefreshCharacterAction refreshCharacter;
	private final AddTestRecordAction addTestRecord;
	private final GetTestRecordsAction getTestRecords;
	private final List<Record> unsavedRecords = new ArrayList<>();

	public TestDisplay(Patient patient, CharacterHeightCalculator calculator, String deviceId, Services services) {
		this.patient = patient;
		this.calculator = calculator;
		this.deviceId = deviceId;
		this.services = services;
		this.refreshCharacter = new RefreshCharacterAction(services.deviceService());
		this.addTestRecord = new AddTestRecordAction(services.testService());
		getTestRecords = new GetTestRecordsAction(services.testService());
	}

	@Override
	public void show() {
		services.pushService().notify("Test", serializeConfiguration());
	}

	private String serializeConfiguration() {
		JsonObject object = new JsonObject();
		object.addProperty("patientName", patient.name);
		object.add("deviceRange", serializeRange());
		object.add("tests", serializeAvailableTests(services.testService().availableTests()));
		object.add("history", serializePatientHistory(getTestRecords.forPatient(patient.id)));
		return object.toString();
	}

	private JsonElement serializeRange() {
		JsonObject object = new JsonObject();
		object.addProperty("min", (int) calculator.range().min);
		object.addProperty("max", (int) calculator.range().max);
		return object;
	}

	private JsonElement serializeAvailableTests(List<Test> tests) {
		return toJsonArray(tests.stream().map(this::serializeTest));
	}

	private JsonElement serializePatientHistory(Map<LocalDate, List<Record>> history) {
		return toJsonArray(history.entrySet().stream().map(this::serializeDayHistory));
	}

	private JsonElement serializeDayHistory(Map.Entry<LocalDate, List<Record>> entry) {
		JsonObject object = new JsonObject();
		object.addProperty("date", FORMATTER.format(entry.getKey()));
		object.add("results", toJsonArray(entry.getValue().stream().map(this::serializeRecord)));
		return object;
	}

	private JsonElement serializeRecord(Record record) {
		return new Gson().toJsonTree(record);
	}

	private JsonArray toJsonArray(Stream<JsonElement> stream) {
		return stream.collect(JsonArray::new, JsonArray::add, JsonArray::addAll);
	}

	private JsonElement serializeTest(Test test) {
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

	public void showChar(String character, long degrees, String eye) {
		refreshCharacter.using(deviceId)
				.show(OptotypeCharacterMapper.fromString(character))
				.withDetail(toPixels(degrees))
				.in(Eye.valueOf(eye.toUpperCase()));
	}

	private long toPixels(long degrees) {
		return (long) calculator.imageHeightForMinutes((int) degrees);
	}

	public void clearDevice() {
		refreshCharacter.clear(deviceId);
	}

	public void addRecord(String character, long detail, String eye, boolean success) {
		unsavedRecords.add(new Record(character, detail, Eye.valueOf(eye), success));
	}

	public void finishTest() {
		addTestRecord.add(patient.id, unsavedRecords.toArray(new Record[unsavedRecords.size()]));
		unsavedRecords.clear();
	}
}
