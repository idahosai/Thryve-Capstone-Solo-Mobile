package com.myappcompany.thea.mobileappthryve;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcPostgresqlConnection {

    private final String dbUrl = "jdbc:postgresql://database-1.cbla1ovjyisy.us-east-2.rds.amazonaws.com:5432/thryvedatabase2";
    private final String dbUsername = "postgres";
    private final String dbPassword = "Thryve2020!";

    public Connection connectDatabase() {
        Connection conn = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        System.out.println("CHECKPOINT 2");
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            System.out.println("CHECKPOINT 3");
        } catch (SQLException e) {
            System.out.println("CHECKPOINT 4: " + e.toString());
        } catch (ClassNotFoundException e) {
            System.out.println("CHECKPOINT 7: " + e.toString());
        }

        return conn;
        /*Connection conn1 = null;

        Context context = null;

        try {
            String dbURL = "jdbc:postgresql://thryve-database-2020.clkzzm3gdrpr.us-east-2.rds.amazonaws.com:5432/thryvedatabase";
            String user = "thryve2020";
            String pass = "Thryve2020!";

            conn1 = DriverManager.getConnection(dbURL, user, pass);
            if (conn1 != null) {
                System.out.println("Connected to database #1");

                Toast.makeText(context, "Connected to database #1", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, "Connection Error!", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Toast.makeText(context, "EX ERROR 1", Toast.LENGTH_SHORT).show();
        } finally {

            try {
                if (conn1 != null && !conn1.isClosed()) {
                    conn1.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                Toast.makeText(context, "EX ERROR 2", Toast.LENGTH_SHORT).show();
            }
        }

        return conn1;*/
    }
}
