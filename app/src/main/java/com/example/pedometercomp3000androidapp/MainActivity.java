package com.example.pedometercomp3000androidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    ConnectionClass connectionClass;
    Connection con;
    ResultSet rs;
    String name,str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectionClass = new ConnectionClass();
        connect();
        //thisnwill get the step data and then display it
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