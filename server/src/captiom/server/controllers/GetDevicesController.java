package captiom.server.controllers;

import captiom.core.actions.device.GetDevicesAction;
import captiom.server.infrastructure.serializers.DeviceSerializer;
import spark.Request;
import spark.Response;

public class GetDevicesController implements Controller {

	private final GetDevicesAction action;
	private final DeviceSerializer serializer = new DeviceSerializer();

	public GetDevicesController(GetDevicesAction action) {
		this.action = action;
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		response.status(200);
		return serializer.serialize(action.allDevices()).toString();
	}
}
