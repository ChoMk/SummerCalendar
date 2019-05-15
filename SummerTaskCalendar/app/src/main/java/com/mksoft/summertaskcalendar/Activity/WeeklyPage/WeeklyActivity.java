package com.mksoft.summertaskcalendar.Activity.WeeklyPage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mksoft.summertaskcalendar.Activity.DailyPage.DailyActivity;
import com.mksoft.summertaskcalendar.Activity.MonthlyPage.MonthlyActivity;
import com.mksoft.summertaskcalendar.Activity.WritePage.WriteMemoActivity;
import com.mksoft.summertaskcalendar.DayDecorator.EventDecorator;
import com.mksoft.summertaskcalendar.DayDecorator.TodayDecorator;
import com.mksoft.summertaskcalendar.R;
import com.mksoft.summertaskcalendar.Repo.Data.MemoData;
import com.mksoft.summertaskcalendar.Repo.Data.OptionData;
import com.mksoft.summertaskcalendar.Repo.MemoReposityDB;
import com.mksoft.summertaskcalendar.ViewModel.MemoViewModel;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class WeeklyActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    MaterialCalendarView materialCalendarView;
    private final TodayDecorator todayDecorator = new TodayDecorator();
    private MemoViewModel memoViewModel;
    RecyclerView weeklyRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    WeeklyAdapter weeklyAdapter;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    @Inject
    MemoReposityDB memoReposityDB;
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.configureDagger();
        setContentView(R.layout.weekly_activity);
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
    }//마지막 상태 저장
    private void configureViewModel(){
        memoViewModel = ViewModelProviders.of(this, viewModelFactory).get(MemoViewModel.class);
        memoViewModel.init();

        memoViewModel.getMemoDataLiveData().observe(this, memoDataLiveData -> {
            //데이터 변경 처리
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
        weeklyRecyclerView = findViewById(R.id.memoListRecyclerView);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        weeklyAdapter = new WeeklyAdapter(getApplicationContext());
        weeklyRecyclerView.setLayoutManager(layoutManager);
        weeklyRecyclerView.setAdapter(weeklyAdapter);
        Calendar calendarDay = materialCalendarView.getCurrentDate().getCalendar();
        rAdapter(calendarDay);

        materialCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                Calendar calendarDay = date.getCalendar();
                rAdapter(calendarDay);
            }
        });



    }



    public void rAdapter(Calendar s){

        if(memoReposityDB == null){
            Log.d("0724","hi");

        }
        List<MemoData> memoData = memoReposityDB.getStaticMemoDataList();
        if(memoData == null)
            return;
        Calendar calendarDay = s;
        int startYear = calendarDay.get(Calendar.YEAR);
        int startMonth = calendarDay.get(Calendar.MONTH)+1;
        int startDay = calendarDay.get(Calendar.DAY_OF_MONTH);
        calendarDay.add(Calendar.DATE, 6);

        int endYear = calendarDay.get(Calendar.YEAR);
        int endMonth = calendarDay.get(Calendar.MONTH)+1;
        int endDay = calendarDay.get(Calendar.DAY_OF_MONTH);

        Date startDate = null;
        Date endDate = null;

        try {
            startDate=format.parse(String.valueOf(startYear)+"-"+String.valueOf(startMonth)+"-"+String.valueOf(startDay));
            endDate = format.parse(String.valueOf(endYear)+"-"+String.valueOf(endMonth)+"-"+String.valueOf(endDay));

        } catch (ParseException e) {
            e.printStackTrace();
        }


        //주의 달력에서 시작과 끝 저장
        List<MemoData> temp = new ArrayList<>();
        for(int i =0; i<memoData.size(); i++){
            Date itemDate = null;
            try {
                itemDate = format.parse("20"+memoData.get(i).getScheduleYear()+"-"+memoData.get(i).getScheduleMonth()+"-"+memoData.get(i).getScheduleDay());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.d("test05010", itemDate.toString()+"  "+startDate.toString()+"  "+endDate.toString());
            int compareSmall = itemDate.compareTo(startDate);
            int compareLarge = endDate.compareTo(itemDate);
            if(compareLarge>=0 && compareSmall>=0){
                temp.add(memoData.get(i));
            }

        }

        weeklyAdapter.refreshItem(temp);
    }
}
