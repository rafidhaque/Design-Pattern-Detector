package parser;

import java.util.ArrayList;

import parser.Connection.Type;

/**
 * Extends ArrayList<Connection> to hold up Connection objects we find.
 * 
 */
@SuppressWarnings("serial")
public class Connections extends ArrayList<Connection> {

	/**
	 * Returns a filtered form of this object, depending on the input type.
	 * 
	 * @param type Can be: {call}{create}{ref}{use}{inh}{has}{wildcard}
	 */
	public ArrayList<Connection> getConnectionsByType(Type type) {
		ArrayList<Connection> filteredConnections = new ArrayList<Connection>();
		for (Connection connection : this) {
			if (connection.hasType(type))
				filteredConnections.add(connection);
		}
		return filteredConnections;
	}
}
