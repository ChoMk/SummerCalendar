package com.mksoft.summertaskcalendar.Repo.Data;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "memo_data_table")
public class MemoData implements Serializable {

    public MemoData(){}


    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id = 0;


    @ColumnInfo(name = "schedule_date")
    private String scheduleDate;
    //YY/MM/DD
    @ColumnInfo(name = "schedule_year")
    private String scheduleYear;
    //YY
    @ColumnInfo(name = "schedule_month")
    private String scheduleMonth;
    //MM
    @ColumnInfo(name = "schedule_day")
    private String scheduleDay;
    //DD
    @ColumnInfo(name = "schedule_memo")
    private String scheduleMemo;
    //메모 내용
    public int getId() {
        return id;
    }
    public void setId(@NonNull int id) {
        this.id = id;
    }


    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getScheduleYear() {
        return scheduleYear;
    }

    public void setScheduleYear(String scheduleYear) {
        this.scheduleYear = scheduleYear;
    }

    public String getScheduleMonth() {
        return scheduleMonth;
    }

    public void setScheduleMonth(String scheduleMonth) {
        this.scheduleMonth = scheduleMonth;
    }

    public String getScheduleDay() {
        return scheduleDay;
    }

    public void setScheduleDay(String scheduleDay) {
        this.scheduleDay = scheduleDay;
    }

    public String getScheduleMemo() {
        return scheduleMemo;
    }

    public void setScheduleMemo(String scheduleMemo) {
        this.scheduleMemo = scheduleMemo;
    }
}
