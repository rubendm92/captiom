package captiom.server.displays;

import captiom.server.infrastructure.PushService;
import com.google.gson.JsonObject;

public class PatientFormDisplay {

	private static final int PATIENT_ID_LENGTH = 8;
	private final PushService service;

	public PatientFormDisplay(PushService service) {
		this.service = service;
	}

	public void init() {
		service.notify("PatientFormConfiguration", serializeConfiguration());
	}

	private String serializeConfiguration() {
		JsonObject object = new JsonObject();
		object.addProperty("patientIdLength", PATIENT_ID_LENGTH);
		return object.toString();
	}
}
