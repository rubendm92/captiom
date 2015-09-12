package captiom.server.controllers;

import captiom.core.model.device.Device;
import captiom.core.model.device.DeviceService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;

public class GetDevicesController implements Route {

	private final DeviceService service;

	public GetDevicesController(DeviceService service) {
		this.service = service;
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.status(200);
		return serialize(service.all()).toString();
	}

	private JsonElement serialize(List<Device> devices) {
		return devices.stream().map(this::serialize).collect(JsonArray::new, JsonArray::add, JsonArray::addAll);
	}

	private JsonElement serialize(Device device) {
		JsonObject object = new JsonObject();
		object.addProperty("id", device.id());
		object.addProperty("modelName", device.modelName());
		return object;
	}
}
