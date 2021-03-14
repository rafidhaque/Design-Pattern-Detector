package patterns;

import java.util.ArrayList;

import parser.ClassObject;

/**
 * Represents Several SuperCandidates or PatternCandidates grouped up under a double dissimilarity. A HyperCandidate
 * consists of 2 or more SuperCandidates or PatternCandidates which share 2 specific dissimilarities in their Abilities.
 */
public class HyperCandidate extends PatternCandidate {

	// //////////////////// VARIABLES ///////////////////////////////

	/**
	 * Specifies the first dissimilar Ability of the pattern this HyperCandidate is based on to be created.
	 */
	private int dissimilarity1;
	/**
	 * Specifies the second dissimilar Ability of the pattern this HyperCandidate is based on to be created.
	 */
	private int dissimilarity2;
	private ArrayList<ArrayList<ClassObject>> Diss1Members = new ArrayList<ArrayList<ClassObject>>();
	private ArrayList<ArrayList<ClassObject>> Diss2Members = new ArrayList<ArrayList<ClassObject>>();
	private ArrayList<PatternCandidate> Mergers = new ArrayList<PatternCandidate>();

	// /////////////////// METHODS //////////////////////////////

	public HyperCandidate(PatternCandidate c, ArrayList<Integer> d) {
		super(c.getPatternName());
		MemberCount = c.getMemberCount();
		dissimilarity1 = d.get(0);
		dissimilarity2 = d.get(1);
		for (int i = 0; i < MemberCount; i++) {
			if (i != dissimilarity1 && i != dissimilarity2) {
				Members.add(c.getMembers().get(i));
				MemberAbilities.add(c.getMemberAbilities().get(i));
				MemberNames.add(c.getMemberNames().get(i));
			} else {
				Members.add(c.getMembers().get(i));
				MemberAbilities.add(c.getMemberAbilities().get(i));
				MemberNames.add(c.getMemberNames().get(i));
				if (c instanceof SuperCandidate) {
					if (i == dissimilarity1) {
						if (((SuperCandidate) c).getDissimilarity() == i) {
							Diss1Members.add(((SuperCandidate) c).getMergedMembers());
						} else {
							ArrayList<ClassObject> temp = new ArrayList<ClassObject>();
							temp.add(c.getMembers().get(i));
							Diss1Members.add(temp);
						}
					} else {
						if (((SuperCandidate) c).getDissimilarity() == i) {
							Diss2Members.add(((SuperCandidate) c).getMergedMembers());
						} else {
							ArrayList<ClassObject> temp = new ArrayList<ClassObject>();
							temp.add(c.getMembers().get(i));
							Diss2Members.add(temp);
						}
					}
				} else {
					ArrayList<ClassObject> temp = new ArrayList<ClassObject>();
					temp.add(c.getMembers().get(i));
					Diss1Members.add(temp);
					if (i == dissimilarity1) {
						Diss1Members.add(temp);
					} else {
						Diss2Members.add(temp);
					}
				}
			}
		}
		Mergers.add(c);
	}

	/**
	 * Adds a PatternCandidate or SuperCandidate to this HyperCandidate.
	 * 
	 * @param c the PatternCandidate or SuperCandidate to be added.
	 */
	public void addHyperMember(PatternCandidate c) {
		Mergers.add(c);
		if (c instanceof SuperCandidate) {
			if (((SuperCandidate) c).getDissimilarity() == dissimilarity1) {
				Diss1Members.add(((SuperCandidate) c).getMergedMembers());
			} else {
				ArrayList<ClassObject> temp = new ArrayList<ClassObject>();
				temp.add(c.getMembers().get(dissimilarity1));
				Diss1Members.add(temp);
			}
			if (((SuperCandidate) c).getDissimilarity() == dissimilarity2) {
				Diss2Members.add(((SuperCandidate) c).getMergedMembers());
			} else {
				ArrayList<ClassObject> temp = new ArrayList<ClassObject>();
				temp.add(c.getMembers().get(dissimilarity2));
				Diss2Members.add(temp);
			}
		} else {
			ArrayList<ClassObject> temp = new ArrayList<ClassObject>();
			temp.add(c.getMembers().get(dissimilarity1));
			Diss1Members.add(temp);

			ArrayList<ClassObject> temp2 = new ArrayList<ClassObject>();
			temp2.add(c.getMembers().get(dissimilarity2));
			Diss2Members.add(temp2);
		}
	}

