package com.tkkd.mealplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.tkkd.mealplanner.Database.AppDatabase;
import com.tkkd.mealplanner.Database.Entities.Ingredient;
import com.tkkd.mealplanner.Database.Inserts;
import java.util.Locale;

public class AddATHomeActivity extends AppCompatActivity {

    private TextView textView;
    private int quantity = 0;
    private AppDatabase database;
    private AutoCompleteTextView autoTextView;
    private EditText expirationDate;

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
        Ingredient ingredient = database.getIngredientDAO().getOneIngredient(ingredientName);
        String expDate = expirationDate.getText().toString();
        Inserts.insertHome(database,ingredient.id,quantity,expDate);
        finish();
    }
}
