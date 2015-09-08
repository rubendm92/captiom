package captiom.core.model.device;

public class CalculatorNotConfiguredException extends RuntimeException {

	public CalculatorNotConfiguredException() {
		super("Calculator has not been setup");
	}
}
