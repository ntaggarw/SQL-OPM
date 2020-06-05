package main;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import static java.lang.System.*;
import java.io.File;

public class Lamain {

    public static String DATABASE_LOCATION = "";

    public static void main(String[] args) {

        Scanner sc = null;
        try {
            sc = new Scanner(new File("/home/naryana/SQL-ORM/database.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        DATABASE_LOCATION = sc.nextLine();
        sc.close();

        try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName("/home/naryana/SQL-ORM/main/mysql-connector-java-8.0.20.jar").newInstance();
        } catch (Exception ex) {
            // handle the error
            ex.printStackTrace();
        }

        Connection myCon = null;
        try {
            myCon = DriverManager.getConnection("jdbc:mysql:" + DATABASE_LOCATION, "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String[] values = new String[]{"Happy", "mad", "sad"};
        out.println(String.join(", ", values));

    }

}