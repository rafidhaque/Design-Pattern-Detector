package parser;

import java.util.ArrayList;

/**
 * Class representing a variable inside a ClassObject.
 *
 */
public class Variable {

	// //////////////////// VARIABLES ///////////////////////////////

	private String name;
	private String type;
	private String initializer;
	private ArrayList<String> modifiers = new ArrayList<String>();

	// /////////////////// METHODS //////////////////////////////

	// GETS

	/**
	 * Returns the name of this Variable.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns a String representing the type of this Variable.
	 */
	public String gettype() {
		return type;
	}

	/**
	 * Returns a String representing the initializer of this Variable.
	 */
	public String getinitializer() {
		return initializer;
	}

	/**
	 * Returns an arraylist of Strings representing the modifiers of this Variable.
	 */
	public ArrayList<String> getmodifiers() {
		return modifiers;
	}

	// SETS

	/**
	 * Sets the name of this Variable.
	 */
	public void setName(String s) {
		name = s;
	}

	/**
	 * Sets the String variable representing the type of this Variable.
	 */
	public void settype(String s) {
		type = s;
	}

	/**
	 * Sets the String variable representing the initializer of this Variable.
	 * 
	 */
	public void setinitializer(String s) {
		initializer = s;
	}

	/**
	 * Adds a String into the modifiers arraylist, input
	 * String should be representing a modifier of this Variable
	 */
	public void addmodifier(String s) {
		modifiers.add(s);
	}

	// PRINT

	/**
	 * Prints all the details of this Variable Object:
	 * {name}{Modifiers}{Type}{Initializer}
	 */
	public void printvariable() {
		System.out.println("	Variable name: " + name);
		System.out.println("		Modifiers  : ");
		for (String s : modifiers) {
			System.out.println("			" + s);
		}
		System.out.println("		Type:\r\n			" + type);
		System.out.println("		Initializer:\r\n			" + initializer);

	}

}
