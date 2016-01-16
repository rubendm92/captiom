package captiom.server.displays;

import captiom.server.infrastructure.DisplayService;
import captiom.server.infrastructure.Services;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class AcceptedApplicationDisplay {

	@Test
	public void should_notify_to_create_a_patient_form_on_create_application() {
		DisplayService displayService = mock(DisplayService.class);
		PatientFormDisplay patientFormDisplay = mock(PatientFormDisplay.class);
		when(displayService.display(PatientFormDisplay.class)).thenReturn(patientFormDisplay);
		Services services = services(displayService);

		new ApplicationDisplay(services);

		verify(displayService).register(any(PatientFormDisplay.class));
		verify(displayService, atLeastOnce()).display(PatientFormDisplay.class);
		verify(patientFormDisplay).show();
	}

	private Services services(DisplayService displayService) {
		Services services = mock(Services.class);
		when(services.displayService()).thenReturn(displayService);
		return services;
	}
}
