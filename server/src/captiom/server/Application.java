package captiom.server;

import captiom.core.actions.device.GetDevicesAction;
import captiom.core.actions.device.RegisterDeviceAction;
import captiom.server.controllers.*;
import captiom.server.displays.ApplicationDisplay;
import captiom.server.infrastructure.PushService;
import captiom.server.infrastructure.Services;

import java.net.UnknownHostException;

import static java.net.InetAddress.getLocalHost;
import static spark.Spark.*;

public class Application {

	private static final int PUSH_PORT = 8081;
	private final Services services;
	private final String pushUrl;

	public Application(Services services, String pushUrl) {
		this.services = services;
		this.pushUrl = pushUrl;
	}

	public void launch() {
		services.pushService().addConnectionOpenedListener(() -> services.displayService().register(new ApplicationDisplay(services)));
		registerRoutes();
	}

	public static void main(String[] args) throws UnknownHostException {
		new Application(new ApplicationServices(new PushService(PUSH_PORT), args[0]), pushUrl()).launch();
	}

	private void registerRoutes() {
		staticFileLocation("site");
		post("/patient", new RegisterPatientController(services.displayService()));
		get("/devices", new GetDevicesController(new GetDevicesAction(services.deviceService())));
		put("/devices", new RegisterDeviceController(new RegisterDeviceAction(services.deviceService())));
		post("/device", new ConfigureDeviceController(services.displayService()));
		post("/test", new TestController(services.displayService()));
		get("/", new HomeController(pushUrl));
	}

	private static String pushUrl() throws UnknownHostException {
		return "ws://" + getLocalHost().getHostAddress() +  ":" + PUSH_PORT;
	}
}
