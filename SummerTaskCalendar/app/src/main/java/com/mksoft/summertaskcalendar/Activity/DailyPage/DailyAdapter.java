package com.mksoft.summertaskcalendar.Activity.DailyPage;

import com.mksoft.summertaskcalendar.Repo.Data.MemoData;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class DailyAdapter extends FragmentPagerAdapter {
    private ArrayList<DailyFragment> fragments = new ArrayList<>();

    // 필수 생성자
    DailyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    void addItem(DailyFragment fragment) {
        fragments.add(fragment);
    }



}
