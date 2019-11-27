package com.tkkd.mealplanner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MealPlannerHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "meal_planner";
    private static final int DB_VERSION = 1;

    MealPlannerHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
