package com.mksoft.summertaskcalendar.Activity.WritePage;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mksoft.summertaskcalendar.Activity.MonthlyPage.MonthlyActivity;
import com.mksoft.summertaskcalendar.OtherMethod.HideKeyboard;
import com.mksoft.summertaskcalendar.R;
import com.mksoft.summertaskcalendar.Repo.Data.MemoData;
import com.mksoft.summertaskcalendar.Repo.MemoReposityDB;

import java.util.Calendar;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class WriteMemoActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    @Inject
    MemoReposityDB memoReposityDB;
    EditText schedule;
    Button settingDate;
    TextView dateTextView;
    Button submitButton;
    RelativeLayout writeRelativeLayout;

    String sDate;

    final String notChanged = "Select a date... (Click above button)";
    final Calendar cal = Calendar.getInstance();


    String sYear;
    String sMonth;
    String sDay;

    HideKeyboard hideKeyboard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_memo_activity);
        configureDagger();
        init();
        clickHideKeyboard();
        hideKeyboard();
    }
    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
    private void configureDagger(){
        AndroidInjection.inject(this);
    }

    public void init(){
        hideKeyboard = new HideKeyboard(this);
        writeRelativeLayout = findViewById(R.id.writeRelativeLayout);
        schedule = findViewById(R.id.schedule);
        settingDate = findViewById(R.id.settingDate);
        dateTextView = findViewById(R.id.dateTextView);
        submitButton = findViewById(R.id.submitButton);
        final FragmentManager fm = this.getSupportFragmentManager();
        settingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(WriteMemoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        month++;
                        sYear = String.valueOf(year).substring(2,4);
                        if(month<10){
                            sMonth = "0"+String.valueOf(month);
                        }else{
                            sMonth = String.valueOf(month);
                        }

                        if(date<10){
                            sDay = "0"+String.valueOf(date);
                        }else{
                            sDay = String.valueOf(date);
                        }


                        String msg = sYear+"/"+sMonth+"/"+sDay;
                        sDate = msg;
                        dateTextView.setText(sDate);
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

                dialog.show();




            }
        });
        sDate = "Select a date... (Click above button)";
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sDate == notChanged){
                    Toast.makeText(getApplicationContext(), "Set Date!!", Toast.LENGTH_SHORT).show();
                }else if(schedule.getText().toString().length() == 0){
                    Toast.makeText(getApplicationContext(), "Write schedule!!", Toast.LENGTH_SHORT).show();
                }
                else { saveData(); }
            }
        });

    }


    public void saveData(){
        MemoData memoData = new MemoData();
        memoData.setScheduleDate(sDate);
        memoData.setScheduleYear(sYear);
        memoData.setScheduleMonth(sMonth);
        memoData.setScheduleDay(sDay);
        memoData.setScheduleMemo(schedule.getText().toString());
        memoReposityDB.insertMemo(memoData);
        Intent intent = new Intent(getApplicationContext(), MonthlyActivity.class);
        finish();
        startActivity(intent);

    }
    //여기는 텝 메뉴 없이 뒤로가기 누르면 Monthly 페이지로
    private void hideKeyboard(){
        hideKeyboard.hideKeyboard();
    }
    private void clickHideKeyboard(){
        writeRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();

            }
        });
    }
}
