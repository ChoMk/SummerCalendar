package com.mksoft.summertaskcalendar.Splash;

import android.content.Intent;
import android.os.Bundle;

import com.mksoft.summertaskcalendar.Activity.MonthlyPage.MonthlyActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MonthlyActivity.class);
        startActivity(intent);

        finish();
    }
}
