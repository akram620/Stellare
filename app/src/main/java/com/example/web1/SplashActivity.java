package com.example.web1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SplashActivity extends AppCompatActivity {

    Button start;
    ProgressBar progressBar;

    private Document doc;
    private Thread secondThread;
    private Runnable runnable;

    private String resultURL = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        start = findViewById(R.id.start);
        progressBar = findViewById(R.id.progressBar);

        if (checkConnection()){
            init();
            try {
                check();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(this, "No internet connection!", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            start.setText("Reload");
            start.setVisibility(View.VISIBLE);

        }



        start.setOnClickListener(v -> {
            if(start.getText().equals("Start")){
                Intent intent = new Intent(this, Game.class);
                start.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                startActivity(intent);
                finish();
            }else {
                if (checkConnection()){
                    init();
                    try {
                        check();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(this, "No internet connection!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    start.setText("Reload");
                    start.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private double limit = 0;
    private void check() throws InterruptedException {
        if (resultURL == null) {
            if (limit < 7) {
                TimeUnit.MILLISECONDS.sleep(500);
                limit += 0.5;
                Log.d("my", "loading url");
                check();
            }else {
                Toast.makeText(this, "Time out!", Toast.LENGTH_SHORT).show();
                limit = 0;
                progressBar.setVisibility(View.GONE);
                start.setVisibility(View.VISIBLE);
                start.setText("Reload");
            }

        }else if (resultURL.isEmpty()) {
            Toast.makeText(this, "URL not found!", Toast.LENGTH_SHORT).show();
            limit = 0;
            progressBar.setVisibility(View.GONE);
            start.setVisibility(View.VISIBLE);
            start.setText("Start");
        } else {
            progressBar.setVisibility(View.GONE);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("baseURL", resultURL);
            startActivity(intent);
            finish();
        }
    }

    private void init(){
        runnable = new Runnable() {
            @Override
            public void run() {
                getWebUrl();
            }
        };
        secondThread = new Thread(runnable);
        secondThread.start();
    }
    private void getWebUrl(){
        try {
            doc = Jsoup.connect("https://fairslot.online/9ghYNtT3").get();
            Log.d("my", doc.body().text() + "");

            resultURL = doc.body().text().trim();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileNetwork = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(wifi.isConnected() || mobileNetwork.isConnected()){
            return true;
        }else {
            return false;
        }
    }



}