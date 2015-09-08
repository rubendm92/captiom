package captiom.core.model.device;

public interface OptotypeCharacter {

	enum Snellen implements OptotypeCharacter {
		OPENS_RIGHT("+"),
		OPENS_UP("/"),
		OPENS_LEFT("-"),
		OPENS_DOWN("*");

		private final String letter;

		Snellen(final String letter) {
			this.letter = letter;
		}

		@Override
		public String letter() {
			return letter;
		}
	}

	enum C implements OptotypeCharacter {
		OPENS_RIGHT("0"), OPENS_RIGHT_UP("1"), OPENS_UP("2"),
		OPENS_LEFT_UP("3"), OPENS_LEFT("4"), OPENS_LEFT_DOWN("5"),
		OPENS_DOWN("6"), OPENS_RIGHT_DOWN("7");

		private final String letter;

		C(final String letter) {
			this.letter = letter;
		}

		@Override
		public String letter() {
			return letter;
		}
	}

	enum Letter implements OptotypeCharacter {
		C("C"), D("D"), E("E"),
		F("F"), L("L"), N("N"),
		O("O"), P("P"), T("T"), Z("Z");

		private final String letter;

		Letter(final String letter) {
			this.letter = letter;
		}

		@Override
		public String letter() {
			return letter;
		}
	}

	String letter();
}
