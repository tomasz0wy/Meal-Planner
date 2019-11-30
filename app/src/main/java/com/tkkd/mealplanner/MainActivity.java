package com.tkkd.mealplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MealPlannerHelper plannerHelper = new MealPlannerHelper(this);
        SQLiteDatabase sqLiteDatabase = plannerHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(MealPlannerHelper.MEASURES,new String[]{"measure_name"},
                null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            TextView textView = findViewById(R.id.testing);
            textView.setText(cursor.getString(0));
        }
        cursor.close();
    }
}
