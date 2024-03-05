package Utils;
import java.sql.*;

public class DataBase {
    final String URL = "jdbc:mysql://localhost:3306/tounsifit";
    final String USER="root";
    final String PASS="";
    private Connection connection;
    public static DataBase instance;

    public DataBase(){
        try {
            connection = DriverManager.getConnection(URL,USER,PASS);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public static DataBase getInstance() {
        if (instance == null)
            instance = new DataBase();
        return instance;

    }
    public Connection getConnection(){
        return connection;
    }


}

