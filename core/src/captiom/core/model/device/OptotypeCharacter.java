package captiom.core.model.device;

public interface OptotypeCharacter {

	enum TumblingE implements OptotypeCharacter {
		OPENS_RIGHT, OPENS_UP, OPENS_LEFT, OPENS_DOWN
	}

	enum C implements OptotypeCharacter {
		OPENS_RIGHT, OPENS_RIGHT_UP, OPENS_UP, OPENS_LEFT_UP, OPENS_LEFT, OPENS_LEFT_DOWN, OPENS_DOWN, OPENS_RIGHT_DOWN
	}

	enum Snellen implements OptotypeCharacter {
		C, D, E, F, L, N, O, P, T, Z
	}

}
