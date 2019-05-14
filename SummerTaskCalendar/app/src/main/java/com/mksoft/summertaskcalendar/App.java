package com.mksoft.summertaskcalendar;

import android.app.Activity;
import android.app.Application;
import android.content.Context;


import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;


public class App extends Application  {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

    }









}
