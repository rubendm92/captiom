package captiom.core.use_cases.device;

import captiom.core.infrastructure.device.DeviceNotifier;
import captiom.core.model.device.DeviceService;
import captiom.core.model.device.Eye;
import captiom.core.model.device.OptotypeCharacter.Snellen;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class AcceptedRefreshCharacterAction {

	@Test
	public void should_notify_device_to_refresh_character_being_displayed() {
		String deviceId = "11";
		DeviceService service = mock(DeviceService.class);
		DeviceNotifier.DeviceLink link = mock(DeviceNotifier.DeviceLink.class);
		when(service.using(deviceId)).thenReturn(link);
		RefreshCharacterAction action = new RefreshCharacterAction(service);
		action.using(deviceId).show(Snellen.OPENS_UP).withDetail(320).in(Eye.LEFT);
		verify(service).using(deviceId);
		verify(link).drawChar(Snellen.OPENS_UP, 320, Eye.LEFT);
	}
}
