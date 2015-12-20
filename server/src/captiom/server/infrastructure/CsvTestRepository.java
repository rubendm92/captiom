package captiom.server.infrastructure;

import captiom.core.infrastructure.test.TestRepository;
import captiom.core.model.device.OptotypeCharacter;
import captiom.core.model.test.Record;
import captiom.core.model.test.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class CsvTestRepository implements TestRepository {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
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
	public Map<LocalDateTime, List<Record>> testResultsByDate(String patientId) {
		return null;
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
		String now = FORMATTER.format(LocalDateTime.now());
		String test = "//TODO"; //TODO
		return now + ";" + test + ";" + record.eye + ";" + record.detail + ";" + record.character + ";" + (record.success ? "Right" : "Wrong");
	}

	private Test landolt() {
		return new Test("Landolt", toList(OptotypeCharacter.C.values()));
	}

	private Test snellen() {
		return new Test("Snellen", toList(OptotypeCharacter.Snellen.values()));
	}

	private Test tumblingE() {
		return new Test("Tumbling E", toList(OptotypeCharacter.TumblingE.values()));
	}

	private List<OptotypeCharacter> toList(OptotypeCharacter[] characteres) {
		return asList(characteres);
	}
}
