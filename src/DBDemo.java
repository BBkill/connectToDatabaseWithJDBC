import java.sql.*;
import java.util.Properties;

public class DBDemo {
    /** The name of the MySQL account to use (or empty for anonymous) */
    private final String userName = "root";

    /** The password for the MySQL account (or empty for anonymous) */
    private final String password = "dominhduc1202";

    /** The name of the computer running MySQL */
    private final String serverName = "localhost";

    /** The port of the MySQL server (default is 3306) */
    private final int portNumber = 3306;

    /** The name of the database we are testing with (this default is installed with MySQL) */
    private final String dbName = "dbdemo";

    /** The name of the table we are testing with */
    private String tableName = "Product";

    /**
     * Get a new database connection
     *
     * @return
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.userName);
        connectionProps.put("password", this.password);

        conn = DriverManager.getConnection("jdbc:mysql://"
                        + this.serverName + ":" + this.portNumber + "/" + this.dbName,
                connectionProps);

        return conn;
    }

    /**
     * Run a SQL command which does not return a recordset:
     * CREATE/INSERT/UPDATE/DELETE/DROP/etc.
     *
     * @throws SQLException If something goes wrong
     */
    public boolean executeUpdate(Connection conn, String command) throws SQLException {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(command); // This will throw a SQLException if it fails
            return true;
        } finally {
            // This will run whether we throw an exception or not
            if (stmt != null) { stmt.close(); }
        }
    }

    /**
     * Connect to MySQL and do some stuff.
     */

    public void createTable() {
        Connection conn = null;
        try {
            conn = this.getConnection();
            System.out.println("Connected to database");
        } catch (SQLException e) {
            System.out.println("ERROR: Could not connect to the database");
            e.printStackTrace();
            return;
        }
        // Create a table
        try {
            String createString =
                    "CREATE TABLE " + tableName + " ( " +
                            "code varchar(40) NOT NULL, " +
                            "description text NOT NULL, " +
                            "price int, " +
                            "PRIMARY KEY (code))";
            this.executeUpdate(conn, createString);
            System.out.println("Created a table");
        } catch (SQLException e) {
            System.out.println("ERROR: Could not create the table");
            e.printStackTrace();
            return;
        }

    }

    public void insertData(String code, String description, String price) {
        Connection conn = null;
        try {
            conn = this.getConnection();
        } catch (SQLException e) {
            System.out.println("ERROR: Could not connect to the database");
            e.printStackTrace();
            return;
        }

        try {
            String insertData = "INSERT INTO Product"+ "(" + "code,"+"description,"+"price"+") VALUES "+"('"+code+"','"+description+"',"+price+")";
            System.out.println("inserted successful");
            this.executeUpdate(conn, insertData);
        }
        catch (SQLException e) {
            System.out.println("ERROR: Could not insert data");
            e.printStackTrace();
            return;
        }
    }
    public void getData() {
        Connection conn = null;
        try {
            conn = this.getConnection();
        } catch (SQLException e) {
            System.out.println("ERROR: Could not connect to the database");
            e.printStackTrace();
            return;
        }


        try  {
            Statement statement = conn.createStatement();
            ResultSet productResult = statement.executeQuery(
                    "SELECT * FROM product");
            System.out.println("code | description | price " );
            while (productResult.next()) {
                String code = productResult.getString("code");
                String description = productResult.getString("description");
                String price = productResult.getString("price");
                System.out.println(code + " " + description + " " + price);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public void findByCode(String code) {
        Connection conn = null;
        try {
            conn = this.getConnection();
        } catch (SQLException e) {
            System.out.println("ERROR: Could not connect to the database");
            e.printStackTrace();
            return;
        }
        try  {
            Statement statement = conn.createStatement();
            ResultSet productResult = statement.executeQuery(
                    "SELECT * FROM product WHERE code = '"+ code+"'");
            System.out.println("code | description | price " );
            while (productResult.next()) {
                String description = productResult.getString("description");
                String price = productResult.getString("price");
                System.out.println(code + " | " + description + " | " + price);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
