package com.mksoft.summertaskcalendar.Repo;



import com.mksoft.summertaskcalendar.Repo.Data.OptionData;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface OptionDataDao {
    @Query("SELECT * FROM option_data WHERE id = 0")
    OptionData getOptionData();


    @Insert(onConflict = REPLACE)
    void insertOPtion(OptionData optionData);

}
