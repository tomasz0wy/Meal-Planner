package com.tkkd.mealplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.tkkd.mealplanner.Database.AppDatabase;
import com.tkkd.mealplanner.Database.Entities.Ingredient;
import com.tkkd.mealplanner.Database.Entities.Measure;
import com.tkkd.mealplanner.Database.Inserts;

import java.util.List;
import java.util.Locale;

public class AddATHomeActivity extends AppCompatActivity {

    private TextView textView;
    private int quantity = 0;
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
        textView.setText(String.format(Locale.US,"%d",quantity));
        checkQuantity();

        expirationDate = findViewById(R.id.expiration_date);
        expirationDate.addTextChangedListener(new MaskWatcher("##/##/##"));

        autoTextView = findViewById(R.id.auto_comp);
        ArrayAdapter<Ingredient> ingredients = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,database.getIngredientDAO().getIngredients());
        autoTextView.setAdapter(ingredients);
    }

    public void minus(View view) {
        quantity--;
        textView.setText(String.format(Locale.US,"%d",quantity));
        checkQuantity();
    }

    public void plus(View view) {
        quantity++;
        textView.setText(String.format(Locale.US,"%d",quantity));
        checkQuantity();
    }

    void checkQuantity(){
        ImageButton button = findViewById(R.id.button1);
        if(this.quantity == 0){
            button.setEnabled(false);
        }else{
            button.setEnabled(true);
        }
    }

    public void insert(View view){
        String ingredientName = autoTextView.getText().toString().toLowerCase();
        String expDate = expirationDate.getText().toString();
        if(checkBox.isChecked()){
            String mesName = spinner.getSelectedItem().toString();
            Inserts.insertIngredient(database,ingredientName, database.getMeasureDAO().getOneMeasure(mesName).id);
        }
        Ingredient ingredient = database.getIngredientDAO().getOneIngredient(ingredientName);
        Inserts.insertHome(database,ingredient.id,quantity,expDate);
        finish();
    }

    public void show(View view) {
        checkBox = findViewById(R.id.is_new_checkbox);
        LinearLayout linearLayout = findViewById(R.id.placeholder);
        if(checkBox.isChecked()){
            spinner = new Spinner(this);
            spinner.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            linearLayout.addView(spinner);
            List<Measure> measureList = database.getMeasureDAO().getMeasures();
            ArrayAdapter<Measure> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,measureList);
            spinner.setAdapter(arrayAdapter);
            spinner.setSelection(1);
        }else {
            linearLayout.removeAllViews();
        }
    }
}
