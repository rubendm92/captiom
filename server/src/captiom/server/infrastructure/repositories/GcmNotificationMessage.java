package captiom.server.infrastructure.repositories;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;

class GcmNotificationMessage {

	private static final String API_KEY = "AIzaSyDuonfvRHUVSXCS3lKXN_oHuOtOBL3nkRo";
	private static final String GCM_URL = "https://android.googleapis.com/gcm/send";
	private final JsonObject message = new JsonObject();

	private GcmNotificationMessage() {
	}

	public static PendingMessage send(Consumer<GcmNotificationMessage> consumer) {
		GcmNotificationMessage message = new GcmNotificationMessage();
		consumer.accept(message);
		return message::send;
	}

	public GcmNotificationMessage addData(String key, String value) {
		message.addProperty(key, value);
		return this;
	}

	private void send(String device) {
		try {
			sendMessage(buildMessage(device), createConnection());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendMessage(JsonObject data, HttpURLConnection connection) throws IOException {
		connection.getOutputStream().write(data.toString().getBytes());
		connection.getInputStream().close();
	}

	private HttpURLConnection createConnection() throws IOException {
		HttpURLConnection conn = (HttpURLConnection) new URL(GCM_URL).openConnection();
		conn.setRequestProperty("Authorization", "key=" + API_KEY);
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		return conn;
	}

	private JsonObject buildMessage(String device) {
		JsonObject data = new JsonObject();
		data.addProperty("to", device);
		data.add("data", message);
		return data;
	}

	@FunctionalInterface
	public interface PendingMessage {
		void to(String device);
	}
}
