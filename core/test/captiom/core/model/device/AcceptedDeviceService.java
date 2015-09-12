package captiom.core.model.device;

import captiom.core.infrastructure.device.DeviceNotifier;
import captiom.core.infrastructure.device.DeviceRepository;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AcceptedDeviceService {

	@Test
	public void should_register_device_into_repository_assigning_an_id_to_the_device() {
		Device device = new Device().withHeight(new Height(1080, 0.06917));
		Device deviceWithId = new Device("11").withHeight(new Height(1080, 0.06917));
		DeviceRepository repository = mock(DeviceRepository.class);
		when(repository.save(device)).thenReturn(deviceWithId);
		DeviceService service = new DeviceService(repository, notifier());
		assertThat(service.register(device).id(), is("11"));
		verify(repository).save(device);
	}

	private DeviceNotifier notifier() {
		return mock(DeviceNotifier.class);
	}
}
