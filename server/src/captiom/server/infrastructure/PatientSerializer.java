package captiom.server.infrastructure;

import captiom.core.model.patient.Patient;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import static java.time.ZoneOffset.UTC;

public class PatientSerializer {

	public JsonElement serialize(Patient patient) {
		JsonObject object = new JsonObject();
		object.addProperty("id", patient.id);
		object.addProperty("birth", patient.dateOfBirth.atStartOfDay().toEpochSecond(UTC));
		object.addProperty("gender", patient.gender.toString());
		return object;
	}
}
