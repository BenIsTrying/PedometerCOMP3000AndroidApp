package com.example.pedometercomp3000androidapp;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

public class ConnectionClass {
    protected static String db = "COMP3000_BGeorge";

    protected static String ip = "proj-mysql.uopnet.plymouth.ac.uk";

    protected static String port = "3306";

    protected static String username = "COMP3000_BGeorge";

    protected static String password = "ZgdF769+";

    public Connection CONN(){
        Connection conn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String connectionString = "jdbc:mysql://"+ip+":"+port+"/"+db;
            conn = DriverManager.getConnection(connectionString,username,password);
        }catch(Exception e){
            Log.e("Error", Objects.requireNonNull(e.getMessage()));
        }
        return conn;
    }
}
