package captiom.server.controllers;

import captiom.core.model.device.Device;
import captiom.core.model.device.DeviceService;
import captiom.core.model.device.Height;
import spark.Request;
import spark.Response;
import spark.Route;

public class RegisterDeviceController implements Route {

	private final DeviceService service;

	public RegisterDeviceController(DeviceService service) {
		this.service = service;
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		service.register(buildDevice(request));
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
