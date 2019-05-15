package com.mksoft.summertaskcalendar.Activity.WeeklyPage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mksoft.summertaskcalendar.Activity.DailyPage.DailyActivity;
import com.mksoft.summertaskcalendar.Activity.MonthlyPage.MonthlyActivity;
import com.mksoft.summertaskcalendar.Activity.WritePage.WriteMemoActivity;
import com.mksoft.summertaskcalendar.DayDecorator.TodayDecorator;
import com.mksoft.summertaskcalendar.R;
import com.mksoft.summertaskcalendar.Repo.Data.OptionData;
import com.mksoft.summertaskcalendar.Repo.MemoReposityDB;
import com.mksoft.summertaskcalendar.ViewModel.MemoViewModel;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class WeeklyActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    MaterialCalendarView materialCalendarView;
    private final TodayDecorator todayDecorator = new TodayDecorator();
    private MemoViewModel memoViewModel;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    @Inject
    MemoReposityDB memoReposityDB;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekly_activity);
        this.configureDagger();
        this.configureViewModel();
        init();
    }
    @Override
    protected void onResume() {
        super.onResume();
        OptionData optionData = new OptionData();
        optionData.setId(0);
        optionData.setLastState(2);
        memoReposityDB.insertOption(optionData);
    }
    private void configureViewModel(){
        memoViewModel = ViewModelProviders.of(this, viewModelFactory).get(MemoViewModel.class);
        memoViewModel.init();

        memoViewModel.getMemoDataLiveData().observe(this, memoDataLiveData -> {
            //데이터 변경 처리
        });
    }
    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
    private void configureDagger(){
        AndroidInjection.inject(this);
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
                break;
            case R.id.DailyButton:
                intent = new Intent(getApplicationContext(), DailyActivity.class);
                finish();
                startActivity(intent);
                break;
            case R.id.WriteButton:
                intent = new Intent(getApplicationContext(), WriteMemoActivity.class);
                finish();
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void init(){
        materialCalendarView = (MaterialCalendarView)findViewById(R.id.WeeklycalendarView);

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 0, 1)) // 달력의 시작
                .setMaximumDate(CalendarDay.from(2030, 11, 31)) // 달력의 끝
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();
        materialCalendarView.addDecorators(todayDecorator);
        //오늘 날짜 표시




    }
}
