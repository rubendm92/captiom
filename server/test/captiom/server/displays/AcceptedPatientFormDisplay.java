package captiom.server.displays;

import captiom.server.infrastructure.PushService;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AcceptedPatientFormDisplay {

	@Test
	public void should_notify_configuration_via_push_on_init() {
		PushService service = mock(PushService.class);
		PatientFormDisplay display = new PatientFormDisplay(service);
		display.init();
		verify(service).notify("PatientFormConfiguration", "{\"patientIdLength\":8}");
	}
}
