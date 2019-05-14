package com.mksoft.summertaskcalendar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mksoft.summertaskcalendar.DayDecorator.TodayDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MonthlyActivity extends AppCompatActivity {

    MaterialCalendarView materialCalendarView;
    private final TodayDecorator todayDecorator = new TodayDecorator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monthly_activity);
        init();
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



        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                materialCalendarView.clearSelection();
                //클릭시 년, 월, 일을 번들로 저장하고 Weekly Activity로 넘기자...

                Bundle bundle = new Bundle();

                bundle.putParcelable("CalendarDay", date);
                Intent intent = new Intent(getApplicationContext(), WeeklyActivity.class);
                intent.putExtra("BUNDLE", bundle);
                startActivity(intent);


            }
        });
    }

}
