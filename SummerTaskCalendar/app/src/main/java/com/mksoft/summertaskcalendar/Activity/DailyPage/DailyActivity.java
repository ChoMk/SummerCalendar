package com.mksoft.summertaskcalendar.Activity.DailyPage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mksoft.summertaskcalendar.Activity.MonthlyPage.MonthlyActivity;
import com.mksoft.summertaskcalendar.Activity.WeeklyPage.WeeklyActivity;
import com.mksoft.summertaskcalendar.Activity.WritePage.WriteMemoActivity;
import com.mksoft.summertaskcalendar.R;
import com.mksoft.summertaskcalendar.Repo.Data.MemoData;
import com.mksoft.summertaskcalendar.Repo.Data.OptionData;
import com.mksoft.summertaskcalendar.Repo.MemoReposityDB;
import com.mksoft.summertaskcalendar.ViewModel.MemoViewModel;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class DailyActivity extends AppCompatActivity implements HasSupportFragmentInjector {

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
    ViewPager viewPager;
    DailyAdapter dailyAdapter;
    List<MemoData> receiveData;

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
            Log.d("daily hi", "hi");
            receiveData = memoDataLiveData;
            dailyAdapter = new DailyAdapter(getSupportFragmentManager());

            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_MONTH, -30);
            viewPager.setAdapter(dailyAdapter);
            for (int i = 0; i < 60; i++) {
                DailyFragment dailyFragment = new DailyFragment();
                Bundle bundle = new Bundle();
                int nYear;
                int nMonth;
                int nDay;

                nYear = cal.get(Calendar.YEAR);
                nMonth = cal.get(Calendar.MONTH) + 1;
                nDay = cal.get(Calendar.DAY_OF_MONTH);
                sYear = String.valueOf(nYear).substring(2,4);
                if(nMonth<10){
                    sMonth = "0"+String.valueOf(nMonth);

                }else{
                    sMonth = String.valueOf(nMonth);

                }
                if(nDay<10){
                    sDay = "0"+String.valueOf(nDay);

                }else{
                    sDay = String.valueOf(nDay);

                }
                if(receiveData != null)
                    Log.d("receiveData", "notnull");
                bundle.putString("date", sYear+"/"+sMonth+"/"+sDay);
                bundle.putSerializable("memo", (Serializable) receiveData);
                dailyFragment.setArguments(bundle);
                dailyAdapter.addItem(dailyFragment);
                cal.add(Calendar.DAY_OF_MONTH, 1);
            }
            dailyAdapter.notifyDataSetChanged();
            viewPager.setCurrentItem(30);
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
        /*if(getIntent().getBundleExtra("BUNDLE") != null){

        }*/
    viewPager = findViewById(R.id.viewPager);

    }

}
