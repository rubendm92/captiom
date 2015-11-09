package captiom.server.controllers;

import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Response;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AcceptedDeviceController {

	private RegisterDeviceController registerDeviceController;
	private ConfigureDeviceController configureDeviceController;
	private DeviceController controller;

	@Before
	public void setUp() throws Exception {
		registerDeviceController = mock(RegisterDeviceController.class);
		configureDeviceController = mock(ConfigureDeviceController.class);
		controller = new DeviceController(registerDeviceController, configureDeviceController);
	}

	@Test
	public void should_execute_register_controller_when_request_operation_is_register() throws Exception {
		Request request = request("register");
		Response response = mock(Response.class);
		controller.handle(request, response);
		verify(registerDeviceController).handle(request, response);
	}

	@Test
	public void should_execute_configure_controller_when_request_operation_is_configure() throws Exception {
		Request request = request("configure");
		Response response = mock(Response.class);
		controller.handle(request, response);
		verify(configureDeviceController).handle(request, response);
	}

	private Request request(String operation) {
		Request request = mock(Request.class);
		when(request.body()).thenReturn("{\"operation\":\"" + operation + "\"}");
		return request;
	}
}
