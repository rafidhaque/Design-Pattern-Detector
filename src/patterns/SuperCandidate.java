package patterns;

import java.util.ArrayList;

import parser.ClassObject;

/**
 * Represents Several PatternCandidates grouped up under a single dissimilarity. A SuperCandidate consists of 2 or more
 * PatternCandidates with 1 specific Ability being dissimilar.
 */
public class SuperCandidate extends PatternCandidate {

	// //////////////////// VARIABLES ///////////////////////////////

	/**
	 * Specifies which Ability of the pattern this SuperCandidate is based on to be created.
	 */
	private int dissimilarity;
	private ArrayList<ClassObject> MergedMembers = new ArrayList<ClassObject>();
	private ArrayList<PatternCandidate> Mergers = new ArrayList<PatternCandidate>();

	// /////////////////// METHODS //////////////////////////////

	public SuperCandidate(PatternCandidate c, int d) {
		super(c.getPatternName());
		MemberCount = c.getMemberCount();
		dissimilarity = d;
		for (int i = 0; i < MemberCount; i++) {
			if (i != d) {
				Members.add(c.getMembers().get(i));
				MemberAbilities.add(c.getMemberAbilities().get(i));
				MemberNames.add(c.getMemberNames().get(i));
			} else {
				Members.add(c.getMembers().get(i));
				MemberAbilities.add(c.getMemberAbilities().get(i));
				MemberNames.add(c.getMemberNames().get(i));
				MergedMembers.add(c.getMembers().get(i));
			}
		}
		Mergers.add(c);
	}

	/**
	 * Adds a PatternCandidate to this SuperCandidate.
	 * 
	 * @param c the PatternCandidate to be added.
	 */
	public void addDissimilarMember(PatternCandidate c) {
		Mergers.add(c);
		MergedMembers.add(c.getMembers().get(dissimilarity));
	}

	/**
	 * Prints this SuperCandidate.
	 */
	@Override
	public void printcandidate() {
		for (int i = 0; i < MemberCount; i++) {
			if (i != dissimilarity)
				System.out
						.println(MemberNames.get(i) + "(" + MemberAbilities.get(i) + "): " + Members.get(i).getName());
			else {
				System.out.print(MemberNames.get(i) + "(" + MemberAbilities.get(i) + "): {");
				for (ClassObject c : MergedMembers) {
					System.out.print(c.getName());
					if (!c.equals(MergedMembers.get(MergedMembers.size() - 1)))
						System.out.print(", ");
				}
				System.out.println("}");
			}
		}
	}

	/**
	 * Returns an integer representing which Ability has the dissimilarity.
	 * 
	 * @return an integer representing which Ability has the dissimilarity.
	 */
	public int getDissimilarity() {
		return dissimilarity;
	}

	/**
	 * Sets an integer representing which Ability has the dissimilarity.
	 * 
	 * @param dissimilarity the Ability that has the dissimilarity.
	 */
	public void setDissimilarity(int dissimilarity) {
		this.dissimilarity = dissimilarity;
	}

	/**
	 * Returns an ArrayList with the ClassObjects this SuperCandidate consists of.
	 * 
	 * @return an ArrayList with the ClassObjects this SuperCandidate consists of.
	 */
	public ArrayList<ClassObject> getMergedMembers() {
		return MergedMembers;
	}

	/**
	 * Sets an ArrayList with the ClassObjects this SuperCandidate consists of.
	 * 
	 * @param mergedMembers the ArrayList with the ClassObjects this SuperCandidate consists of.
	 */
	public void setMergedMembers(ArrayList<ClassObject> mergedMembers) {
		MergedMembers = mergedMembers;
	}

	/**
	 * Returns an ArrayList with the PatternCandidates this SuperCandidate consists of.
	 * 
	 * @return an ArrayList with the PatternCandidates this SuperCandidate consists of.
	 */
	public ArrayList<PatternCandidate> getMergers() {
		return Mergers;
	}

	/**
	 * Sets an ArrayList with the PatternCandidates this SuperCandidate consists of.
	 * 
	 * @param mergers the ArrayList with the PatternCandidates this SuperCandidate consists of.
	 */
	public void setMergers(ArrayList<PatternCandidate> mergers) {
		Mergers = mergers;
	}

}
