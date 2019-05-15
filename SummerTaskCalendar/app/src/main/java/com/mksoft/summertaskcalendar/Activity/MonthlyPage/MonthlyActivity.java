package com.mksoft.summertaskcalendar.Activity.MonthlyPage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mksoft.summertaskcalendar.Activity.DailyPage.DailyActivity;
import com.mksoft.summertaskcalendar.Activity.WeeklyPage.WeeklyActivity;
import com.mksoft.summertaskcalendar.Activity.WritePage.WriteMemoActivity;
import com.mksoft.summertaskcalendar.App;
import com.mksoft.summertaskcalendar.DayDecorator.EventDecorator;
import com.mksoft.summertaskcalendar.DayDecorator.TodayDecorator;
import com.mksoft.summertaskcalendar.R;
import com.mksoft.summertaskcalendar.Repo.Data.OptionData;
import com.mksoft.summertaskcalendar.Repo.MemoReposityDB;
import com.mksoft.summertaskcalendar.ViewModel.MemoViewModel;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Calendar;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MonthlyActivity extends AppCompatActivity  implements HasSupportFragmentInjector {

    MaterialCalendarView materialCalendarView;
    private final TodayDecorator todayDecorator = new TodayDecorator();
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    @Inject
    MemoReposityDB memoReposityDB;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private MemoViewModel memoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monthly_activity);

        this.configureDagger();
        configureViewModel();

        init();
        startPageSelect();
        App.startFalg = 1;
    }
    @Override
    protected void onResume() {
        super.onResume();
        OptionData optionData = new OptionData();
        optionData.setId(0);
        optionData.setLastState(1);
        memoReposityDB.insertOption(optionData);
    }
    private void configureViewModel(){
        memoViewModel = ViewModelProviders.of(this, viewModelFactory).get(MemoViewModel.class);
        memoViewModel.init();

        memoViewModel.getMemoDataLiveData().observe(this, memoDataLiveData -> {
            Calendar calendar = Calendar.getInstance();
            ArrayList<CalendarDay> dates = new ArrayList<>();
            for(int i =0; i<memoDataLiveData.size(); i++){
                int year = Integer.parseInt("20"+memoDataLiveData.get(i).getScheduleYear());
                int month = Integer.parseInt(memoDataLiveData.get(i).getScheduleMonth());
                int dayy = Integer.parseInt(memoDataLiveData.get(i).getScheduleDay());
                calendar.set(year,month-1,dayy);
                CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);
            }
            materialCalendarView.addDecorator(new EventDecorator(Color.RED, dates, this));
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

                break;
            case R.id.WeeklyButton:
                intent = new Intent(getApplicationContext(), WeeklyActivity.class);
                startActivity(intent);
                break;
            case R.id.DailyButton:
                intent = new Intent(getApplicationContext(), DailyActivity.class);
                startActivity(intent);
                break;
            case R.id.WriteButton:
                intent = new Intent(getApplicationContext(), WriteMemoActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void init(){
        materialCalendarView = (MaterialCalendarView)findViewById(R.id.MonthlycalendarView);

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 0, 1)) // 달력의 시작
                .setMaximumDate(CalendarDay.from(2030, 11, 31)) // 달력의 끝
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.addDecorators(
                todayDecorator);
        //오늘 날짜 표시



    }
    private void startPageSelect(){
        if(App.startFalg != 0)
            return;
        Intent intent;
        OptionData optionData = memoReposityDB.getOptionData();
        if(optionData != null && optionData.getLastState() == 2){
            intent = new Intent(this, WeeklyActivity.class);
            startActivity(intent);
        }else if(optionData != null &&optionData.getLastState() == 3){
            intent = new Intent(this, DailyActivity.class);
            startActivity(intent);
        }
        //페이지가 넘어갈 때 마다 DB에 마지막 페이지 상태를 저장하자
        //그걸 불러와서 상태에 맞는 엑티비티 띄우자



    }

}
