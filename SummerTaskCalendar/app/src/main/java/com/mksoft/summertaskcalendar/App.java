package com.mksoft.summertaskcalendar;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;


import com.mksoft.summertaskcalendar.DI.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;


public class App extends Application implements HasActivityInjector{
    public static Context context;
    public static int startFalg = 0;
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;
    @Inject
    DispatchingAndroidInjector<Service> dispatchingAndroidInjectorService;


    @Override
    public void onCreate() {
        super.onCreate();
        this.initDagger();
        context = getApplicationContext();
    }



    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }


    private void initDagger(){

        DaggerAppComponent.builder().application(this).build().inject(this);
    }










}
