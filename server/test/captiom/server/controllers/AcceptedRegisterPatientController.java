package captiom.server.controllers;

import captiom.core.use_cases.patient.RegisterPatientAction;
import captiom.server.infrastructure.PushService;
import org.junit.Test;
import spark.Request;
import spark.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AcceptedRegisterPatientController {

	@Test
	public void should_save_patient_and_notify_show_patient_with_data() throws Exception {
		PushService pushService = mock(PushService.class);
		RegisterPatientController controller = new RegisterPatientController(mock(RegisterPatientAction.class), pushService);
		Request request = requestWithPatientData();
		Response response = mock(Response.class);

		assertThat(controller.handle(request, response), is("OK"));

		verify(response).status(200);
		verify(pushService).notify("ShowPatient", "{\"id\":\"111\",\"birth\":709171200,\"gender\":\"Male\"}");
	}

	private Request requestWithPatientData() {
		Request request = mock(Request.class);
		when(request.queryParams("patientId")).thenReturn("111");
		when(request.queryParams("birth")).thenReturn("709171200");
		when(request.queryParams("gender")).thenReturn("male");
		return request;
	}
}
