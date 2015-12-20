package captiom.server.controllers;

import captiom.core.model.patient.Gender;
import captiom.core.model.patient.Patient;
import captiom.server.infrastructure.DisplayService;
import captiom.server.displays.PatientFormDisplay;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import spark.Request;
import spark.Response;

import java.time.LocalDate;

public class RegisterPatientController implements Controller {

	private final DisplayService displayService;

	public RegisterPatientController(DisplayService displayService) {
		this.displayService = displayService;
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		Patient patient = buildPatient(GSON.fromJson(request.body(), JsonElement.class).getAsJsonObject());
		displayService.display(PatientFormDisplay.class).patient(patient);
		response.status(200);
		return "OK";
	}

	private Patient buildPatient(JsonObject object) {
		return new Patient(getString(object, "patientId"), getString(object, "name"),
				getLocalDate(object, "birthDate"), Gender.valueOf(getString(object, "gender").toUpperCase()));
	}

	private String getString(JsonObject object, String name) {
		return object.get(name).getAsString();
	}

	private LocalDate getLocalDate(JsonObject object, String name) {
		return LocalDate.ofEpochDay(toDays(object.get(name).getAsLong()));
	}

	private long toDays(long milliseconds) {
		return milliseconds / (1000 * 60 * 60 * 24);
	}
}
