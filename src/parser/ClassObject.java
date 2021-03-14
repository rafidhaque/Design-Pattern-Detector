package parser;

import java.util.ArrayList;

import parser.Connection.Type;

/**
 * Represents a class. This is the main object of this project.
 * 
 */
public class ClassObject {

	// ////////////////////////// VARIABLES ////////////////////////////

	int i;
	private String name;
	private String ability;
	private String extending;

	public enum Abstraction {
		Normal, Interface, Abstract, Abstracted, Unknown, Any
	};

	private Abstraction abstraction;
	private ArrayList<String> Implementing = new ArrayList<String>();
	private ArrayList<String> MethodInvocations = new ArrayList<String>();
	private ArrayList<Method> Methods = new ArrayList<Method>();
	private ArrayList<Variable> Variables = new ArrayList<Variable>();
	private ArrayList<String> Modifiers = new ArrayList<String>();
	private ArrayList<String> New_Instances = new ArrayList<String>();
	private Connections connections = new Connections();

	// ///////////////////////// METHODS ///////////////////////////

	// Constructor
	public ClassObject() {
	}

	// GETS

	public int getId() {
		return name.toCharArray()[0] - 65;
	}

	/**
	 * Returns this ClassObject's name as a string
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the ability of this ClassObject if any. Mostly for pattern purposes.
	 */
	public String getAbility() {
		return ability;
	}

	/**
	 * Returns the class's name this ClassObject extends if any
	 */
	public String getExtends() {
		return extending;
	}

	/**
	 * Returns the abstraction type of this ClassObject, abstraction
	 * returned is an enum type which can be any of the following:
	 * {Abstract}{Interface}{Abstracted}{Normal}{Any}
	 */
	public Abstraction get_abstraction() {
		return abstraction;
	}

	/**
	 * Returns an arraylist with strings of the names
	 * this ClassObject is implementing
	 */
	public ArrayList<String> getImplements() {
		return Implementing;
	}

	/**
	 * Returns an arraylist with strings of the method
	 * invocations this ClassObject has
	 */
	public ArrayList<String> getMethodInvocations() {
		return MethodInvocations;
	}

	/**
	 * Returns an arraylist with Method objects, those
	 * objects represent methods of this ClassObject
	 */
	public ArrayList<Method> getMethods() {
		return Methods;
	}

	/**
	 * Returns an arraylist with Variable objects, those
	 * objects represent variables of this ClassObject
	 */
	public ArrayList<Variable> getVariables() {
		return Variables;
	}

	/**
	 * Returns an arraylist with strings of the
	 * modifiers of this ClassObject
	 */
	public ArrayList<String> getModifiers() {
		return Modifiers;
	}

	/**
	 * Returns an arraylist with strings of the new(declaration)
	 * instances this ClassObject has
	 */
	public ArrayList<String> getNew_Instances() {
		return New_Instances;
	}

	/**
	 * Returns an arraylist with connection objects depending on their type. Those
	 * objects represent the use(or inh or ref etc depending on type) relations starting
	 * from this ClassObject.
	 */
	public ArrayList<Connection> getConnections(Type type) {
		return connections.getConnectionsByType(type);
	}

	// SETS-ADDS

	/**
	 * Sets the name of this ClassObject
	 */
	public void setName(String s) {
		name = s;
	}

	/**
	 * Sets the ability of this ClassObject, mostly for pattern purposes.
	 * 
	 * @param s Name of the ability.
	 */
	public void setAbility(String s) {
		ability = s;
	}

	/**
	 * Sets the string variable representing which
	 * class this ClassObject extends
	 */
	public void setExtends(String s) {
		extending = s;
	}

	/**
	 * Sets the enum abstraction type of this ClassObject, abstraction
	 * is an enum type which can be any of the following:
	 * {Abstract}{Interface}{Abstracted}{Normal}{Any}
	 */
	public void set_abstraction(Abstraction a) {
		abstraction = a;
	}

	/**
	 * Adds a String into the Implementing arraylist, input
	 * String should be representing a Class this ClassObject is implementing
	 */
	public void addImplement(String s) {
		Implementing.add(s);
	}

	/**
	 * Adds a String into the MethodInvocation arraylist, input
	 * String should be representing a MethodInvocation of this ClassObject
	 */
	public void addMethodInvocation(String s) {
		MethodInvocations.add(s);
	}

	/**
	 * Adds a Method object into the Methods arraylist, input
	 * Object should be representing a method of this ClassObject
	 */
	public void addMethod(Method m) {
		Methods.add(m);
	}

	/**
	 * Adds a Variable object into the Variables arraylist, input
	 * Object should be representing a variable of this ClassObject
	 */
	public void addMVariable(Variable v) {
		Variables.add(v);
	}

	/**
	 * Adds a String into the Modifiers arraylist, input
	 * String should be representing a modifier of this ClassObject
	 */
	public void addModifier(String s) {
		Modifiers.add(s);
	}

