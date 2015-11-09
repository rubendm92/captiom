package captiom.server.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class DeviceController implements Controller {

	private final Map<String, Controller> controllers = new HashMap<>();
	private final Controller defaultController;
	private final Gson gson;

	public DeviceController(RegisterDeviceController registerDeviceController, ConfigureDeviceController configureDeviceController) {
		controllers.put("register", registerDeviceController);
		controllers.put("configure", configureDeviceController);
		defaultController = (request, response) -> null;
		gson = new Gson();
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		return controller(operation(request)).handle(request, response);
	}

	private Controller controller(String operation) {
		return controllers.getOrDefault(operation, defaultController);
	}

	private String operation(Request request) {
		JsonObject object = gson.fromJson(request.body(), JsonObject.class);
		return object.get("operation").getAsString().toLowerCase();
	}
}
