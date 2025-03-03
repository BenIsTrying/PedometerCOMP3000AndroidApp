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
import java.util.GregorianCalendar;
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


        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);

        TextView txtListDay = findViewById(R.id.textView3);
        txtListDay.setText("Day of the month: "+day);






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

        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);


        TextView stepText = findViewById(R.id.steptxt);
        TextView weightText = findViewById(R.id.weighttxt);
        TextView goalStepText = findViewById(R.id.goalSteptxt);
        TextView goalWeightText = findViewById(R.id.goalWeighttxt);

        String steps = stepText.getText().toString();
        int num1 = Integer.parseInt(steps);

        String weight = weightText.getText().toString();
        int num2 = Integer.parseInt(weight);

        String stepGoals = goalStepText.getText().toString();
        int num3 = Integer.parseInt(stepGoals);

        String weightGoals = goalWeightText.getText().toString();
        int num4 = Integer.parseInt(weightGoals);



        //this is where the update function will be used

        //this is where the update function will be used
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                con = connectionClass.CONN();
                String sqlUpdate1 = "update steps set Step_Day_"+day+"="+num1;
                Statement st1 = (Statement) con.createStatement();
                boolean rs1 = st1.execute(sqlUpdate1);

            }catch(Exception e) {
                Log.e("error", e.getMessage());
            }
            runOnUiThread(()->{
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            });
        });
        ExecutorService executorService2 = Executors.newSingleThreadExecutor();
        executorService2.execute(() -> {
            try {
                con = connectionClass.CONN();
                String sqlUpdate2 = "update weight set Weight_Day_"+day+"="+num2;
                Statement st2 = (Statement) con.createStatement();
                boolean rs2 = st2.execute(sqlUpdate2);

            }catch(Exception e) {
                Log.e("error", e.getMessage());
            }
            runOnUiThread(()->{
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            });
        });
        ExecutorService executorService3 = Executors.newSingleThreadExecutor();
        executorService3.execute(() -> {
            try {
                con = connectionClass.CONN();
                String sqlUpdate3 = "update target set StepTarget ="+num3;
                Statement st3 = (Statement) con.createStatement();
                boolean rs3 = st3.execute(sqlUpdate3);

            }catch(Exception e) {
                Log.e("error", e.getMessage());
            }
            runOnUiThread(()->{
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            });
        });
        ExecutorService executorService4 = Executors.newSingleThreadExecutor();
        executorService4.execute(() -> {
            try {
                con = connectionClass.CONN();
                String sqlUpdate4 = "update target set WeightTarget ="+num4;
                Statement st4 = (Statement) con.createStatement();
                boolean rs4 = st4.execute(sqlUpdate4);

            }catch(Exception e) {
                Log.e("error", e.getMessage());
            }
            runOnUiThread(()->{
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            });
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