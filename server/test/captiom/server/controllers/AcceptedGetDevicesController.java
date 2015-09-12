package captiom.server.controllers;

import captiom.core.model.device.Device;
import captiom.core.model.device.DeviceService;
import captiom.core.model.device.Height;
import captiom.core.use_cases.device.GetDevicesAction;
import org.junit.Test;
import spark.Request;
import spark.Response;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class AcceptedGetDevicesController {

	@Test
	public void should_return_a_json_list_with_all_devices() throws Exception {
		GetDevicesController controller = new GetDevicesController(action());
		Response response = mock(Response.class);
		assertThat(controller.handle(mock(Request.class), response), is(expectedDeviceList()));
		verify(response).status(200);
	}

	private GetDevicesAction action() {
		DeviceService service = mock(DeviceService.class);
		when(service.all()).thenReturn(asList(firstDevice(), secondDevice()));
		return new GetDevicesAction(service);
	}

	private Device firstDevice() {
		return new Device("1").height(new Height(1080, 0.06791)).notificationId("11").modelName("Device 1");
	}

	private Device secondDevice() {
		return new Device("2").height(new Height(720, 0.04591)).notificationId("1123").modelName("Device 2");
	}

	private String expectedDeviceList() {
		return "[{\"id\":\"1\",\"modelName\":\"Device 1\"},{\"id\":\"2\",\"modelName\":\"Device 2\"}]";
	}
}
