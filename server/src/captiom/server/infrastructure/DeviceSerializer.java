package captiom.server.infrastructure;

import captiom.core.model.device.Device;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

public class DeviceSerializer {

	public JsonElement serialize(List<Device> devices) {
		return devices.stream().map(this::serialize).collect(JsonArray::new, JsonArray::add, JsonArray::addAll);
	}

	private JsonElement serialize(Device device) {
		JsonObject object = new JsonObject();
		object.addProperty("id", device.id());
		object.addProperty("model", device.modelName());
		return object;
	}
}
