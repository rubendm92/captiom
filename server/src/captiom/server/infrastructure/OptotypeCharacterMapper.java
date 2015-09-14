package captiom.server.infrastructure;

import captiom.core.model.device.OptotypeCharacter;
import captiom.core.model.device.OptotypeCharacter.C;
import captiom.core.model.device.OptotypeCharacter.Letter;
import captiom.core.model.device.OptotypeCharacter.Snellen;

import java.util.HashMap;
import java.util.Map;

public class OptotypeCharacterMapper {

	private static final Map<String, OptotypeCharacter> stringToChar = new HashMap<>();
	private static final Map<OptotypeCharacter, String> charToString = new HashMap<>();

	static {
		fillStringToCharMap();
		fillCharToStringMap();
	}

	public static OptotypeCharacter fromString(String value) {
		return stringToChar.get(value);
	}

	public static String toString(OptotypeCharacter character) {
		return charToString.get(character);
	}

	private static void fillStringToCharMap() {
		stringToChar.put("+", Snellen.OPENS_RIGHT);
		stringToChar.put("/", Snellen.OPENS_UP);
		stringToChar.put("-", Snellen.OPENS_LEFT);
		stringToChar.put("*", Snellen.OPENS_DOWN);
		stringToChar.put("0", C.OPENS_RIGHT);
		stringToChar.put("1", C.OPENS_RIGHT_UP);
		stringToChar.put("2", C.OPENS_UP);
		stringToChar.put("3", C.OPENS_LEFT_UP);
		stringToChar.put("4", C.OPENS_LEFT);
		stringToChar.put("5", C.OPENS_LEFT_DOWN);
		stringToChar.put("6", C.OPENS_DOWN);
		stringToChar.put("7", C.OPENS_RIGHT_DOWN);
		stringToChar.put("C", Letter.C);
		stringToChar.put("D", Letter.D);
		stringToChar.put("E", Letter.E);
		stringToChar.put("F", Letter.F);
		stringToChar.put("L", Letter.L);
		stringToChar.put("N", Letter.N);
		stringToChar.put("O", Letter.O);
		stringToChar.put("P", Letter.P);
		stringToChar.put("T", Letter.T);
		stringToChar.put("Z", Letter.Z);
	}

	private static void fillCharToStringMap() {
		charToString.put(Snellen.OPENS_RIGHT, "+");
		charToString.put(Snellen.OPENS_UP, "/");
		charToString.put(Snellen.OPENS_LEFT, "-");
		charToString.put(Snellen.OPENS_DOWN, "*");
		charToString.put(C.OPENS_RIGHT, "0");
		charToString.put(C.OPENS_RIGHT_UP, "1");
		charToString.put(C.OPENS_UP, "2");
		charToString.put(C.OPENS_LEFT_UP, "3");
		charToString.put(C.OPENS_LEFT, "4");
		charToString.put(C.OPENS_LEFT_DOWN, "5");
		charToString.put(C.OPENS_DOWN, "6");
		charToString.put(C.OPENS_RIGHT_DOWN, "7");
		charToString.put(Letter.C, "C");
		charToString.put(Letter.D, "D");
		charToString.put(Letter.E, "E");
		charToString.put(Letter.F, "F");
		charToString.put(Letter.L, "L");
		charToString.put(Letter.N, "N");
		charToString.put(Letter.O, "O");
		charToString.put(Letter.P, "P");
		charToString.put(Letter.T, "T");
		charToString.put(Letter.Z, "Z");
	}
}
