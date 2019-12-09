package com.tkkd.mealplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MealPlannerHelper extends SQLiteOpenHelper {

    //Database related variables
    private static final String DB_NAME = "meal_planner";
    private static final int DB_VERSION = 1;

    //Tables names
    static final String AT_HOME = "AT_HOME";
    static final String INGREDIENTS = "INGREDIENTS";
    static final String MEASURES = "MEASURE";

    //Class constructor
    MealPlannerHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    //First database creation
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //SQL command creating "MEASURE" table
        String create_tab_measures = "CREATE TABLE " + MEASURES + "(\n" +
                "    _id integer NOT NULL CONSTRAINT measures_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    measure_name text\n" +
                ");";

        //SQL command creating "INGREDIENTS" table
        String create_tab_ingredients = "CREATE TABLE " + INGREDIENTS + "(\n" +
                "    _id integer NOT NULL CONSTRAINT ingredients_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    name text,\n" +
                "    measures__id integer NOT NULL,\n" +
                "    CONSTRAINT ingredients_measures FOREIGN KEY (measures__id)\n" +
                "    REFERENCES measures (_id)\n" +
                ");";

        //SQL command creating "AT_HOME" table
        String create_tab_at_home = "CREATE TABLE " + AT_HOME + "(\n" +
                "    _id integer NOT NULL CONSTRAINT at_home_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    ingredients__id integer NOT NULL,\n" +
                "    quantity integer,\n" +
                "    CONSTRAINT at_home_ingredients FOREIGN KEY (ingredients__id)\n" +
                "    REFERENCES ingredients (_id)\n" +
                ");";

        //Actual creation of database
        sqLiteDatabase.execSQL(create_tab_measures);
        sqLiteDatabase.execSQL(create_tab_ingredients);
        sqLiteDatabase.execSQL(create_tab_at_home);

        //Populate "Measure" table with data
        insertMeasure(sqLiteDatabase,"Kg");
        insertMeasure(sqLiteDatabase,"L");
        insertMeasure(sqLiteDatabase,"Ml");

        insertIngredients(sqLiteDatabase,"Milk",2);
        insertIngredients(sqLiteDatabase,"Water",3);
        insertIngredients(sqLiteDatabase,"Potatoes",1);

        insertAtHome(sqLiteDatabase,1,2);
        insertAtHome(sqLiteDatabase,3,5);
    }

    //Update of existing databse
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //Method adding data to "Measure" table
    private void insertMeasure(SQLiteDatabase sqLiteDatabase, String mesName){
        ContentValues contentValues = new ContentValues();
        contentValues.put("measure_name",mesName);
        sqLiteDatabase.insert(MEASURES,null,contentValues);
    }

    //Method adding data to "Measure" table
    private void insertIngredients(SQLiteDatabase sqLiteDatabase, String ingName, int mesId){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",ingName);
        contentValues.put("measures_id",mesId);
        sqLiteDatabase.insert(INGREDIENTS,null,contentValues);
    }

    //Method adding data to "Measure" table
    private void insertAtHome(SQLiteDatabase sqLiteDatabase, int ingId, int quantity){
        ContentValues contentValues = new ContentValues();
        contentValues.put("ingredients_id",ingId);
        contentValues.put("quantity",quantity);
        sqLiteDatabase.insert(AT_HOME,null,contentValues);
    }
}
