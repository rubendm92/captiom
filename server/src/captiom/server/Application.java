package captiom.server;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.staticFileLocation;

public class Application {

	public static void main(String[] args) {
		staticFileLocation("site");
		get("/patient/:id", (request, response) -> "{\"id\":\"" + request.params("id") + "\", \"age\":23, \"gender\":\"Male\"}");
		post("/patient", (request, response) -> "OK");
	}
}
