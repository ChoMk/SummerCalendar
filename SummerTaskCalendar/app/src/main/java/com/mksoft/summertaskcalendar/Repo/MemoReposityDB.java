package com.mksoft.summertaskcalendar.Repo;


import android.os.AsyncTask;


import com.mksoft.summertaskcalendar.Repo.Data.MemoData;
import com.mksoft.summertaskcalendar.Repo.Data.OptionData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;

@Singleton
public class MemoReposityDB {
    private MemoDataDao memoDataDao;
    private OptionDataDao optionDataDao;
    private LiveData<List<MemoData>> memoDataList;

    private List<MemoData> staticMemoDataList;
    private OptionData optionData;

    @Inject
    public MemoReposityDB(MemoDataDao memoDataDao, OptionDataDao optionDataDao){
        this.memoDataDao = memoDataDao;
        this.optionDataDao = optionDataDao;
        memoDataList = memoDataDao.getAll();


    }
    public LiveData<List<MemoData>> getMemoDataList(){

        return memoDataList;
    }

    public List<MemoData> getStaticMemoDataList(){
        try {
            staticMemoDataList = new getAllAsyncTask(memoDataDao).execute().get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return staticMemoDataList;

    }

    private static class getAllAsyncTask extends AsyncTask<Void, Void, List<MemoData>>{
        private MemoDataDao asyncUserDao;


        getAllAsyncTask(MemoDataDao dao){
            asyncUserDao = dao;
        }

        @Override
        protected List<MemoData> doInBackground(Void... voids) {


            return asyncUserDao.getStaticAll();
        }
    }

    public void insertMemo(MemoData memoData){
        new insertAsyncTask(memoDataDao).execute(memoData);
    }

    public void deleteMemo(MemoData memoData){
        new deleteAsyncTask(memoDataDao).execute(memoData);
    }

    private static class deleteAsyncTask extends AsyncTask<MemoData, Void, Void>{
        private MemoDataDao asyncUserDao;
        deleteAsyncTask(MemoDataDao dao){
            asyncUserDao = dao;
        }

        @Override
        protected Void doInBackground(MemoData... memoData) {
            asyncUserDao.delete(memoData[0]);
            return null;
        }
    }
    private static class insertAsyncTask extends AsyncTask<MemoData, Void, Void>{
        private MemoDataDao asyncUserDao;
        insertAsyncTask(MemoDataDao dao){
            asyncUserDao = dao;
        }

        @Override
        protected Void doInBackground(MemoData... memoData) {
            asyncUserDao.insertMemo(memoData[0]);
            return null;
        }
    }

    public void insertOption(OptionData optionData){
        new insertAsyncTaskOption(optionDataDao).execute(optionData);
    }
    private static class insertAsyncTaskOption extends AsyncTask<OptionData, Void, Void>{
        private OptionDataDao asyncUserDao;
        insertAsyncTaskOption(OptionDataDao dao){
            asyncUserDao = dao;
        }

        @Override
        protected Void doInBackground(OptionData... optionData) {
            asyncUserDao.insertOPtion(optionData[0]);
            return null;
        }
    }
    public OptionData getOptionData(){
        try {
            this.optionData = new getOptionDataAsyncTask(optionDataDao).execute().get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return optionData;

    }

    private static class getOptionDataAsyncTask extends AsyncTask<Void, Void, OptionData>{
        private OptionDataDao asyncUserDao;

        getOptionDataAsyncTask(OptionDataDao dao){
            asyncUserDao = dao;
        }

        @Override
        protected OptionData doInBackground(Void... voids) {


            return asyncUserDao.getOptionData();
        }
    }

}
