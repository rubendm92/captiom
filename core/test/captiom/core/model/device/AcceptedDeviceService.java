package captiom.core.model.device;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AcceptedDeviceService {

	@Test
	public void should_register_device_into_repository() {
		Device device = new Device("11").height(new Height(1080, 0.06917));
		DeviceRepository repository = mock(DeviceRepository.class);
		DeviceService service = new DeviceService(repository, notifier());

		service.register(device);

		verify(repository).save(device);
	}

	private DeviceNotifier notifier() {
		return mock(DeviceNotifier.class);
	}
}
