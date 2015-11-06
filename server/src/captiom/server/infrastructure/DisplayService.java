package captiom.server.infrastructure;

import captiom.server.displays.Display;

import java.util.HashMap;
import java.util.Map;

public class DisplayService {

	private final Map<Class, Display> record = new HashMap<>();

	public void register(Display display) {
		record.put(display.getClass(), display);
	}

	@SuppressWarnings("unchecked")
	public <T extends Display> T display(Class<T> type) {
		return (T) record.get(type);
	}
}
