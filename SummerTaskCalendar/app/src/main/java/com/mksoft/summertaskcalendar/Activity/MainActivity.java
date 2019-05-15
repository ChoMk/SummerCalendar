package com.mksoft.summertaskcalendar.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.mksoft.summertaskcalendar.Activity.DailyPage.DailyActivity;
import com.mksoft.summertaskcalendar.Activity.MonthlyPage.MonthlyActivity;
import com.mksoft.summertaskcalendar.Activity.WeeklyPage.WeeklyActivity;
import com.mksoft.summertaskcalendar.R;
import com.mksoft.summertaskcalendar.Repo.Data.OptionData;
import com.mksoft.summertaskcalendar.Repo.MemoReposityDB;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    @Inject
    MemoReposityDB memoReposityDB;
    OptionData optionData = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.configureDagger();
        startPageSelect();

    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
    private void configureDagger(){
        AndroidInjection.inject(this);
    }


    private void startPageSelect(){
        Intent intent;
        optionData = memoReposityDB.getOptionData();
        if(optionData == null || optionData.getLastState() == 1){
            intent = new Intent(this, MonthlyActivity.class);
            startActivity(intent);

        }else if(optionData.getLastState() == 2){
            intent = new Intent(this, WeeklyActivity.class);
            startActivity(intent);
        }else if(optionData.getLastState() == 3){
            intent = new Intent(this, DailyActivity.class);
            startActivity(intent);
        }
        //페이지가 넘어갈 때 마다 DB에 마지막 페이지 상태를 저장하자
        //그걸 불러와서 상태에 맞는 엑티비티 띄우자



    }

    //마지막 종료된 엑티비티를 기억했다가 띄워주는 기능 수행
}
