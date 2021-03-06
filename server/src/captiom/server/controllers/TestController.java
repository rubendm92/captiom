package captiom.server.controllers;

import captiom.core.model.device.Eye;
import captiom.core.model.device.OptotypeCharacter;
import captiom.core.model.test.Record;
import captiom.server.displays.TestDisplay;
import captiom.server.infrastructure.DisplayService;
import captiom.server.infrastructure.OptotypeCharacterMapper;
import com.google.gson.JsonObject;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import static captiom.server.controllers.Controller.*;

public class TestController implements Controller {

	private static final String SELECT_TEST = "selectTest";
	private static final String SHOW_CHAR = "showChar";
	private static final String ADD_RECORD = "addRecord";
	private static final String SUGGEST = "suggest";
	private static final String CLEAR = "clear";
	private static final String FINISH = "finish";
	private final Map<String, BiFunction<JsonObject, Response, String>> actions = new HashMap<>();
	private final DisplayService displayService;

	public TestController(DisplayService displayService) {
		this.displayService = displayService;
		actions.put(SELECT_TEST, this::selectTest);
		actions.put(SHOW_CHAR, this::showChar);
		actions.put(ADD_RECORD, this::addRecord);
		actions.put(SUGGEST, this::suggest);
		actions.put(FINISH, this::finish);
		actions.put(CLEAR, this::clearDevice);
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		JsonObject body = bodyFrom(request);
		return actions.getOrDefault(body.get("operation").getAsString(), this::defaultAction).apply(body, response);
	}

	private String selectTest(JsonObject body, Response response) {
		testDisplay().selectTest(body.get("test").getAsString());
		return "OK";
	}

	private String showChar(JsonObject body, Response response) {
		long detail = body.get("detail").getAsLong();
		String character = body.get("character").getAsString();
		String eye = body.get("eye").getAsString();
		testDisplay().showChar(character, detail, eye);
		return "OK";
	}

	private String addRecord(JsonObject body, Response response) {
		long detail = body.get("detail").getAsLong();
		String testName = body.get("testName").getAsString();
		OptotypeCharacter character = OptotypeCharacterMapper.fromString(body.get("character").getAsString());
		String eye = body.get("eye").getAsString();
		boolean success = body.get("success").getAsBoolean();
		testDisplay().addRecord(new Record(character, testName, detail, Eye.valueOf(eye), success));
		return "OK";
	}

	private String suggest(JsonObject body, Response response) {
		testDisplay().suggest();
		return "OK";
	}

	private String finish(JsonObject body, Response response) {
		testDisplay().finishTest();
		return "OK";
	}

	private String clearDevice(JsonObject body, Response response) {
		testDisplay().clearDevice();
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
