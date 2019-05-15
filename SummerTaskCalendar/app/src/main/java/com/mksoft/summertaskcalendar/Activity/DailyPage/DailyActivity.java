package com.mksoft.summertaskcalendar.Activity.DailyPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mksoft.summertaskcalendar.Activity.MonthlyPage.MonthlyActivity;
import com.mksoft.summertaskcalendar.Activity.WeeklyPage.WeeklyActivity;
import com.mksoft.summertaskcalendar.Activity.WritePage.WriteMemoActivity;
import com.mksoft.summertaskcalendar.R;
import com.mksoft.summertaskcalendar.Repo.Data.OptionData;
import com.mksoft.summertaskcalendar.Repo.MemoReposityDB;
import com.mksoft.summertaskcalendar.ViewModel.MemoViewModel;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class DailyActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    TextView dailyHead;
    String sYear;
    String sMonth;
    String sDay;
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
        setContentView(R.layout.daily_activity);
        this.configureDagger();
        configureViewModel();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        OptionData optionData = new OptionData();
        optionData.setId(0);
        optionData.setLastState(3);
        memoReposityDB.insertOption(optionData);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
    private void configureDagger(){
        AndroidInjection.inject(this);
    }
    private void configureViewModel(){
        memoViewModel = ViewModelProviders.of(this, viewModelFactory).get(MemoViewModel.class);
        memoViewModel.init();

        memoViewModel.getMemoDataLiveData().observe(this, memoDataLiveData -> {
            //데이터 변경 처리
        });
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
