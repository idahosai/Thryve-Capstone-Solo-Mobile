package com.myappcompany.thea.mobileappthryve;

import android.content.Context;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectData {
    Connection connect;
    String ConnectionResult = "";
    Boolean isSuccessful = false;

    public List<Map<String,String>> getData() {
        List<Map<String, String>> data = null;
        data = new ArrayList<Map<String, String>>();

        Context context = null;
        System.out.println("CHECKPOINT 1");

        try {
            JdbcPostgresqlConnection instance = new JdbcPostgresqlConnection();
            connect = instance.connectDatabase();        // Connect to database

            if (connect == null) {
                ConnectionResult = "CHECK YOUR INTERNET ACCESS!";
                System.out.println("CHECKPOINT 5: " + ConnectionResult);

            } else {
                // Change below query according to your own database.
                String query = "SELECT * FROM public.\"Career-Staff\"";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    Map<String, String> datanum = new HashMap<String, String>();
                    datanum.put("id", rs.getString("id"));
                    datanum.put("FirstName", rs.getString("staff_fname"));
                    datanum.put("LastName", rs.getString("staff_lname"));
                    datanum.put("Title", rs.getString("title"));
                    datanum.put("EmailAddress", rs.getString("email"));
                    datanum.put("PhoneNumber", rs.getString("phone_number"));
                    datanum.put("PhoneExtension", rs.getString("phone_extension"));
                    data.add(datanum);
                }


                ConnectionResult = "SUCCESSFUL";
                System.out.println("CHECKPOINT 6: " + ConnectionResult);
                isSuccessful = true;
                connect.close();
            }
        } catch (Exception ex) {
            isSuccessful = false;
            ConnectionResult = ex.getMessage();
        }

        return data;
    }
}
