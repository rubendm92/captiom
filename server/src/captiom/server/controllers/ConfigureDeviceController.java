package captiom.server.controllers;

import captiom.server.displays.DeviceDisplay;
import captiom.server.infrastructure.DisplayService;
import com.google.gson.JsonObject;
import spark.Request;
import spark.Response;

public class ConfigureDeviceController implements Controller {

	private final DisplayService displayService;

	public ConfigureDeviceController(DisplayService displayService) {
		this.displayService = displayService;
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		displayService.display(DeviceDisplay.class).configure(buildConfiguration(Controller.bodyFrom(request)));
		response.status(200);
		return "OK";
	}

	private DeviceDisplay.Configuration buildConfiguration(JsonObject body) {
		return new DeviceDisplay.Configuration(body.get("deviceId").getAsString(), body.get("diopters").getAsDouble(), body.get("distance").getAsDouble() / 1000);
	}
}
