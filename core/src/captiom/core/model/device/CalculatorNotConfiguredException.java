package captiom.core.model.device;

class CalculatorNotConfiguredException extends RuntimeException {

	public CalculatorNotConfiguredException() {
		super("Calculator has not been setup");
	}
}
