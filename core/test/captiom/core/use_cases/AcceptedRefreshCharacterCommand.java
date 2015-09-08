package captiom.core.use_cases;

import captiom.core.model.device.DeviceService;
import captiom.core.model.device.OptotypeCharacter;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class AcceptedRefreshCharacterCommand {

	@Test
	public void should_notify_device_to_refresh_character_being_displayed() {
		String deviceId = "11";
		DeviceService service = mock(DeviceService.class);
		DeviceService.DeviceLink link = mock(DeviceService.DeviceLink.class);
		when(service.using(deviceId)).thenReturn(link);
		RefreshCharacterCommand command = new RefreshCharacterCommand(service);
		command.using(deviceId).show(OptotypeCharacter.Snellen.OPENS_UP).withHeight(960);
		verify(service).using(deviceId);
		verify(link).drawChar(OptotypeCharacter.Snellen.OPENS_UP, 960);
	}
}
