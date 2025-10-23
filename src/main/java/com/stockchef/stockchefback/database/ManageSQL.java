package com.stockchef.stockchefback.database;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A utility class to handle MySQL database operations with connection pooling.
 */
@Component
public class ManageSQL {

    private final DataSource dataSource;

    public ManageSQL(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Executes a SELECT SQL query and returns the results as a list of maps.
     * Each map represents a row with column names as keys.
     *
     * @param query The SQL SELECT query to execute
     * @param params Optional parameters for prepared statement
     * @return List of maps containing the query results
     * @throws SQLException If a database access error occurs
     */
    public List<Map<String, Object>> executeSelectSql(String query, Object... params) throws SQLException {
        List<Map<String, Object>> results = new ArrayList<>();
        
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            // Set parameters if any
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            
            try (ResultSet resultSet = statement.executeQuery()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                
                while (resultSet.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnLabel(i);
                        Object value = resultSet.getObject(i);
                        row.put(columnName, value);
                    }
                    results.add(row);
                }
            }
        }
        
        return results;
    }

    /**
     * Executes an UPDATE, INSERT, or DELETE SQL statement.
     *
     * @param query The SQL statement to execute
     * @param params Optional parameters for prepared statement
     * @return The number of rows affected by the SQL statement
     * @throws SQLException If a database access error occurs
     */
    public int executeUpdateSql(String query, Object... params) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            // Set parameters if any
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            
            return statement.executeUpdate();
        }
    }

    /**
     * Executes an INSERT statement and returns the generated keys.
     *
     * @param query The SQL INSERT statement
     * @param params Parameters for the prepared statement
     * @return A map containing the generated keys
     * @throws SQLException If a database access error occurs
     */
    public Map<String, Object> executeInsertWithGeneratedKeys(String query, Object... params) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            // Set parameters if any
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            
            int affectedRows = statement.executeUpdate();
            Map<String, Object> result = new HashMap<>();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        result.put("generatedKey", generatedKeys.getObject(1));
                    }
                }
            }
            
            result.put("affectedRows", affectedRows);
            return result;
        }
    }

    /**
     * Executes a batch of SQL statements in a transaction.
     *
     * @param queries Array of SQL statements to execute
     * @return Array of update counts for each statement
     * @throws SQLException If a database access error occurs or the SQL statement returns a ResultSet object
     */
    public int[] executeBatch(String[] queries) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            
            connection.setAutoCommit(false);
            
            for (String query : queries) {
                statement.addBatch(query);
            }
            
            int[] results = statement.executeBatch();
            connection.commit();
            return results;
        }
    }
}
