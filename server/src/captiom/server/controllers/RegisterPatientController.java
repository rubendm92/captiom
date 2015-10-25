package captiom.server.controllers;

import captiom.core.model.patient.Gender;
import captiom.core.model.patient.Patient;
import captiom.core.use_cases.patient.RegisterPatientAction;
import captiom.server.infrastructure.PatientSerializer;
import captiom.server.infrastructure.PushService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.time.LocalDateTime;

import static java.time.ZoneOffset.UTC;

public class RegisterPatientController implements Route {

	private final RegisterPatientAction action;
	private final PushService pushService;

	public RegisterPatientController(RegisterPatientAction action, PushService pushService) {
		this.action = action;
		this.pushService = pushService;
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		Patient patient = buildPatient(request);
		action.register(patient);
		pushService.notify("ShowPatient", new PatientSerializer().serialize(patient).toString());
		response.status(200);
		return "OK";
	}

	private Patient buildPatient(Request request) {
		return new Patient(request.queryParams("patientId"), toDateTime(request.queryParams("birth")).toLocalDate(), Gender.valueOf(request.queryParams("gender").toUpperCase()));
	}

	private LocalDateTime toDateTime(String birth) {
		return LocalDateTime.ofEpochSecond(Long.valueOf(birth), 0, UTC);
	}
}
