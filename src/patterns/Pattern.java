package patterns;

import java.util.ArrayList;

import parser.ClassObject;
import parser.ClassObject.Abstraction;
import parser.Connection;
import parser.Connection.Type;

/**
 * Represents a Pattern. The Pattern must be defined by the user (in main) through
 * its input functions.{insert_connection}{insert_member}
 */
public class Pattern {

	// //////////////////// VARIABLES ///////////////////////////////

	private String name;
	private ArrayList<ClassObject> Members = new ArrayList<ClassObject>();
	protected ArrayList<Connection> Connections = new ArrayList<Connection>();
	protected int conn_num = 0;
	protected int memb_num = 0;

	// /////////////////// METHODS //////////////////////////////

	public Pattern(String s) {
		name = s;
	}

	/**
	 * Creates and adds a Connection object to the Connection arraylist Connections, defined by the input arguments.
	 * Example call: insert_connection("A", type.uses, "B").
	 * 
	 * @param s1 first member of Connection.
	 * @param t connection type.
	 * @param s2 second member of Connection.
	 */
	public void insert_connection(String s1, Type t, String s2) {
		int flag1 = 0;
		int flag2 = 0;
		// Checking if the Strings that were given are not parts of this Pattern's Members
		ClassObject pm1 = null;
		ClassObject pm2 = null;
		for (ClassObject pm : Members) {
			if (pm.getName().equals(s1)) {
				flag1 = 1;
				pm1 = pm;
			}

			if (pm.getName().equals(s2)) {
				flag2 = 1;
				pm2 = pm;
			}
		}
		if (flag1 == 0) {
			System.out.println("Pattern does not contain member " + s1);
			System.out.println("Please RESTART");
			parser.ProjectASTParser.getchar();
		}
		if (flag2 == 0) {
			System.out.println("Pattern does not contain member " + s2);
			System.out.println("Please RESTART");
			parser.ProjectASTParser.getchar();
		}
		Connection temp = new Connection(pm1, pm2, t);
		insert_connection(temp);
	}

	/**
	 * Adds a Connection object to the Connection arraylist Connections.
	 */
	public void insert_connection(Connection ac) {
		Connections.add(ac);
		conn_num++;
	}

	/**
	 * Adds a Pattern_Member and all its details to arraylist Members of this Pattern.
	 * Example call: insert_member("A", Abstraction.Abstracted, "Abstract Factory")
	 * 
	 * @param s1 member name e.g. "A".
	 * @param a abstraction needed by this member, can be one of {Abstract, Interface, Abstracted, Normal, Any}.
	 * @param s2 member pattern name e.g. "Concrete Factory".
	 */
	public void insert_member(String s1, Abstraction a, String s2) {
		int flag1 = 0;
		int flag2 = 0;
		// Checking if the Strings that were given already exist as parts of a Member
		for (ClassObject pm : Members) {
			if (pm.getName().equals(s1)) {
				flag1 = 1;
			}
			if (pm.getAbility().equals(s2)) {
				flag2 = 1;
			}
		}
		if (flag1 == 1) {
			System.out.println("Pattern already contains member " + s1);
			System.out.println("Please RESTART");
			parser.ProjectASTParser.getchar();
		}
		if (flag2 == 1) {
			System.out.println("Pattern already contains ability " + s2);
			System.out.println("Please RESTART");
			parser.ProjectASTParser.getchar();
		}
		ClassObject temp = new ClassObject();
		temp.setName(s1);
		temp.set_abstraction(a);
		temp.setAbility(s2);
		Members.add(temp);
		memb_num++;
	}

	/**
	 * Returns the ClassObject arraylist Members, which represents the members of this Pattern.
	 */
	public ArrayList<ClassObject> get_Members() {
		return Members;
	}

	/**
	 * Returns the Connection arraylist Connections, which represents the connections of this Pattern
	 * has.
	 */
	public ArrayList<Connection> get_Connections() {
		return Connections;
	}

	/**
	 * Returns the name of this Pattern.
	 */
	public String get_name() {
		return name;
	}

	/**
	 * Returns an integer representing the number of connections this Pattern has.
	 */
	public int get_conn_num() {
		return conn_num;
	}

	/**
	 * Returns an integer representing the number of members this Pattern has.
	 */
	public int get_memb_num() {
		return memb_num;
	}

	/**
	 * Overrides function toString(), in order to print this Pattern members and connections.
	 */
	@Override
	public String toString() {
		String res = "";
		for (ClassObject pm : Members) {
			res += pm.getName() + "(" + pm.get_abstraction() + ") : " + pm.getAbility() + "\n";

		}
		for (Connection connection : Connections) {
			res += connection + "\n";
		}

		return res;
	}

	/**
	 * Clears this Pattern Object.
	 */
	public void clear() {
		name = "";
		conn_num = 0;
		memb_num = 0;
		Members.clear();
		Connections.clear();
	}
}
