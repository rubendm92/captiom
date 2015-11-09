package captiom.server.controllers;

import captiom.server.displays.DeviceDisplay;
import captiom.server.infrastructure.DisplayService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import spark.Request;
import spark.Response;

public class ConfigureDeviceController implements Controller {

	private final DisplayService displayService;
	private final Gson gson;

	public ConfigureDeviceController(DisplayService displayService) {
		this.displayService = displayService;
		this.gson = new Gson();
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		displayService.display(DeviceDisplay.class).configure(buildConfiguration(request.body()));
		response.status(200);
		return "OK";
	}

	private DeviceDisplay.Configuration buildConfiguration(String body) {
		JsonObject object = gson.fromJson(body, JsonObject.class);
		return new DeviceDisplay.Configuration(object.get("deviceId").getAsString(), object.get("diopters").getAsDouble(), object.get("distance").getAsDouble() / 1000);
	}
}
