package captiom.core.model.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableList;

public class History {

	private final Map<LocalDate, List<Record>> history = new HashMap<>();

	public Stream<Entry> entries() {
		return history.entrySet().stream().map(Entry::new).sorted();
	}

	public void addRecord(LocalDate date, Record record) {
		history.putIfAbsent(date, new ArrayList<>());
		history.get(date).add(record);
	}

	public static class Entry implements Comparable<Entry> {
		public final LocalDate date;
		public final List<Record> records;

		Entry(Map.Entry<LocalDate, List<Record>> entry) {
			this.date = entry.getKey();
			this.records = unmodifiableList(entry.getValue());
		}

		@Override
		public int compareTo(Entry o) {
			return date.compareTo(o.date);
		}
	}
}
