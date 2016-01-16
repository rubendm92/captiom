package captiom.server.infrastructure;

import captiom.core.model.device.OptotypeCharacter;
import captiom.core.model.device.OptotypeCharacter.C;
import captiom.core.model.device.OptotypeCharacter.Snellen;
import captiom.core.model.device.OptotypeCharacter.TumblingE;

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
		return stringToChar.getOrDefault(value, TumblingE.OPENS_RIGHT);
	}

	public static String toString(OptotypeCharacter character) {
		return charToString.getOrDefault(character, "+");
	}

	private static void fillStringToCharMap() {
		stringToChar.put("+", TumblingE.OPENS_RIGHT);
		stringToChar.put("/", TumblingE.OPENS_UP);
		stringToChar.put("-", TumblingE.OPENS_LEFT);
		stringToChar.put("*", TumblingE.OPENS_DOWN);
		stringToChar.put("0", C.OPENS_RIGHT);
		stringToChar.put("1", C.OPENS_RIGHT_UP);
		stringToChar.put("2", C.OPENS_UP);
		stringToChar.put("3", C.OPENS_LEFT_UP);
		stringToChar.put("4", C.OPENS_LEFT);
		stringToChar.put("5", C.OPENS_LEFT_DOWN);
		stringToChar.put("6", C.OPENS_DOWN);
		stringToChar.put("7", C.OPENS_RIGHT_DOWN);
		stringToChar.put("C", Snellen.C);
		stringToChar.put("D", Snellen.D);
		stringToChar.put("E", Snellen.E);
		stringToChar.put("F", Snellen.F);
		stringToChar.put("L", Snellen.L);
		stringToChar.put("N", Snellen.N);
		stringToChar.put("O", Snellen.O);
		stringToChar.put("P", Snellen.P);
		stringToChar.put("T", Snellen.T);
		stringToChar.put("Z", Snellen.Z);
	}

	private static void fillCharToStringMap() {
		stringToChar.entrySet().stream().forEach(e -> charToString.put(e.getValue(), e.getKey()));
	}
}
