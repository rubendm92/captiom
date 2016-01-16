package captiom.server.infrastructure.serializers;

import captiom.core.model.test.History;
import captiom.core.model.test.Record;
import captiom.server.infrastructure.OptotypeCharacterMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public class HistorySerializer {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public JsonElement serialize(History history) {
		return toJsonArray(history.entries().map(this::serializeDayHistory));
	}

	private JsonElement serializeDayHistory(History.Entry entry) {
		JsonObject object = new JsonObject();
		object.addProperty("date", FORMATTER.format(entry.date));
		object.add("results", toJsonArray(entry.records.stream().map(this::serializeRecord)));
		return object;
	}

	private JsonElement serializeRecord(Record record) {
		JsonObject object = new JsonObject();
		object.addProperty("character", OptotypeCharacterMapper.toString(record.character));
		object.addProperty("testName", record.testName);
		object.addProperty("detail", record.detail);
		object.addProperty("eye", record.eye.toString());
		object.addProperty("success", record.success);
		return object;
	}

	private JsonArray toJsonArray(Stream<JsonElement> stream) {
		return stream.collect(JsonArray::new, JsonArray::add, JsonArray::addAll);
	}
}
