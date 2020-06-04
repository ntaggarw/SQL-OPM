package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;
import static java.lang.System.*;

public class Lamain {

    public String DATABASE_LOCATION = "";

    public static void main(String[] args) {

        Scanner sc = new Scanner(new File("main/database.txt"));
        DATABASE_LOCATION = sc.nextLine();
        sc.close();

        try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            // handle the error
        }

        Connection myCon = DriverManager.getConnection("jdbc:mysql:" + DATABASE_LOCATION, "root", "root");


        String[] values = new String[]{"Happy", "mad", "sad"};
        out.println(String.join(", ", values));

    }

}