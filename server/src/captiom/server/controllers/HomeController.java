package captiom.server.controllers;

import spark.Request;
import spark.Response;

import java.nio.file.Files;
import java.nio.file.Paths;

public class HomeController implements Controller {

	private final String pushUrl;

	public HomeController(String pushUrl) {
		this.pushUrl = pushUrl;
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		return new String(Files.readAllBytes(Paths.get("res/index-template.html")), "utf-8").replace("$pushUrl", pushUrl);
	}
}
