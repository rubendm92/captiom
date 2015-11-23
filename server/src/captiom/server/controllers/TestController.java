package captiom.server.controllers;

import captiom.server.displays.TestDisplay;
import captiom.server.infrastructure.DisplayService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class TestController implements Controller {

	private static final String SHOW_CHAR = "showChar";
	private static final String ADD_RECORD = "addRecord";
	private static final String FINISH = "finish";
	private final Map<String, BiFunction<JsonObject, Response, String>> actions = new HashMap<>();
	private final DisplayService displayService;

	public TestController(DisplayService displayService) {
		this.displayService = displayService;
		actions.put(SHOW_CHAR, this::showChar);
		actions.put(ADD_RECORD, this::addRecord);
		actions.put(FINISH, this::finish);
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		JsonObject body = bodyFrom(request);
		return actions.getOrDefault(body.get("operation").getAsString(), this::defaultAction).apply(body, response);
	}

	private JsonObject bodyFrom(Request request) {
		return GSON.fromJson(request.body(), JsonElement.class).getAsJsonObject();
	}

	private String showChar(JsonObject body, Response response) {
		double degrees = body.getAsJsonObject("character").get("value").getAsDouble();
		String measure = body.getAsJsonObject("character").get("measure").getAsString();
		String character = body.get("character").getAsString();
		String eye = body.get("eye").getAsString();
		testDisplay().showChar(character, degrees, measure, eye);
		return "OK";
	}

	private String addRecord(JsonObject body, Response response) {
		double degrees = body.getAsJsonObject("character").get("value").getAsDouble();
		String measure = body.getAsJsonObject("character").get("measure").getAsString();
		String character = body.get("character").getAsString();
		String eye = body.get("eye").getAsString();
		// TODO
		// testDisplay().saveRecord();
		return "OK";
	}

	private String finish(JsonObject body, Response response) {
		// TODO
		// testDisplay().finishTest();
		return "OK";
	}

	private TestDisplay testDisplay() {
		return displayService.display(TestDisplay.class);
	}

	private String defaultAction(JsonObject body, Response response) {
		response.status(501);
		return "No action defined for operation";
	}
}
