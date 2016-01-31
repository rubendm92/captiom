package captiom.server.displays;

import captiom.server.infrastructure.PushService;
import captiom.server.infrastructure.Services;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AcceptedPatientFormDisplay {

	@Test
	public void should_notify_via_push_to_show_patient_form_with_configuration() {
		PushService pushService = mock(PushService.class);
		PatientFormDisplay display = new PatientFormDisplay(services(pushService));
		display.show();
		verify(pushService).notify("PatientForm", "{\"patientIdLength\":8}");
	}

	private Services services(PushService pushService) {
		Services services = mock(Services.class);
		when(services.pushService()).thenReturn(pushService);
		return services;
	}
}
