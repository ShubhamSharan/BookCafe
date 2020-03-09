import java.sql.*;

public class DataConnectionJDBC {
    //All SQL Queries
    public void connector() {
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookCafe");
                Statement statement = connection.createStatement();
        ) {


        } catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
        }
    }
}
