package captiom.core.use_cases.device;

import captiom.core.model.device.Device;
import captiom.core.model.device.DeviceService;
import captiom.core.model.device.Height;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AcceptedRegisterDeviceAction {

	@Test
	public void should_register_new_device_in_service() {
		DeviceService service = mock(DeviceService.class);
		RegisterDeviceAction action = new RegisterDeviceAction(service);
		Device device = new Device("id").height(new Height(1080, 0.06917));
		action.register(device);
		verify(service).register(device);
	}
}