	/**
	 * Adds a String into the New_Instances arraylist, input
	 * String should be representing a new instance of this ClassObject
	 */
	public void addNew_Instance(String s) {
		New_Instances.add(s);
	}

	// PRINTS
	/**
	 * Prints all the details of this ClassObject:
	 * {name}{abstraction}{extends}{implements}{Methods}{Variables}
	 */
	public void printclass() {
		System.out.println("Class name: " + abstraction + " " + name);
		System.out.println("	Modifiers : ");
		for (String s : Modifiers) {
			System.out.println("		" + s);
		}
		System.out.print("	Extends   :\r\n");
		if (extending != null) {
			System.out.print("		" + extending + "\r\n");
		}
		System.out.println("	Implements: ");
		for (String s : Implementing) {
			System.out.println("		" + s);
		}
		System.out.println("Methods: ");
		for (Method m : Methods) {
			m.printmethod();
		}
		System.out.println("Variables: ");
		for (Variable v : Variables) {
			v.printvariable();
		}
		System.out.println();

	}

	/**
	 * Returns a String of the name and the abstraction of this ClassObject
	 * e.g. "pet(Abstract Class)"
	 */
	@Override
	public String toString() {
		return getName() + "(" + abstraction + ")";
	}

	// ------------------------------------ Uses ----------------------------------------
	/**
	 * This function finds all the use connections starting from this ClassObject.
	 * A total parsing is required before this function is called, so that the static Hashmap Classes is filled.
	 * use connections are saved in both a static arraylist containing all use connections(parser.ProjectASTParser.Uses)
	 * and a ClassObject specific arraylist(Uses)
	 */
	public void findUses() {
		Connection tempuse;
		for (Method m : Methods) {
			if (!(GeneralMethods.isPrimitive(m.getReturntype()))) {
				tempuse = new Connection(this, ProjectASTParser.Classes.get(m.getReturntype()), Type.uses);
				connections.add(tempuse);
			}
		}
	}

	/**
	 * Returns true if this ClassObject uses the input ClassObject c,
	 * returns false otherwise
	 */
	public boolean uses(ClassObject c) {
		for (Connection u : connections.getConnectionsByType(Type.uses)) {
			if (u.getTo().equals(c))
				return true;
		}
		return false;
	}

	// ------------------------------------ Uses ----------------------------------------

	// ------------------------------------ Inherits ----------------------------------------
	/**
	 * This function finds all the inherit connections starting from this ClassObject.
	 * A total parsing is required before this function is called, so that the static Hashmap Classes is filled.
	 * inh connections are saved in both a static arraylist containing all inh
	 * connections(parser.ProjectASTParser.Inherits)
	 * and a ClassObject specific arraylist(Inherits)
	 */
	public void findInherits() {
		Connection tempinh;
		if (extending != null) {
			tempinh = new Connection(this, ProjectASTParser.Classes.get(extending), Type.inherits);
			connections.add(tempinh);
		}
		for (String s : Implementing) {
			if (s != null) {
				tempinh = new Connection(this, ProjectASTParser.Classes.get(s), Type.inherits);
				connections.add(tempinh);
			}
		}

	}

	/**
	 * Returns true if this ClassObject inherits the input ClassObject c,
	 * returns false otherwise
	 */
	public boolean inherits(ClassObject c) {
		for (Connection i : connections.getConnectionsByType(Type.inherits)) {
			if (i.getTo().equals(c))
				return true;
		}
		return false;
	}

	// ------------------------------------ Inherits ----------------------------------------

	// ------------------------------------ Has ----------------------------------------
	/**
	 * This function finds all the has connections starting from this ClassObject.
	 * A total parsing is required before this function is called, so that the static Hashmap Classes is filled.
	 * has connections are saved in both a static arraylist containing all has connections(parser.ProjectASTParser.Has)
	 * and a ClassObject specific arraylist(Has)
	 */
	public void findHas() {
		Connection temphas;
		int flag;
		String s;
		for (Variable v : Variables) {
			s = v.gettype();
			flag = 0;
			if (ProjectASTParser.Classes.containsKey(s)) {
				// Check for duplicates (one has is enough)
				for (Connection h : connections.getConnectionsByType(Type.has)) {
					if (h.getTo().getName().equalsIgnoreCase(s))
						flag = 1;
				}
				// End of duplicate checking
				// If it is not duplicate (flag is 0)
				if (flag == 0) {
					temphas = new Connection(this, ProjectASTParser.Classes.get(s), Type.has);
					connections.add(temphas);
				}
			}
		}
	}

	/**
	 * Returns true if this ClassObject has the input ClassObject c,
	 * returns false otherwise
	 */
	public boolean has(ClassObject c) {
		for (Connection h : connections.getConnectionsByType(Type.has)) {
			if (h.getTo().equals(c))
				return true;
		}
		return false;
	}

