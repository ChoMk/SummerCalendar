package com.mksoft.summertaskcalendar.Activity.DailyPage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mksoft.summertaskcalendar.R;
import com.mksoft.summertaskcalendar.Repo.Data.MemoData;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DailyFragment extends Fragment {
    TextView dailyHead;
    RecyclerView recyclerView;
    DailyMemoAdapter dailyMemoAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.daily_main_item, container, false);

        dailyHead = view.findViewById(R.id.dailyHead);
        recyclerView = view.findViewById(R.id.memoListSubRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        dailyMemoAdapter =new DailyMemoAdapter(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(dailyMemoAdapter);

        if (getArguments() != null) {
            Bundle args = getArguments();
            dailyHead.setText(args.getString("date"));
            List<MemoData> items = (List<MemoData>) args.getSerializable("memo");
            if(items != null){
                Log.d("oknull","hi");
                List<MemoData> result = new ArrayList<>();
                for(int i =0; i<items.size();i++){
                    Log.d("okif",items.get(i).getScheduleDate()+"  "+dailyHead.getText().toString());
                    if(dailyHead.getText().toString().equals(items.get(i).getScheduleDate())){
                        result.add(items.get(i));

                    }
                }
                dailyMemoAdapter.refreshItem(result);
            }

        }
        return view;
    }

}
