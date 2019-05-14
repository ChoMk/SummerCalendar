package com.mksoft.summertaskcalendar.Activity.WritePage;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mksoft.summertaskcalendar.Activity.MainActivity;
import com.mksoft.summertaskcalendar.OtherMethod.HideKeyboard;
import com.mksoft.summertaskcalendar.R;

import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

public class WriteMemoActivity extends AppCompatActivity {
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
        init();
        clickHideKeyboard();
        hideKeyboard();
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
                }
                else { saveData(); }
            }
        });

    }


    public void saveData(){

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
