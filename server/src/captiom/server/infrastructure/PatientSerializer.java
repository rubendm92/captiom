package captiom.server.infrastructure;

import captiom.core.model.patient.Patient;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.time.ZoneId;

public class PatientSerializer {

	public static final ZoneId ZONE = ZoneId.systemDefault();

	public JsonElement serialize(Patient patient) {
		JsonObject object = new JsonObject();
		object.addProperty("id", patient.id);
		object.addProperty("birth", patient.dateOfBirth.atStartOfDay(ZONE).toEpochSecond());
		object.addProperty("gender", patient.gender.toString());
		return object;
	}
}
