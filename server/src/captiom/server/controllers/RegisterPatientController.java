package captiom.server.controllers;

import captiom.core.model.patient.Gender;
import captiom.core.model.patient.Patient;
import captiom.core.use_cases.patient.RegisterPatientAction;
import captiom.server.infrastructure.PushService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import spark.Request;
import spark.Response;

public class RegisterPatientController implements Controller {

	private final RegisterPatientAction action;
	private final PushService pushService;

	public RegisterPatientController(RegisterPatientAction action, PushService pushService) {
		this.action = action;
		this.pushService = pushService;
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		Patient patient = buildPatient(GSON.fromJson(request.body(), JsonElement.class).getAsJsonObject());
		action.register(patient);
		pushService.notify("ShowSetupDevice");
		response.status(200);
		return "OK";
	}

	private Patient buildPatient(JsonObject object) {
		return new Patient(object.get("patientId").getAsString(), object.get("age").getAsInt(), Gender.valueOf(object.get("gender").getAsString().toUpperCase()));
	}
}
