package com.mksoft.summertaskcalendar.ViewModel;

import android.util.Log;


import com.mksoft.summertaskcalendar.Repo.Data.MemoData;
import com.mksoft.summertaskcalendar.Repo.MemoReposityDB;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


@Singleton
public class MemoViewModel extends ViewModel {
    private LiveData<List<MemoData>> memoDataLiveData;
    private MemoReposityDB memoReposityDB;
    @Inject
    public MemoViewModel(MemoReposityDB memoReposityDB){
        Log.d("testViewModel", "make it !");
        this.memoReposityDB = memoReposityDB;
        init();
    }

    public void init(){
        if(this.memoDataLiveData != null){
            return;
        }


        memoDataLiveData = memoReposityDB.getMemoDataList();

    }
    public LiveData<List<MemoData>> getMemoDataLiveData(){
        return this.memoDataLiveData;
    }

}
