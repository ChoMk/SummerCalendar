package com.mksoft.summertaskcalendar.Repo.Data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "option_data")
public class OptionData {
    public OptionData(){

    }
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int id = 0;


    @ColumnInfo(name = "last_state")
    private int lastState;//마지막 종료 페이지 저장

    public void setId(@NonNull int id) {
        this.id = id;
    }
    @NonNull
    public int getId() {
        return id;
    }

    public int getLastState() {
        return lastState;
    }

    public void setLastState(int lastState) {
        this.lastState = lastState;
    }
}
