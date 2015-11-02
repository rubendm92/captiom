package captiom.server.infrastructure;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class PushService extends WebSocketServer {

	private final List<ConnectionOpened> connectionOpenedListeners = new ArrayList<>();
	private final JsonParser parser = new JsonParser();

	public PushService(int port) {
		super(new InetSocketAddress(port));
		start();
	}

	@Override
	public void onOpen(WebSocket socket, ClientHandshake handshake) {
		connectionOpenedListeners.forEach(ConnectionOpened::onOpen);
	}

	@Override
	public void onClose(WebSocket socket, int code, String reason, boolean remote) {
	}

	@Override
	public void onMessage(WebSocket socket, String message) {
		notify(message);
	}

	@Override
	public void onError(WebSocket socket, Exception e) {
	}

	public void addConnectionOpenedListener(ConnectionOpened connectionOpened) {
		connectionOpenedListeners.add(connectionOpened);
	}

	public void notify(String text) {
		connections().stream().forEach(connection -> connection.send(serialize(text, "")));
	}

	public void notify(String title, String content) {
		connections().stream().forEach(connection -> connection.send(serialize(title, content)));
	}

	private String serialize(String title, String content) {
		JsonObject object = new JsonObject();
		object.addProperty("name", title);
		object.add("content", parser.parse(content));
		return object.toString();
	}

	public interface ConnectionOpened {
		void onOpen();
	}
}