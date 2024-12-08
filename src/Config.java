import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Config {
    public static void main(String[]args) throws ClassNotFoundException{

        String url ="jdbc:mysql://localhost:3306/library_management";
        String username ="root";
        String password ="JA08@jaya";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("successfully");

        } catch (ClassNotFoundException e) {
            System.out.println();
        }

        try{
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("established");
        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("failed");
        }
    }
}
