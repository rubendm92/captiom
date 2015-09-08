package captiom.core.use_cases;

import captiom.core.model.Patient;
import captiom.core.model.PatientService;
import org.junit.Test;

import static captiom.core.model.Gender.MALE;
import static java.time.LocalDate.of;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AcceptedRegisterPatientCommand {

	@Test
	public void should_save_patient() {
		PatientService service = mock(PatientService.class);
		RegisterPatientCommand command = new RegisterPatientCommand(service);
		command.register(new Patient("1111", of(1992, 6, 22), MALE));
		verify(service).save(new Patient("1111", of(1992, 6, 22), MALE));
	}
}
