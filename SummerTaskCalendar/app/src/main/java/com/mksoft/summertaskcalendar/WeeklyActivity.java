package com.mksoft.summertaskcalendar;

import android.content.Intent;
import android.os.Bundle;

import com.mksoft.summertaskcalendar.DayDecorator.TodayDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class WeeklyActivity extends AppCompatActivity {
    MaterialCalendarView materialCalendarView;
    private final TodayDecorator todayDecorator = new TodayDecorator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekly_activity);
        init();
    }
    public void init(){
        materialCalendarView = (MaterialCalendarView)findViewById(R.id.WeeklycalendarView);

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 0, 1)) // 달력의 시작
                .setMaximumDate(CalendarDay.from(2030, 11, 31)) // 달력의 끝
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();
        materialCalendarView.setCurrentDate((CalendarDay)getIntent().getBundleExtra("BUNDLE").getParcelable("CalendarDay"));
        materialCalendarView.addDecorators(
                todayDecorator);
        //오늘 날짜 표시



        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int Year = date.getYear();
                int Month = date.getMonth();
                int Day = date.getDay();

                materialCalendarView.clearSelection();
                //클릭시 년, 월, 일을 번들로 저장하고 Weekly Activity로 넘기자...

                Bundle bundle = new Bundle();
                bundle.putInt("Year", Year);
                bundle.putInt("Month", Month);
                bundle.putInt("Day", Day);

                Intent intent = new Intent(getApplicationContext(), DailyActivity.class);
                intent.putExtra("BUNDLE", bundle);
                startActivity(intent);


            }
        });
    }
}