	// ------------------------------------ Has ----------------------------------------

	// ------------------------------------ Calls ----------------------------------------
	/**
	 * This function finds all the call connections starting from this ClassObject.
	 * A total parsing is required before this function is called, so that the static Hashmap Classes is filled.
	 * call connections are saved in both a static arraylist containing all call
	 * connections(parser.ProjectASTParser.Calls)
	 * and a ClassObject specific arraylist(Calls)
	 */
	public void findCalls() {
		Connection tempcall;
		int flag;
		String[] words;
		for (String s : MethodInvocations) {
			words = s.split("\\.");
			for (i = 0; i < words.length - 1; i++) {
				flag = 0;
				for (Variable v : Variables) {
					if (words[i].equals(v.getName())) {
						// this word is a variable, next should be its method
						for (ClassObject c : ProjectASTParser.Classes.values()) {
							for (Method m : c.getMethods()) {
								if (words[i + 1].equals(m.getName()) && (v.gettype().equals(c.getName()))) {
									// Check for duplicates (one calls is enough)
									for (Connection ca : connections.getConnectionsByType(Type.calls)) {
										if (ca.getTo().getName().equalsIgnoreCase(c.getName()))
											flag = 1;
									}
									// End of duplicate checking
									// If it is not duplicate (flag is 0)
									if (flag == 0) {
										tempcall = new Connection(this, c, Type.calls);
										connections.add(tempcall);
									}
								}
							}
						}
					}
				}
			}

		}

	}

	/**
	 * Returns true if this ClassObject calls the input ClassObject c,
	 * returns false otherwise
	 */
	public boolean calls(ClassObject c) {
		for (Connection cll : connections.getConnectionsByType(Type.calls)) {
			if (cll.getTo().equals(c))
				return true;
		}
		return false;
	}

	// ------------------------------------ Calls ----------------------------------------

	// ------------------------------------ Creates ----------------------------------------
	/**
	 * This function finds all the create connections starting from this ClassObject.
	 * A total parsing is required before this function is called, so that the static Hashmap Classes is filled.
	 * create connections are saved in both a static arraylist containing all create
	 * connections(parser.ProjectASTParser.Creates)
	 * and a ClassObject specific arraylist(Creates)
	 */
	public void findCreates() {
		Connection tempcreate;
		int flag;
		for (String s : New_Instances) {
			flag = 0;
			if (ProjectASTParser.Classes.containsKey(s)) {
				// Check for duplicates (one create is enough)
				for (Connection c : connections.getConnectionsByType(Type.creates)) {
					if (c.getTo().getName().equalsIgnoreCase(s))
						flag = 1;
				}
				// End of duplicate checking
				// If it is not duplicate (flag is 0)
				if (flag == 0) {
					tempcreate = new Connection(this, ProjectASTParser.Classes.get(s), Type.creates);
					connections.add(tempcreate);
				}
			}
		}

	}

	/**
	 * Returns true if this ClassObject creates the input ClassObject c,
	 * returns false otherwise
	 */
	public boolean creates(ClassObject c) {
		for (Connection cr : connections.getConnectionsByType(Type.creates)) {
			if (cr.getTo().equals(c))
				return true;
		}
		return false;
	}

	// ------------------------------------ Creates ----------------------------------------

	// ------------------------------------ References ----------------------------------------
	/**
	 * This function finds all the reference connections starting from this ClassObject.
	 * A total parsing is required before this function is called, so that the static Hashmap Classes is filled.
	 * ref connections are saved in both a static arraylist containing all ref
	 * connections(parser.ProjectASTParser.References)
	 * and a ClassObject specific arraylist(References)
	 */
	public void findReferences() {
		Connection tempref;
		int flag;
		for (Method m : Methods) {
			for (String s : m.getInputtypes()) {
				flag = 0;
				if (!(GeneralMethods.isPrimitive(s))) {
					// Check for duplicates (one reference is enough)
					for (Connection r : connections.getConnectionsByType(Type.references)) {
						if (r.getTo().getName().equalsIgnoreCase(s))
							flag = 1;
					}
					// End of duplicate checking
					// If it is not duplicate (flag is 0)
					if (flag == 0) {
						tempref = new Connection(this, ProjectASTParser.Classes.get(s), Type.references);
						connections.add(tempref);
					}
				}
			}
		}
	}

	/**
	 * Returns true if this ClassObject references the input ClassObject c,
	 * returns false otherwise
	 */
	public boolean references(ClassObject c) {
		for (Connection r : connections.getConnectionsByType(Type.references)) {
			if (r.getTo().equals(c))
				return true;
		}
		return false;
	}
	// ------------------------------------ References ----------------------------------------

	public boolean relates(ClassObject c) {
		for (Connection r : connections) {
			if (r.getTo().equals(c))
				return true;
		}
		return false;
	}
}
