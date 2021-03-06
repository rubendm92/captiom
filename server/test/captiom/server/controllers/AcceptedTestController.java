package captiom.server.controllers;

import captiom.core.model.test.Record;
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

		verify(testDisplay).showChar("+", 200, "LEFT");
	}

	@Test
	public void should_add_record_to_patient_test() throws Exception {
		controller.handle(requestToAddRecord(), mock(Response.class));

		verify(testDisplay).addRecord(any(Record.class));
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
		when(request.body()).thenReturn("{\"operation\":\"showChar\",\"detail\":200,\"character\":\"+\",\"eye\":\"LEFT\"}");
		return request;
	}

	private Request requestToAddRecord() {
		Request request = mock(Request.class);
		when(request.body()).thenReturn("{\"operation\":\"addRecord\",\"detail\":200,\"testName\":\"anyTest\",\"character\":\"+\",\"eye\":\"LEFT\",\"success\":\"true\"}");
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
