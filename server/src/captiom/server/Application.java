package captiom.server;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.externalStaticFileLocation;

public class Application {

	public static void main(String[] args) {
		externalStaticFileLocation("website/");
		get("/patient/:id", (request, response) -> "{\"id\":\"" + request.params("id") + "\", \"age\":23, \"sex\":\"Male\"}");
		post("/patient", (request, response) -> "OK");
	}
}