	/**
	 * Prints this HyperCandidate.
	 */
	@Override
	public void printcandidate() {
		for (int i = 0; i < MemberCount; i++) {
			if (i != dissimilarity1 && i != dissimilarity2)
				System.out
						.println(MemberNames.get(i) + "(" + MemberAbilities.get(i) + "): " + Members.get(i).getName());
			else {
				System.out.print(MemberNames.get(i) + "(" + MemberAbilities.get(i) + "): { ");
				if (i == dissimilarity1) {
					for (ArrayList<ClassObject> c : Diss1Members) {
						System.out.print("[ ");
						for (int j = 0; j < c.size(); j++) {
							System.out.print(c.get(j).getName());
							if ((c.size() > (j + 1)))
								System.out.print(", ");
						}
						System.out.print(" ] ");
					}
					System.out.println("}");
				} else {
					for (ArrayList<ClassObject> c : Diss2Members) {
						System.out.print("[ ");
						for (int j = 0; j < c.size(); j++) {
							System.out.print(c.get(j).getName());
							if ((c.size() > (j + 1)))
								System.out.print(", ");
						}
						System.out.print(" ] ");
					}
					System.out.println("}");
				}
			}
		}
	}

	/**
	 * Prints the members of this HyperCandidate and their respective roles into a String.
	 * 
	 * @return a String containing the members of this HyperCandidate.
	 */
	@Override
	public String candidatetoString() {
		String s = "";
		for (int i = 0; i < MemberCount; i++) {
			if (i != dissimilarity1 && i != dissimilarity2)
				s += MemberNames.get(i) + "(" + MemberAbilities.get(i) + "): " + Members.get(i).getName() + "\n";
			else {
				s += MemberNames.get(i) + "(" + MemberAbilities.get(i) + "): { ";
				if (i == dissimilarity1) {
					for (ArrayList<ClassObject> c : Diss1Members) {
						s += "[ ";
						for (int j = 0; j < c.size(); j++) {
							s += c.get(j).getName();
							if ((c.size() > (j + 1)))
								s += ", ";
						}
						s += " ] ";
					}
					s += "}" + "\n";
				} else {
					for (ArrayList<ClassObject> c : Diss2Members) {
						s += "[ ";
						for (int j = 0; j < c.size(); j++) {
							s += c.get(j).getName();
							if ((c.size() > (j + 1)))
								s += ", ";
						}
						s += " ] ";
					}
					s += "}" + "\n";
				}
			}
		}
		return s;
	}

	/**
	 * Returns an integer representing which Ability has the first dissimilarity.
	 * 
	 * @return an integer representing which Ability has the first dissimilarity.
	 */
	public int getDissimilarity1() {
		return dissimilarity1;
	}

	/**
	 * Sets an integer representing which Ability has the first dissimilarity.
	 * 
	 * @param dissimilarity the Ability that has the first dissimilarity.
	 */
	public void setDissimilarity1(int dissimilarity) {
		this.dissimilarity1 = dissimilarity;
	}

	/**
	 * Returns an integer representing which Ability has the second dissimilarity.
	 * 
	 * @return an integer representing which Ability has the second dissimilarity.
	 */
	public int getDissimilarity2() {
		return dissimilarity2;
	}

	/**
	 * Sets an integer representing which Ability has the second dissimilarity.
	 * 
	 * @param dissimilarity the Ability that has the second dissimilarity.
	 */
	public void setDissimilarity2(int dissimilarity) {
		this.dissimilarity2 = dissimilarity;
	}

	/**
	 * Returns an ArrayList with the PatternCandidates or SuperCandidates this HyperCandidate consists of.
	 * 
	 * @return an ArrayList with the PatternCandidates or SuperCandidates this HyperCandidate consists of.
	 */
	public ArrayList<PatternCandidate> getMergers() {
		return Mergers;
	}

	/**
	 * Sets an ArrayList with the PatternCandidates or SuperCandidates this HyperCandidate consists of.
	 * 
	 * @param mergers the PatternCandidates or SuperCandidates this HyperCandidate consists of.
	 */
	public void setMergers(ArrayList<PatternCandidate> mergers) {
		Mergers = mergers;
	}

}
