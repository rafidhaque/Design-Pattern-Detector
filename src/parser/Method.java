package parser;

import java.util.ArrayList;

/**
 * Represents a method inside a ClassObject.
 *
 */
public class Method {

	// //////////////////// VARIABLES ///////////////////////////////

	private String name;
	private String returntype;
	private Boolean isAbstract;
	private ArrayList<String> Inputtypes = new ArrayList<String>();
	private ArrayList<String> Modifiers = new ArrayList<String>();

	// /////////////////// METHODS //////////////////////////////

	// GETS

	/**
	 * Returns a String representing the name of this method.
	 * 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns a Boolean indicating if this method is abstract or not.
	 * 
	 */
	public Boolean getisAbstract() {
		return isAbstract;
	}

	/**
	 * Returns a String representing the return type if any of this method.
	 * 
	 */
	public String getReturntype() {
		return returntype;
	}

	/**
	 * Returns an arraylist of Strings that consists of the input types of this method.
	 */
	public ArrayList<String> getInputtypes() {
		return Inputtypes;
	}

	/**
	 * Returns an arraylist of Strings that consists of the modifiers of this method.
	 * 
	 */
	public ArrayList<String> getModifiers() {
		return Modifiers;
	}

	// SETS

	/**
	 * Sets the name of this method.
	 */
	public void setName(String s) {
		name = s;
	}

	/**
	 * Sets string variable representing
	 * the return type of this method.
	 */
	public void setReturntype(String s) {
		returntype = s;
	}

	/**
	 * Sets the Boolean representing the abstraction of this method.
	 */
	public void setisAbstract(Boolean b) {
		isAbstract = b;
	}

	/**
	 * Adds a String into the Inputtypes arraylist, input
	 * String should be representing an input type of this method.
	 */
	public void addInputtype(String s) {
		Inputtypes.add(s);
	}

	/**
	 * Adds a String into the Modifiers arraylist, input
	 * String should be representing a modifier of this method.
	 */
	public void addModifier(String s) {
		Modifiers.add(s);
	}

	// PRINT

	/**
	 * Prints all the details of this Method:
	 * {name}{abstraction}{Modifiers}{Return type}{Input Types}
	 */
	public void printmethod() {
		System.out.println("	Method name: " + name);
		System.out.println("		Modifiers  : ");
		for (String s : Modifiers) {
			System.out.println("			" + s);
		}
		System.out.println("		Return type:\r\n			" + returntype);
		System.out.println("		Input types: ");
		for (String s : Inputtypes) {
			System.out.println("			" + s);
		}
	}

}
