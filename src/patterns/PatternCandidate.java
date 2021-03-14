package patterns;

import java.util.ArrayList;

import parser.ClassObject;

/**
 * Represents a number of ClassObjects and their roles as parts of a Design Pattern.
 */
public class PatternCandidate {

	// //////////////////// VARIABLES ///////////////////////////////

	protected int MemberCount = 0;
	protected String PatternName;
	protected ArrayList<ClassObject> Members = new ArrayList<ClassObject>();
	protected ArrayList<String> MemberNames = new ArrayList<String>();
	protected ArrayList<String> MemberAbilities = new ArrayList<String>();

	// /////////////////// METHODS //////////////////////////////

	public PatternCandidate(String s) {
		PatternName = s;
	}

	/**
	 * Takes as input a ClassObject and a String representing a part(ClassObject) of the Design Pattern and its
	 * respective role(String), adding them to their respective arraylists.
	 * 
	 * @param c the ClassObject.
	 * @param s1 member name e.g. "A".
	 * @param s2 member pattern name e.g. "Concrete Factory".
	 */
	public void addMember(ClassObject c, String s1, String s2) {
		Members.add(c);
		MemberNames.add(s1);
		MemberAbilities.add(s2);
		MemberCount++;
	}

	/**
	 * Returns the name of the Pattern.
	 * 
	 * @return the name of the Pattern.
	 */
	public String getPatternName() {
		return PatternName;
	}

	/**
	 * Prints the members of this PatternCandidate and their respective roles.
	 */
	public void printcandidate() {
		for (int i = 0; i < MemberCount; i++) {
			System.out.println(MemberNames.get(i) + "(" + MemberAbilities.get(i) + "): " + Members.get(i).getName());
		}
	}

	/**
	 * Prints the members of this PatternCandidate and their respective roles into a String.
	 * 
	 * @return a String containing the members of this PatternCandidate.
	 */
	public String candidatetoString() {
		String s = "";
		for (int i = 0; i < MemberCount; i++) {
			s += MemberNames.get(i) + "(" + MemberAbilities.get(i) + "): " + Members.get(i).getName() + "\n";
		}
		return s;
	}

	/**
	 * Returns an integer representing the number of this PatternCandidate's members.
	 * 
	 * @return an integer representing the number of this PatternCandidate's members.
	 */
	public int getMemberCount() {
		return MemberCount;
	}

	/**
	 * Sets an integer representing the number of this PatternCandidate's members.
	 * 
	 * @param memberCount the number of this PatternCandidate's members to be set.
	 */
	public void setMemberCount(int memberCount) {
		MemberCount = memberCount;
	}

	/**
	 * Returns an ArrayList of ClassObjects which contains the ClassObjects this PatternCandidate consists of.
	 * 
	 * @return an ArrayList of ClassObjects which contains the ClassObjects this PatternCandidate consists of.
	 */
	public ArrayList<ClassObject> getMembers() {
		return Members;
	}

	/**
	 * Sets an ArrayList of ClassObjects which contains the ClassObjects this PatternCandidate consists of.
	 * 
	 * @param members the ArrayList to be set which contains the ClassObjects this PatternCandidate consists of.
	 */
	public void setMembers(ArrayList<ClassObject> members) {
		Members = members;
	}

	/**
	 * Returns an ArrayList of Strings which contains the names of this PatternCandidate's ClassObjects.
	 * 
	 * @return an ArrayList of Strings which contains the names of this PatternCandidate's ClassObjects.
	 */
	public ArrayList<String> getMemberNames() {
		return MemberNames;
	}

	/**
	 * Sets an ArrayList of Strings which contains the names of this PatternCandidate's ClassObjects.
	 * 
	 * @param memberNames the ArrayList which contains the names to be set to this PatternCandidate's ClassObjects.
	 */
	public void setMemberNames(ArrayList<String> memberNames) {
		MemberNames = memberNames;
	}

	/**
	 * Returns an ArrayList of Strings which contains the Abilities of this PatternCandidate's Pattern.
	 * 
	 * @return an ArrayList of Strings which contains the Abilities of this PatternCandidate's Pattern.
	 */
	public ArrayList<String> getMemberAbilities() {
		return MemberAbilities;
	}

	/**
	 * Sets an ArrayList of Strings which contains the Abilities of this PatternCandidate's Pattern.
	 * 
	 * @param memberAbilities the ArrayList which contains the Abilities to be set to this PatternCandidate's Pattern.
	 */
	public void setMemberAbilities(ArrayList<String> memberAbilities) {
		MemberAbilities = memberAbilities;
	}
}
