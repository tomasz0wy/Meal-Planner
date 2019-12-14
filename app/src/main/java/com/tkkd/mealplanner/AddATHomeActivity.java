package com.tkkd.mealplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class AddATHomeActivity extends AppCompatActivity {

    private TextView textView;
    private int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_athome);
        textView = findViewById(R.id.button_tester);
        textView.setText(Integer.toString(quantity));
        checkQuantity();
    }

    public void minus(View view) {
        quantity--;
        textView.setText(Integer.toString(quantity));
        checkQuantity();
    }

    public void plus(View view) {
        quantity++;
        textView.setText(Integer.toString(quantity));
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
}
