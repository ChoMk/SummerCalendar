package com.mksoft.summertaskcalendar;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startPageSelect();

    }


    private void startPageSelect(){
        Intent intent;
        //페이지가 넘어갈 때 마다 DB에 마지막 페이지 상태를 저장하자
        //그걸 불러와서 상태에 맞는 엑티비티 띄우자

        intent = new Intent(this, MonthlyActivity.class);
        startActivity(intent);

    }

    //마지막 종료된 엑티비티를 기억했다가 띄워주는 기능 수행
}
