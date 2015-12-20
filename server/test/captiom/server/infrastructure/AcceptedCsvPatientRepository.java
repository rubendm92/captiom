package captiom.server.infrastructure;

import captiom.core.model.patient.Gender;
import captiom.core.model.patient.Patient;
import org.junit.*;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class AcceptedCsvPatientRepository {

	private static final String BASE_DIR = "test-res/csv-repositories";
	public static final Path PATIENTS_REPO = Paths.get(BASE_DIR, "patient-repository");

	@BeforeClass
	public static void setUpClass() throws IOException {
		Files.createDirectories(Paths.get(BASE_DIR));
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		Files.delete(Paths.get(BASE_DIR));
	}

	@Before
	public void setUp() throws Exception {
		Files.createDirectory(PATIENTS_REPO);
	}

	@After
	public void tearDown() throws Exception {
		Files.walkFileTree(PATIENTS_REPO, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				Files.delete(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

	@Test
	public void should_create_patients_file_if_it_does_not_exist_and_write_patient() throws IOException {
		CsvPatientRepository repository = new CsvPatientRepository(PATIENTS_REPO.toString());
		Patient patient = new Patient("1", "Ruben", LocalDate.of(1992, 6, 22), Gender.MALE);

		repository.save(patient);

		checkThatPatientsFileExistsAndContainsPatient(patient);
	}

	private void checkThatPatientsFileExistsAndContainsPatient(Patient patient) throws IOException {
		Path patientsFile = Paths.get(PATIENTS_REPO.toString(), "patients.csv");
		assertTrue(Files.exists(patientsFile));
		List<String> lines = Files.readAllLines(patientsFile);

		assertThat(lines.get(1), is(patient.id + ";" + patient.name + ";" + DateTimeFormatter.ofPattern("dd/MM/yyyy").format(patient.birthDate) + ";" +  patient.gender));
	}
}
