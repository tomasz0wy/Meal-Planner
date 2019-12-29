package com.tkkd.mealplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.tkkd.mealplanner.Database.AppDatabase;
import com.tkkd.mealplanner.Database.Entities.Ingredient;
import com.tkkd.mealplanner.Database.Entities.Measure;
import com.tkkd.mealplanner.Database.Inserts;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddATHomeActivity extends AppCompatActivity {

    private EditText textView;
    private float quantity = 1;
    private AppDatabase database;
    private AutoCompleteTextView autoTextView;
    private EditText expirationDate;
    private Spinner spinner;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_athome);

        database = Room.databaseBuilder(this,AppDatabase.class,"MealPlanner")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();

        textView = findViewById(R.id.button_tester);
        textView.setText(String.format(Locale.US,"%.1f",quantity));
        checkQuantity();

        expirationDate = findViewById(R.id.expiration_date);
        expirationDate.addTextChangedListener(new MaskWatcher("##/##/##"));

        autoTextView = findViewById(R.id.auto_comp);
        ArrayAdapter<Ingredient> ingredients = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,database.getIngredientDAO().getIngredients());
        autoTextView.setAdapter(ingredients);
        checkBox = findViewById(R.id.is_new_checkbox);
    }

    public void minus(View view) {
        quantity = Float.valueOf(textView.getText().toString());
        quantity--;
        textView.setText(String.format(Locale.US,"%.1f",quantity));
        checkQuantity();
    }

    public void plus(View view) {
        quantity = Float.valueOf(textView.getText().toString());
        quantity++;
        textView.setText(String.format(Locale.US,"%.1f",quantity));
        checkQuantity();
    }

    void checkQuantity(){
        Button button = findViewById(R.id.button1);
        if(this.quantity == 0){
            button.setEnabled(false);
        }else{
            button.setEnabled(true);
        }
    }

    public void insert(View view){
        String ingredientName = autoTextView.getText().toString().toLowerCase();
        float quantityTaken = Float.valueOf(textView.getText().toString());
        if(ingredientName.equals("")){
            Toast.makeText(this,"Add the ingredient name",Toast.LENGTH_LONG).show();
        }else{
            String expDate = expirationDate.getText().toString();
            if(expDate.equals("")){
                long date = new Date().getTime() + 7*86400000;
                Date dateToSet = new Date(date);
                expDate = new SimpleDateFormat("dd/MM/yy",Locale.US).format(dateToSet);
            }
            if(checkBox.isChecked()){
                String mesName = spinner.getSelectedItem().toString();
                Inserts.insertIngredient(database,ingredientName, database.getMeasureDAO().getOneMeasure(mesName).id);
            }
            Ingredient ingredient = database.getIngredientDAO().getOneIngredient(ingredientName);

            if(ingredient == null){
                Toast.makeText(this,"No such ingredient in database",Toast.LENGTH_LONG).show();
            }else{
                Inserts.insertHome(database,ingredient.id,quantityTaken,expDate);
                finish();
            }
        }
    }

    public void show(View view) {
        LinearLayout linearLayout = findViewById(R.id.placeholder);
        if(checkBox.isChecked()){
            TextView spinnerPrompt = new TextView(this);
            spinnerPrompt.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            spinnerPrompt.setText(R.string.choose_def_meas);
            spinner = new Spinner(this);
            spinner.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            linearLayout.addView(spinnerPrompt);
            linearLayout.addView(spinner);
            List<Measure> measureList = database.getMeasureDAO().getMeasures();
            ArrayAdapter<Measure> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,measureList);
            spinner.setAdapter(arrayAdapter);
            spinner.setSelection(1);
        }else {
            linearLayout.removeAllViews();
        }
    }

    public void change(View view) {
        Intent intent = new Intent(AddATHomeActivity.this, RecipeListActivity.class);
        startActivity(intent);
    }
}
