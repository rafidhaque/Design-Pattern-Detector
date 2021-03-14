package parser;

/**
 * Represents a connection between 2 ClassObjects.
 * 
 */
public class Connection {

	public enum Type {
		calls, creates, references, uses, inherits, has, relates
	};

	protected ClassObject from;
	protected ClassObject to;
	protected Type type;

	public Connection(ClassObject A, ClassObject B, Type type) {
		from = A;
		to = B;
		this.type = type;
	}

	/**
	 * Returns a ClassObject representing the first part of this Connection.
	 * 
	 */
	public ClassObject getFrom() {
		return from;
	}

	/**
	 * Returns a ClassObject representing the second part of this Connection.
	 * 
	 */
	public ClassObject getTo() {
		return to;
	}

	/**
	 * Returns an enum type representing the type of this Connection.
	 * 
	 */
	public Type getType() {
		return type;
	}

	public boolean hasType(Type type) {
		return this.type == type;
	}
	
	/**
	 * Overriding toString() to print the appropriate results.
	 */
	@Override
	public String toString() {
		return from + " " + to + " " + type;
	}
}