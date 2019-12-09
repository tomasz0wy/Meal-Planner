package com.tkkd.mealplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private int i = 0;

    String[] t1;
    String[] t2;
    String[] t3;
    String[] t4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MealPlannerHelper plannerHelper = new MealPlannerHelper(this);
        SQLiteDatabase sqLiteDatabase = plannerHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(MealPlannerHelper.AT_HOME, new String[]{"ingredients_id","quantity"},
                null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            int ingId = cursor.getInt(0);
            int quantity = cursor.getInt(1);

            Cursor cursor2 = sqLiteDatabase.query(MealPlannerHelper.INGREDIENTS, new String[]{"name","measures_id"},
                    "_id = ?", new String[]{Integer.toString(ingId)},null,null,null,null);
            if(cursor2.moveToFirst()){
                String ingName = cursor2.getString(0);
                int mesId = cursor2.getInt(1);

                Cursor cursor3 = sqLiteDatabase.query(MealPlannerHelper.MEASURES, new String[]{"measure_name"},
                        "_id = ?", new String[]{Integer.toString(mesId)},null,null,null,null);
                if(cursor3.moveToFirst()){
                    String mesName = cursor3.getString(0);
                }
            }
            while(i != 0){

            }
        }
        cursor.close();
    }
}
