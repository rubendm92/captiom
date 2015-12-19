package captiom.server.controllers;

import captiom.server.displays.TestDisplay;
import captiom.server.infrastructure.DisplayService;
import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Response;

import static org.mockito.Mockito.*;

public class AcceptedTestController {

	private TestController controller;
	private TestDisplay testDisplay;

	@Before
	public void setUp() {
		DisplayService displayService = mock(DisplayService.class);
		testDisplay = mock(TestDisplay.class);
		when(displayService.display(TestDisplay.class)).thenReturn(testDisplay);
		controller = new TestController(displayService);
	}

	@Test
	public void should_show_char_on_test_display() throws Exception {
		controller.handle(requestToShowChar(), mock(Response.class));

		verify(testDisplay).showChar("+", 200, "MAR", "LEFT");
	}

	@Test
	public void should_add_record_to_patient_test() throws Exception {
		controller.handle(requestToAddRecord(), mock(Response.class));

		verify(testDisplay).addRecord("+", 200, "MAR", "LEFT", true);
	}

	@Test
	public void should_clear_device() throws Exception {
		controller.handle(requestToClearDevice(), mock(Response.class));

		verify(testDisplay).clearDevice();
	}

	@Test
	public void should_finish_test() throws Exception {
		controller.handle(requestToFinishTest(), mock(Response.class));

		verify(testDisplay).finishTest();
	}

	private Request requestToShowChar() {
		Request request = mock(Request.class);
		when(request.body()).thenReturn("{\"operation\":\"showChar\",\"detail\":{\"value\":200,\"measure\":\"MAR\"},\"character\":\"+\",\"eye\":\"LEFT\"}");
		return request;
	}

	private Request requestToAddRecord() {
		Request request = mock(Request.class);
		when(request.body()).thenReturn("{\"operation\":\"addRecord\",\"detail\":{\"value\":200,\"measure\":\"MAR\"},\"character\":\"+\",\"eye\":\"LEFT\",\"success\":\"true\"}");
		return request;
	}

	private Request requestToClearDevice() {
		Request request = mock(Request.class);
		when(request.body()).thenReturn("{\"operation\":\"clear\"}");
		return request;
	}

	private Request requestToFinishTest() {
		Request request = mock(Request.class);
		when(request.body()).thenReturn("{\"operation\":\"finish\"}");
		return request;
	}
}
