package parser;

/**
 * Contains all around methods used in various classes.
 */
public class GeneralMethods {

	/**
	 * Static function that checks if a string represents a primitive data type.
	 */
	public static boolean isPrimitive(String s) {
		if (s.equalsIgnoreCase("String") || s.equalsIgnoreCase("Integer") || s.equalsIgnoreCase("Int")
				|| s.equalsIgnoreCase("Void") || s.equalsIgnoreCase("Boolean") || s.equalsIgnoreCase("Character")
				|| s.equalsIgnoreCase("Char") || s.equalsIgnoreCase("Byte") || s.equalsIgnoreCase("Short")
				|| s.equalsIgnoreCase("Long") || s.equalsIgnoreCase("Float") || s.equalsIgnoreCase("Double")
				|| s.equalsIgnoreCase("NullPointerException") || s.equalsIgnoreCase("") || s.contains("[]")
				|| s.contains(">"))
			return true;
		else
			return false;
	}

}
