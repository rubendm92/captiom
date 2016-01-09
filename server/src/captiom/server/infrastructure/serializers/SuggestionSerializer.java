package captiom.server.infrastructure.serializers;

import captiom.core.model.test.Suggestion;
import captiom.server.infrastructure.OptotypeCharacterMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class SuggestionSerializer {

	public JsonElement serialize(Suggestion suggestion) {
		JsonObject object = new JsonObject();
		object.addProperty("degrees", suggestion.degrees);
		object.addProperty("character", OptotypeCharacterMapper.toString(suggestion.character));
		object.addProperty("eye", suggestion.eye.toString());
		return object;
	}
}
