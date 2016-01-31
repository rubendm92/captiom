package captiom.server.controllers;

import captiom.server.displays.DeviceDisplay;
import captiom.server.infrastructure.DisplayService;
import org.junit.Test;
import spark.Request;
import spark.Response;

import static org.mockito.Mockito.*;

public class AcceptedConfigureDeviceController {
	
	@Test
	public void should_configure_device_on_display() throws Exception {
		DisplayService displayService = mock(DisplayService.class);
		DeviceDisplay deviceDisplay = mock(DeviceDisplay.class);
		when(displayService.display(DeviceDisplay.class)).thenReturn(deviceDisplay);
		ConfigureDeviceController controller = new ConfigureDeviceController(displayService);
		Request request = requestWithConfiguration();

		controller.handle(request, mock(Response.class));

		verify(displayService).display(DeviceDisplay.class);
		verify(deviceDisplay).configure(any(DeviceDisplay.Configuration.class));
	}

	private Request requestWithConfiguration() {
		Request request = mock(Request.class);
		when(request.body()).thenReturn("{\"deviceId\":\"111\",\"distance\":\"20\",\"diopters\":\"25\"}");
		return request;
	}
}
