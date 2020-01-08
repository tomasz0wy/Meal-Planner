package com.tkkd.mealplanner;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tkkd.mealplanner.Database.Entities.Recipe;
import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {
    private List<Recipe> data;

    class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout linearLayout;

        ViewHolder(LinearLayout linearLayout){
            super(linearLayout);
            this.linearLayout = linearLayout;
        }
    }

    RecipeListAdapter(List<Recipe> data){
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    @NonNull
    @Override
    public RecipeListAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recipe_recycler_row,parent,false);
        return new ViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListAdapter.ViewHolder holder, final int position) {
        final LinearLayout linearLayout = holder.linearLayout;
        TextView textView = linearLayout.findViewById(R.id.recipe_name);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(linearLayout.getContext(),SingleRecipeActivity.class);
                intent.putExtra(SingleRecipeActivity.INTENT_RECIPE,data.get(position).id);
                linearLayout.getContext().startActivity(intent);
            }
        });
        textView.setText(data.get(position).recName);
    }
}
