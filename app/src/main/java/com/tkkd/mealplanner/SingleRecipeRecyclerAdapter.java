package com.tkkd.mealplanner;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tkkd.mealplanner.Database.AppDatabase;
import com.tkkd.mealplanner.Database.Entities.IngredientListForRecipe;

import java.util.List;
import java.util.Locale;

public class SingleRecipeRecyclerAdapter extends RecyclerView.Adapter<SingleRecipeRecyclerAdapter.ViewHolder> {

    private List<IngredientListForRecipe> data;
    private AppDatabase database;

    class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout linearLayout;

        ViewHolder(LinearLayout linearLayout){
            super(linearLayout);
            this.linearLayout = linearLayout;
        }
    }

    SingleRecipeRecyclerAdapter(List<IngredientListForRecipe> data, AppDatabase database){
        this.data = data;
        this.database = database;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SingleRecipeRecyclerAdapter.ViewHolder holder, int position) {
        final LinearLayout linearLayout = holder.linearLayout;

        TextView quantityList = linearLayout.findViewById(R.id.quantity_list);
        TextView measureList = linearLayout.findViewById(R.id.measure_list);
        TextView nameList = linearLayout.findViewById(R.id.name_list);

        float quantity = data.get(position).quantity;
        if(quantity == Math.round(quantity)){
            int quantitySmall = (int) quantity;
            quantityList.setText(String.valueOf(quantitySmall));
        }else {
            quantityList.setText(String.format(Locale.US,"%.2f",quantity));
        }

        String mesName = database.getMeasureDAO().getOneMeasureById(data.get(position).mesId).mesName;
        if(mesName.equals("")){
            measureList.setText(mesName);
        }else {
            measureList.setText(" " + mesName);
        }
        nameList.setText(" "+database.getIngredientDAO().getOneIngredientById(data.get(position).ingredientId).toString());
    }

    @NonNull
    @Override
    public SingleRecipeRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.single_recipe_recycler_row,
                parent,false);
        return new ViewHolder(linearLayout);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
