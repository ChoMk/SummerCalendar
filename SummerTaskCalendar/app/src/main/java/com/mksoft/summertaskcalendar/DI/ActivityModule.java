package com.mksoft.summertaskcalendar.DI;



import com.mksoft.summertaskcalendar.Activity.DailyPage.DailyActivity;
import com.mksoft.summertaskcalendar.Activity.MainActivity;
import com.mksoft.summertaskcalendar.Activity.MonthlyPage.MonthlyActivity;
import com.mksoft.summertaskcalendar.Activity.WeeklyPage.WeeklyActivity;
import com.mksoft.summertaskcalendar.Activity.WritePage.WriteMemoActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Philippe on 02/03/2018.
 */


@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector
    abstract MonthlyActivity contributeMonthlyActivity();

    @ContributesAndroidInjector
    abstract WeeklyActivity contributeWeeklyActivity();

    @ContributesAndroidInjector
    abstract DailyActivity contributeDailyActivity();

    @ContributesAndroidInjector
    abstract WriteMemoActivity contributeWriteMemoActivity();





}
