package captiom.server.controllers;

import captiom.core.model.patient.Patient;
import captiom.core.use_cases.patient.SearchPatientAction;
import captiom.server.infrastructure.PatientSerializer;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Optional;

public class SearchPatientController implements Route {

	private final PatientSerializer serializer = new PatientSerializer();
	private final SearchPatientAction action;

	public SearchPatientController(SearchPatientAction action) {
		this.action = action;
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		Optional<Patient> patient = searchPatient(request.params("id"));
		if (patient.isPresent())
			return responseWithPatient(patient.get(), response);
		return patientNotFound(response);
	}

	private Optional<Patient> searchPatient(String id) {
		return action.searchPatient(id);
	}

	private Object responseWithPatient(Patient patient, Response response) {
		response.status(200);
		return serializer.serialize(patient).toString();
	}

	private Object patientNotFound(Response response) {
		response.status(204);
		return "Not found";
	}
}
