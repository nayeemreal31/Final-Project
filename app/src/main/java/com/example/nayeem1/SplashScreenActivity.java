package com.example.nayeem1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashScreenActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        progressBar=(ProgressBar) findViewById(R.id.progressbar);

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                dowork();
                startmainscreen();
            }
        });
        thread.start();


    }
    public void dowork() {

        for (progress = 20; progress <= 100; progress = progress+ 20) {
            try {
                Thread.sleep(800);
                progressBar.setProgress(progress);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

  public void startmainscreen(){
        Intent intent=new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
  }

}