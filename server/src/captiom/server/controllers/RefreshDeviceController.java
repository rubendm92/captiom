package captiom.server.controllers;

import captiom.core.model.device.Eye;
import captiom.core.model.device.OptotypeCharacter;
import captiom.core.use_cases.device.RefreshCharacterAction;
import captiom.server.infrastructure.OptotypeCharacterMapper;
import spark.Request;
import spark.Response;

public class RefreshDeviceController implements Controller {

	private final RefreshCharacterAction action;

	public RefreshDeviceController(RefreshCharacterAction action) {
		this.action = action;
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		action.using(device(request)).show(character(request)).withDetail(detail(request)).in(eye(request));
		response.status(200);
		return "OK";
	}

	private String device(Request request) {
		return request.queryParams("deviceId");
	}

	private OptotypeCharacter character(Request request) {
		return OptotypeCharacterMapper.fromString(request.queryParams("char"));
	}

	private long detail(Request request) {
		return Long.valueOf(request.queryParams("detail"));
	}

	private Eye eye(Request request) {
		return Eye.fromString(request.queryParams("eye"));
	}
}
