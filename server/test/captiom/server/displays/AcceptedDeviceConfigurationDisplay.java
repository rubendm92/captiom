package captiom.server.displays;

import captiom.server.infrastructure.PushService;
import captiom.server.infrastructure.Services;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AcceptedDeviceConfigurationDisplay {

	@Test
	public void should_notify_via_push_to_device_configuration() {
		PushService pushService = mock(PushService.class);
		DeviceDisplay display = new DeviceDisplay(services(pushService));
		display.show();
		verify(pushService).notify("DeviceConfiguration");
	}


	private Services services(PushService pushService) {
		Services services = mock(Services.class);
		when(services.pushService()).thenReturn(pushService);
		return services;
	}
}
