package com.mksoft.summertaskcalendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import androidx.appcompat.app.AppCompatActivity;

public class DailyActivity extends AppCompatActivity {
    TextView dailyHead;
    String sYear;
    String sMonth;
    String sDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_activity);
        init();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //ActionBar 메뉴 클릭에 대한 이벤트 처리
        int id = item.getItemId();
        Intent intent;
        switch (id){
            case R.id.MonthlyButton:
                intent = new Intent(getApplicationContext(), MonthlyActivity.class);
                finish();
                startActivity(intent);
                break;
            case R.id.WeeklyButton:
                intent = new Intent(getApplicationContext(), WeeklyActivity.class);
                finish();
                startActivity(intent);

                break;
            case R.id.DailyButton:
                break;
            case R.id.WriteButton:
                intent = new Intent(getApplicationContext(), WriteMemoActivity.class);
                finish();
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void init(){
        dailyHead = findViewById(R.id.dailyHead);
        if(getIntent().getBundleExtra("BUNDLE") != null){

        }
    }
}
