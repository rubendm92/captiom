package captiom.server.controllers;

import captiom.core.use_cases.test.GetTestsAction;
import org.junit.Test;
import spark.Request;
import spark.Response;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class AcceptedGetTestsController {
	
	@Test
	public void should_return_available_test_names() throws Exception {
	    GetTestsController controller = new GetTestsController(mock(GetTestsAction.class));
		String result = controller.handle(mock(Request.class), mock(Response.class)).toString();

		assertThat(result, is("[\"TumblingE\",\"C\"]"));

	}
}
