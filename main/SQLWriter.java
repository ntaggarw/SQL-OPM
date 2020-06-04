import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
	Types of Queries
	- Insert Query
	- Select Query
	- Update Query
	- Remove Query
 */

/**
 * @author Naryan Aggarwal
 * @since 05/01/2020
 * @version 1.0
 */
public class SQLWriter {
	
	/**
	 * Executes the SQL Insert command using Java-style arguments (For those who lack SQL knowledge)
	 * @param conn The connection being used.
	 * @param tableName The name of the table.
	 * @param columnNames the names of the columns being inserted into.
	 * @param values The values being inserted.
	 * @return whether the operation was successful or not.
	 * @throws SQLException
	 */
	public static String insertInto(Connection conn, String tableName, String columnNames, String... values) throws SQLException {
		String sql = "INSERT INTO " + tableName + " ";
		sql+=" (" + columnNames + ") VALUES (";
		sql += String.join(", ", values);
		sql+=")";
		PreparedStatement stmt = conn.prepareStatement(sql);
		int row = stmt.executeUpdate();
		// TODO Replace "Oh no" with custom SQLException
		return (row > 0) ? "Success" : "Oh no";
	}
	
	/**
	 * Executes the SQL update command using Java-style arguments (For those who lack SQL knowledge)
	 * @param tableName Name of the table being edited.
	 * @param columnNames name of the columns being edited. must match Values.
	 * @param conn The connection established.
	 * @param condition The condition in which something will be replaced. Use AND or OR to add multiple.
	 * @param values The new values.
	 * @return whether it was successful.
	 * @throws SQLException
	 */
	public static String update(String tableName, String[] columnNames, Connection conn, String condition, String... values) throws SQLException {
		PreparedStatement stmt; String sql = "UPDATE " + tableName + " SET ";
		for(String column: columnNames) {
			sql+=column + " = ?"; 
		}
		
		stmt = conn.prepareStatement(sql);
		int i = 0;
		for(String s : values) {
			i+=1;
			stmt.setString(i, s);
		}
		
		int row = stmt.executeUpdate();
		return (row > 0) ? "Success" : "Oh no";
	}
	
	/**
	 * @deprecated
	 * Executes the SQL delete command using Java-style arguments (For those who lack SQL knowledge)
	 * @param tableName The name of the table.
	 * @param conn The connection being used.
	 * @param isAnd Whether the connector will be AND or OR.
	 * @param condition What conditions data will be deleted.
	 * @return whether the operation was successful.
	 * @throws SQLException
	 */
	public static String exterminate(String tableName, Connection conn, boolean isAnd, String... condition) throws SQLException {
		PreparedStatement stmt; String sql="DELETE FROM " + tableName + " WHERE";
		for(String s : condition) {
			sql+=" " + s;
			if(isAnd) {
				sql+=" AND";
			} else {
				sql+=" OR";
			}
		}
		stmt = conn.prepareStatement(sql);
		int row = stmt.executeUpdate();
		return (row > 0) ? "Success" : "Oh no";
	}
	
	/**
	 * Executes the SQL delete command using Java-style arguments (For those who lack SQL knowledge)
	 * @param tableName The name of the table.
	 * @param conn The connection being used.
	 * @param condition What conditions data will be deleted.
	 * @return whether the operation was successful.
	 * @throws SQLException
	 */
	public static String exterminate(String tableName, Connection conn, String... condition) throws SQLException {
		PreparedStatement stmt; String sql="DELETE FROM " + tableName + " WHERE";
		for(String s : condition) {
			sql+=" " + s;
		}
		stmt = conn.prepareStatement(sql);
		int row = stmt.executeUpdate();
		return (row > 0) ? "Success" : "Oh no";
	}
	
	/**
	 * Executes the SQL select command using Java-style arguments (For those who lack SQL knowledge)
	 * @param conn the connection being used.
	 * @param tableName The table being used.
	 * @param columns The columns being retrieved.
	 * @return a ResultSet of the selected data.
	 * @throws SQLException
	 */
	public static ResultSet select(Connection conn, String tableName, String... columns) throws SQLException {
		PreparedStatement stmt; String sql="SELECT ";
		for(String s : columns) {
			sql+=s + ", ";
		}
		sql = sql.substring(0, sql.length()-2);
		sql+="FROM " + tableName;
		stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		return rs;
	}
	
	public static ResultSet select(Connection conn, String[] tableNames, String[] conditions, String[]... columns) {
		PreparedStatement stmt; String sql = "SELECT ";
		// Builds part 1 of the statement.
		String[][] columnMatrix = new String[tableNames.length][]; int i = 0; int j = 0;
		for(String table: tableNames) {
			for(String[] column : columns) {
				columnMatrix[i] = new String[column.length];
				for(String value : column) {
					sql+=table + "." + value + ",";
					columnMatrix[i][j] = value;
				}
				j+=1;
			}
			i+=1;
		}
		// Cleans up part 1
		sql = sql.substring(0, sql.length()-2) + " FROM ";
		
		
		return null;
	}
	
	public static String selectHelper(Connection conn, String[] tableNames, String[] conditions, String[][] columns) {
		String result = "";
		result+= tableNames[0] + " ";
		if(tableNames.length > 1) {
			result+="INNER JOIN (";
			// TODO
			result+=") ";
		}
		
		
		
		return null;
	}
	
	public static ResultSet select(Connection conn, String tableName, String conditions, String... columns) throws SQLException {
		PreparedStatement stmt; String sql="SELECT ";
		for(String s : columns) {
			sql+=s + ", ";
		}
		sql+="FROM " + tableName + " WHERE " + conditions;
		stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		return rs;
	}
}
