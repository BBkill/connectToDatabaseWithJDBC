import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    /**
     * Connect to the DB and do some stuff
     */
    public static void main(String[] args) {
        DBDemo app = new DBDemo();
        Scanner input = new Scanner(System.in);
        while (true) {
            int t =  Integer.parseInt(input.nextLine());
            //create table
            if(t==1) {
                app.createTable();
            }
            //insert data
            else if(t==2) {
                // input
                String code= input.nextLine();
                String description  = input.nextLine();
                String price = input.nextLine();
                app.insertData(code,description,price);
            }
            //get all data
            else if (t==3) {

                app.getData();
            }
            //find data by code
            else if (t==4) {
                String code= input.nextLine();
                app.findByCode(code);
            }
            // stop
            else if (t == 0) {
                return;
            }
        }
    }
}
