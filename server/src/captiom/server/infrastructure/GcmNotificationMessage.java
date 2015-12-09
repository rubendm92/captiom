package captiom.server.infrastructure;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;

import java.io.IOException;
import java.util.function.Consumer;

class GcmNotificationMessage {

	private static final String GOOGLE_SERVER_KEY = "AIzaSyADH6vui8GuX_YHFPx8HPUWVJ0mH9lmSpw";
	private final Sender sender = new Sender(GOOGLE_SERVER_KEY);
	private final Message.Builder messageBuilder = new Message.Builder().timeToLive(30).delayWhileIdle(true);

	private GcmNotificationMessage() {
	}

	public static PendingMessage send(Consumer<GcmNotificationMessage> consumer) {
		GcmNotificationMessage message = new GcmNotificationMessage();
		consumer.accept(message);
		return message::send;
	}

	public GcmNotificationMessage addData(String key, String value) {
		messageBuilder.addData(key, value);
		return this;
	}

	private void send(String device) {
		try {
			sender.send(messageBuilder.build(), device, 5);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FunctionalInterface
	public interface PendingMessage {
		void to(String device);
	}
}
