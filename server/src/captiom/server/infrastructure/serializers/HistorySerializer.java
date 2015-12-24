package captiom.server.infrastructure.serializers;

import captiom.core.model.test.Record;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

class HistorySerializer {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public JsonElement serialize(Map<LocalDate, List<Record>> history) {
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
}
