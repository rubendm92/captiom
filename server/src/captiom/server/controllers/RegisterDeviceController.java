package captiom.server.controllers;

import captiom.core.model.device.Device;
import captiom.core.model.device.Height;
import captiom.core.use_cases.device.RegisterDeviceAction;
import spark.Request;
import spark.Response;
import spark.Route;

public class RegisterDeviceController implements Route {

	private final RegisterDeviceAction action;

	public RegisterDeviceController(RegisterDeviceAction action) {
		this.action = action;
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		action.register(buildDevice(request));
		response.status(201);
		return "OK";
	}

	private Device buildDevice(Request request) {
		return new Device().height(buildHeight(request))
				.notificationId(request.queryParams("notificationId"));
	}

	private Height buildHeight(Request request) {
		return new Height(asDouble(request.queryParams("pixelHeight")), asDouble(request.queryParams("metersHeight")));
	}

	private double asDouble(String string) {
		return Double.valueOf(string);
	}
}
