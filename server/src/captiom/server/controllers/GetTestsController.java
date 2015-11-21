package captiom.server.controllers;

import captiom.core.use_cases.test.GetTestsAction;
import spark.Request;
import spark.Response;

public class GetTestsController implements Controller {

	private final GetTestsAction action;

	public GetTestsController(GetTestsAction action) {
		this.action = action;
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		return null;
	}
}
