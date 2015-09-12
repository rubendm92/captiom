package captiom.core.use_cases;

import captiom.core.model.device.Device;
import captiom.core.model.device.DeviceService;
import captiom.core.model.device.Height;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AcceptedRegisterDeviceCommand {

	@Test
	public void should_register_new_device_in_service() {
		DeviceService service = mock(DeviceService.class);
		RegisterDeviceCommand command = new RegisterDeviceCommand(service);
		Device device = new Device("id").withHeight(new Height(1080, 0.06917));
		command.register(device);
		verify(service).register(device);
	}
}
