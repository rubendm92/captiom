package captiom.server.controllers;

import captiom.core.model.device.DeviceNotifier;
import captiom.core.model.device.DeviceService;
import captiom.core.model.device.Eye;
import captiom.core.model.device.OptotypeCharacter;
import captiom.core.use_cases.device.RefreshCharacterAction;
import org.junit.Test;
import spark.Request;
import spark.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class AcceptedRefreshDeviceController {

	@Test
	public void should_refresh_device_with_new_char() throws Exception {
		String deviceId = "11";
		DeviceService service = mock(DeviceService.class);
		DeviceNotifier.DeviceLink link = mock(DeviceNotifier.DeviceLink.class);
		when(service.using(deviceId)).thenReturn(link);

		RefreshDeviceController controller = new RefreshDeviceController(new RefreshCharacterAction(service));
		Response response = mock(Response.class);
		assertThat(controller.handle(request(deviceId), response), is("OK"));
		verify(response).status(200);
		verify(service).using(deviceId);
		verify(link).drawChar(OptotypeCharacter.TumblingE.OPENS_UP, 300, Eye.LEFT);
	}

	private Request request(String deviceId) {
		Request request = mock(Request.class);
		when(request.queryParams("deviceId")).thenReturn(deviceId);
		when(request.queryParams("char")).thenReturn("/");
		when(request.queryParams("detail")).thenReturn("300");
		when(request.queryParams("eye")).thenReturn("left");
		return request;
	}
}
