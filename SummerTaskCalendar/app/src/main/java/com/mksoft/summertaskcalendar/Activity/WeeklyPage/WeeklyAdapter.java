package com.mksoft.summertaskcalendar.Activity.WeeklyPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mksoft.summertaskcalendar.Activity.DailyPage.DailyMemoAdapter;
import com.mksoft.summertaskcalendar.R;
import com.mksoft.summertaskcalendar.Repo.Data.MemoData;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WeeklyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView DailyDateView;
        TextView DailyMemoView;


        MyViewHolder(View view){
            super(view);
            DailyDateView = view.findViewById(R.id.DailyDateView);
            DailyMemoView = view.findViewById(R.id.DailyMemoView);
        }
    }
    List<MemoData> items =  Collections.emptyList();
    Context context;
    String startDate;
    String endDate;
    WeeklyAdapter.MyViewHolder myViewHolder;
    public WeeklyAdapter(Context context){
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_memo_item, parent, false);
        return new WeeklyAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        myViewHolder = (WeeklyAdapter.MyViewHolder) holder;
        myViewHolder.DailyDateView.setText(items.get(position).getScheduleDate());
        myViewHolder.DailyMemoView.setText(items.get(position).getScheduleMemo());

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void deleteItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void refreshItem(List<MemoData> items){
        this.items = items;
        notifyDataSetChanged();
    }
    public MemoData getItem(int idx){
        return items.get(idx);
    }
    public List<MemoData> getAllItem(){return items;}

}
