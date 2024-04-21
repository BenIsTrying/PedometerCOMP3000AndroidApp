package com.example.pedometercomp3000androidapp;

import static android.app.SearchManager.QUERY;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    ConnectionClass connectionClass;
    Connection con;
    ResultSet rs;
    String name,str;


    static int getLastDayOfMonthUsingCalendar(int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectionClass = new ConnectionClass();
        connect();

        //get day of the month here



//        SimpleDateFormat sdf = new SimpleDateFormat("'Date\t'dd-MM-yyyy '\t\tand\t\tTime\t'HH:mm:ss z");
//
//        String currentDateAndTime = sdf.format(new Date());

//        time.setText(currentDateAndTime);







        //this will get the step data and then display it
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                con= connectionClass.CONN();
                String query = "SELECT * FROM steps";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs =stmt.executeQuery();
                StringBuilder bStr =new StringBuilder("Today's Steps: ");
                while(rs.next()){
                    bStr.append(rs.getString("Step_Day_01")).append("\n");
                }
                name = bStr.toString();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }

            runOnUiThread(()->{
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                TextView txtList = findViewById(R.id.textView);
                txtList.setText(name);

            });

        });

        //now load weight data
        ExecutorService executorService2 = Executors.newSingleThreadExecutor();
        executorService2.execute(() -> {
            try {
                con= connectionClass.CONN();
                String query = "SELECT * FROM weight";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs =stmt.executeQuery();
                StringBuilder bStr =new StringBuilder("Today's Weight: ");
                while(rs.next()){
                    bStr.append(rs.getString("Weight_Day_01")).append("\n");
                }
                name = bStr.toString();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }

            runOnUiThread(()->{
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                TextView txtList = findViewById(R.id.textView2);
                txtList.setText(name);

            });

        });


    }

    public void btnClick(View view) {

        int day = 10;
        //this is where the update function will be used

//this is where the update function will be used
        ExecutorService executorService2 = Executors.newSingleThreadExecutor();
        executorService2.execute(() -> {
            try {
                con = connectionClass.CONN();
                String sqlUpdate = "update steps set Step_Day_01 =2000";
                Statement st = (Statement) con.createStatement();
                boolean rs = st.execute(sqlUpdate);



            }catch(Exception e) {
                Log.e("error", e.getMessage());

            }


        });




    }

    public void connect(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() ->{
            try{
                con = connectionClass.CONN();
                if(con == null){
                    str="Error in connection with MySQL server";
                }else{
                    str= "Connected with MySQL server";
                }
            }catch (Exception e){
                throw new RuntimeException(e);
            }

            runOnUiThread(()->{
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                Toast.makeText(this,str, Toast.LENGTH_SHORT).show();


            });

        });
    }
}