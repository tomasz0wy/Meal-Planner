package com.tkkd.mealplanner.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.tkkd.mealplanner.Database.Entities.Measure;
import java.util.List;

@Dao
public interface MeasureDAO {

    @Insert
    void insertMeasure(Measure... measures);

    @Query("SELECT * FROM measures")
    List<Measure> getMeasures();

    @Query("SELECT * FROM measures WHERE measure_name = :mesName")
    Measure getOneMeasure(String mesName);
}
