package captiom.server.infrastructure.repositories;

import captiom.core.model.test.TestRepository;
import captiom.core.model.device.Eye;
import captiom.core.model.device.OptotypeCharacter;
import captiom.core.model.test.Record;
import captiom.core.model.test.Test;
import captiom.server.infrastructure.OptotypeCharacterMapper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class CsvTestRepository implements TestRepository {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final String EXTENSION = ".csv";
	private static final byte[] HEADER = "Date;Test;Eye;Detail;Character;Result\n".getBytes(Charset.forName("utf-8"));
	private final Path workingDirectory;
	private final List<Test> availableTests;

	public CsvTestRepository(String workingDirectory) {
		this.workingDirectory = Paths.get(workingDirectory);
		availableTests = asList(landolt(), snellen(), tumblingE());
	}

	@Override
	public List<Test> availableTests() {
		return availableTests;
	}

	@Override
	public Map<LocalDate, List<Record>> testResultsByDate(String patientId) {
		LinkedHashMap<LocalDate, List<Record>> history = new LinkedHashMap<>();
		readRecordsOf(patientId)
				.map(l -> l.split(";"))
				.forEach(splitLine -> {
					LocalDate instant = instantFrom(splitLine[0]);
					history.putIfAbsent(instant, new ArrayList<>());
					history.get(instant).add(recordFrom(splitLine));
				});
		return history;
	}

	private Stream<String> readRecordsOf(String patientId) {
		try {
			return Files.lines(Paths.get(workingDirectory.toFile().getAbsolutePath(), patientId + ".csv")).skip(1);
		} catch (IOException e) {
			return Stream.empty();
		}
	}

	private LocalDate instantFrom(String instant) {
		return LocalDate.from(FORMATTER.parse(instant));
	}

	private Record recordFrom(String[] splitLine) {
		return new Record(OptotypeCharacterMapper.fromString(splitLine[4]), splitLine[1], Long.valueOf(splitLine[3]), Eye.valueOf(splitLine[2]), splitLine[5].equalsIgnoreCase("Right"));
	}

	@Override
	public void save(String patientId, List<Record> testResults) {
		try {
			Files.write(findPatientHistoryFile(patientId), toCsv(testResults), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Path findPatientHistoryFile(String patientId) {
		return filesInWorkingDirectory()
				.filter(p -> p.equals(Paths.get(workingDirectory.toString(), patientId + EXTENSION)))
				.findFirst()
				.orElseGet(() -> newFileWithHeaders(patientId));
	}

	private Stream<Path> filesInWorkingDirectory() {
		try {
			return Files.list(workingDirectory);
		} catch (IOException e) {
			return Stream.empty();
		}
	}

	private Path newFileWithHeaders(String patientId) {
		try {
			Path file = Files.createFile(Paths.get(workingDirectory.toString(), patientId + EXTENSION));
			Files.write(file, HEADER);
			return file;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private List<String> toCsv(List<Record> testResults) {
		return testResults.stream().map(this::toCsv).collect(Collectors.toList());
	}

	private String toCsv(Record record) {
		return now() + ";" + record.testName + ";" + record.eye + ";" + record.detail + ";" + record.character + ";" + (record.success ? "Right" : "Wrong");
	}

	private String now() {
		return FORMATTER.format(LocalDate.now());
	}

	private Test landolt() {
		return new Test("Landolt", asList(OptotypeCharacter.C.values()));
	}

	private Test snellen() {
		return new Test("Snellen", asList(OptotypeCharacter.Snellen.values()));
	}

	private Test tumblingE() {
		return new Test("Tumbling E", asList(OptotypeCharacter.TumblingE.values()));
	}

}
