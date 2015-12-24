package captiom.server.infrastructure.serializers;

import captiom.core.model.device.OptotypeCharacter;
import captiom.core.model.test.Test;
import captiom.server.infrastructure.OptotypeCharacterMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.List;
import java.util.stream.Stream;

class TestSerializer {

	public JsonElement serialize(List<Test> tests) {
		return toJsonArray(tests.stream().map(this::serializeTest));
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
}
