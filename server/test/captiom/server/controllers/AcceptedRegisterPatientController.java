package captiom.server.controllers;

import captiom.core.model.patient.Gender;
import captiom.core.model.patient.Patient;
import captiom.server.displays.PatientFormDisplay;
import captiom.server.infrastructure.DisplayService;
import org.junit.Test;
import spark.Request;
import spark.Response;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class AcceptedRegisterPatientController {

	@Test
	public void should_save_patient() throws Exception {
		DisplayService displayService = mock(DisplayService.class);
		PatientFormDisplay patientFormDisplay = mock(PatientFormDisplay.class);
		when(displayService.display(PatientFormDisplay.class)).thenReturn(patientFormDisplay);
		RegisterPatientController controller = new RegisterPatientController(displayService);
		Request request = requestWithPatientData();
		Response response = mock(Response.class);

		assertThat(controller.handle(request, response), is("OK"));

		verify(response).status(200);
		verify(patientFormDisplay).patient(new Patient("111", "Ruben", LocalDate.of(1992, 6, 22), Gender.MALE));
	}

	private Request requestWithPatientData() {
		Request request = mock(Request.class);
		when(request.body()).thenReturn("{\"patientId\":\"111\",\"name\":\"Ruben\",\"birthDate\":\"709171200000\",\"gender\":\"male\"}");
		return request;
	}
}
