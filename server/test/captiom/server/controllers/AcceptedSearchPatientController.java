package captiom.server.controllers;

import captiom.core.model.patient.Patient;
import captiom.core.use_cases.patient.SearchPatientAction;
import org.junit.Test;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static captiom.core.model.patient.Gender.MALE;
import static java.time.LocalDate.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class AcceptedSearchPatientController {

	@Test
	public void should_return_serialized_patient_when_id_is_from_registered_patient() throws Exception {
		String patientId = "1";
		SearchPatientAction action = mock(SearchPatientAction.class);
		when(action.searchPatient(patientId)).thenReturn(Optional.of(patient(patientId)));
		SearchPatientController controller = new SearchPatientController(action);
		Response response = mock(Response.class);

		Object serializedPatient = controller.handle(request(patientId), response);

		assertThat(serializedPatient, is("{\"id\":\"1\",\"birth\":709167600,\"gender\":\"Male\"}"));
		verify(response).status(200);
	}

	@Test
	public void should_response_with_code_204_when_patient_is_not_registered() throws Exception {
		String patientId = "1";
		SearchPatientAction action = mock(SearchPatientAction.class);
		when(action.searchPatient(patientId)).thenReturn(Optional.empty());
		SearchPatientController controller = new SearchPatientController(action);
		Response response = mock(Response.class);

		Object serializedPatient = controller.handle(request(patientId), response);

		assertThat(serializedPatient, is("Not found"));
		verify(response).status(204);
	}

	private Request request(String patientId) {
		Request request = mock(Request.class);
		when(request.params("id")).thenReturn(patientId);
		return request;
	}

	private Patient patient(String patientId) {
		return new Patient(patientId, of(1992, 6, 22), MALE);
	}
}
