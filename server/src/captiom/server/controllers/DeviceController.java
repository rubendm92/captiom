package captiom.server.controllers;

import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class DeviceController implements Controller {

	private final Map<String, Controller> controllers = new HashMap<>();
	private final Controller defaultController;

	public DeviceController(RegisterDeviceController registerDeviceController, ConfigureDeviceController configureDeviceController) {
		controllers.put("register", registerDeviceController);
		controllers.put("configure", configureDeviceController);
		defaultController = (request, response) -> null;
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		return controllers.getOrDefault(operation(request), defaultController).handle(request, response);
	}

	private String operation(Request request) {
		return request.queryParams("operation").toLowerCase();
	}
}
