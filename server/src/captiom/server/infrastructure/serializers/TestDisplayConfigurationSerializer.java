package captiom.server.infrastructure.serializers;

import captiom.core.model.device.CharacterHeightCalculator;
import captiom.core.model.patient.Patient;
import captiom.core.model.test.Record;
import captiom.core.model.test.Test;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class TestDisplayConfigurationSerializer {

	private static final Gson gson = new Gson();
	private static final TestSerializer testSerializer = new TestSerializer();
	private static final HistorySerializer historySerializer = new HistorySerializer();

	public JsonElement serialize(Patient patient, CharacterHeightCalculator.Range range, List<Test> tests, Map<LocalDate, List<Record>> history) {
		JsonObject object = new JsonObject();
		object.addProperty("patientName", patient.name);
		object.add("deviceRange", gson.toJsonTree(range));
		object.add("tests", testSerializer.serialize(tests));
		object.add("history", historySerializer.serialize(history));
		return object;
	}
}
