package com.tkkd.mealplanner.Database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;

import com.tkkd.mealplanner.Database.Entities.Measure;

@Dao
public interface MeasureDAO {

    @Insert
    public void insertMeasure(Measure... measures);
}
