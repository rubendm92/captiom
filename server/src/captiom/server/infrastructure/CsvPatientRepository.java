package captiom.server.infrastructure;

import captiom.core.infrastructure.patient.PatientRepository;
import captiom.core.model.patient.Gender;
import captiom.core.model.patient.Patient;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

public class CsvPatientRepository implements PatientRepository {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final byte[] HEADER = "ID;Name;Birth date;Gender\n".getBytes(Charset.forName("utf-8"));
	private final Path patientsFile;

	public CsvPatientRepository(String workingDirectory) {
		this.patientsFile = Paths.get(workingDirectory, "patients.csv");
		createFileIfItDoesNotExist();
	}

	private void createFileIfItDoesNotExist() {
		if (Files.exists(patientsFile)) return;
		try {
			Files.write(patientsFile, HEADER);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void save(Patient patient) {
		if (allPatients().noneMatch(patient::equals)) {
			register(patient);
		}
	}

	private Stream<Patient> allPatients() {
		return allCsvPatients().map(this::toPatient);
	}

	private Stream<String> allCsvPatients() {
		try {
			List<String> lines = Files.readAllLines(patientsFile);
			return lines.subList(1, lines.size()).stream();
		} catch (IOException e) {
			return Stream.empty();
		}
	}

	private Patient toPatient(String csv) {
		String[] data = csv.split(";");
		String id = data[0];
		String name = data[1];
		LocalDate birthDate = LocalDate.from(FORMATTER.parse(data[2]));
		Gender gender = Gender.valueOf(data[3].toUpperCase());
		return new Patient(id, name, birthDate, gender);
	}

	private void register(Patient patient) {
		try {
			Files.write(patientsFile, toCsv(patient).getBytes(Charset.forName("utf-8")), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String toCsv(Patient patient) {
		return patient.id + ";" + patient.name + ";" + FORMATTER.format(patient.birthDate) + ";" + patient.gender;
	}
}
