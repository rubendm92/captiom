package captiom.server.controllers;

import captiom.core.model.device.Device;
import captiom.core.model.device.DeviceService;
import captiom.core.actions.device.RegisterDeviceAction;
import org.junit.Test;
import spark.Request;
import spark.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class AcceptedRegisterDeviceController {

	@Test
	public void should_save_device_using_device_service() throws Exception {
		DeviceService service = mock(DeviceService.class);
		RegisterDeviceController controller = new RegisterDeviceController(new RegisterDeviceAction(service));
		Request request = request();
		Response response = mock(Response.class);
		assertThat(controller.handle(request, response), is("OK"));
		verify(service).register(any(Device.class));
		verify(response).status(201);
	}

	private Request request() {
		Request request = mock(Request.class);
		when(request.queryParams("notificationId")).thenReturn("11");
		when(request.queryParams("pixelHeight")).thenReturn("1080");
		when(request.queryParams("metersHeight")).thenReturn("0.06917");
		return request;
	}
}
