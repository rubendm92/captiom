package captiom.server.displays;

import captiom.core.model.patient.Patient;
import captiom.core.use_cases.patient.RegisterPatientAction;
import captiom.server.infrastructure.Services;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class PatientFormDisplay implements Display {

	private static final int PATIENT_ID_LENGTH = 8;
	private final Services services;
	private final RegisterPatientAction action;
	private final List<PatientRegistered> listeners = new ArrayList<>();

	public PatientFormDisplay(Services services) {
		this.services = services;
		this.action = new RegisterPatientAction(services.patientService());
	}

	@Override
	public void show() {
		services.pushService().notify("PatientForm", serializeConfiguration());
	}

	public void patient(Patient patient) {
		action.register(patient);
		listeners.stream().forEach(PatientRegistered::registered);
	}

	public void onPatientRegistered(PatientRegistered patientRegistered) {
		listeners.add(patientRegistered);
	}

	private String serializeConfiguration() {
		JsonObject object = new JsonObject();
		object.addProperty("patientIdLength", PATIENT_ID_LENGTH);
		return object.toString();
	}

	@FunctionalInterface
	public interface PatientRegistered {
		void registered();
	}
}
