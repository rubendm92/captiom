package captiom.server.displays;

import captiom.core.model.device.CharacterHeightCalculator;
import captiom.core.model.device.Eye;
import captiom.core.model.patient.Patient;
import captiom.core.model.test.Record;
import captiom.core.use_cases.device.RefreshCharacterAction;
import captiom.core.use_cases.test.AddTestRecordAction;
import captiom.core.use_cases.test.GetTestRecordsAction;
import captiom.server.infrastructure.OptotypeCharacterMapper;
import captiom.server.infrastructure.Services;
import captiom.server.infrastructure.serializers.TestDisplayConfigurationSerializer;

import java.util.ArrayList;
import java.util.List;

public class TestDisplay implements Display {

	private static final TestDisplayConfigurationSerializer serializer = new TestDisplayConfigurationSerializer();
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
		this.getTestRecords = new GetTestRecordsAction(services.testService());
	}

	@Override
	public void show() {
		services.pushService().notify("Test", serializeConfiguration());
	}

	private String serializeConfiguration() {
		return serializer.serialize(patient, calculator.range(), services.testService().availableTests(), getTestRecords.forPatient(patient.id)).toString();
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

	public void addRecord(Record record) {
		unsavedRecords.add(record);
	}

	public void finishTest() {
		addTestRecord.add(patient.id, unsavedRecords.toArray(new Record[unsavedRecords.size()]));
		unsavedRecords.clear();
	}
}
