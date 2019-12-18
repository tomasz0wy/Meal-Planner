package com.tkkd.mealplanner;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tkkd.mealplanner.Database.DAO.HomeDAO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MealPlannerRecyclerAdapter extends RecyclerView.Adapter<MealPlannerRecyclerAdapter.ViewHolder> {
    private List<HomeDAO.ATHome> ATHomeList;

    class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout linearLayout;

        ViewHolder(LinearLayout linearLayout){
            super(linearLayout);
            this.linearLayout = linearLayout;
        }
    }

    MealPlannerRecyclerAdapter(List<HomeDAO.ATHome> list){
        this.ATHomeList = list;
    }

    @Override
    public int getItemCount() {
        return this.ATHomeList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recycler_row,parent,false);
        return new ViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LinearLayout linearLayout = holder.linearLayout;
        Date insertDate = new Date();
        insertDate.setTime(ATHomeList.get(position).insertTime + (ATHomeList.get(position).expTime)*86400000);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM",Locale.US);
        String ingredient = ATHomeList.get(position).ingName.substring(0,1).toUpperCase() + ATHomeList.get(position).ingName.substring(1);
        TextView number = linearLayout.findViewById(R.id.number_text_view);
        TextView name = linearLayout.findViewById(R.id.name_text_view);
        TextView measure = linearLayout.findViewById(R.id.measure_text_view);
        TextView quantity = linearLayout.findViewById(R.id.quantity_text_view);
        TextView expTime = linearLayout.findViewById(R.id.exp_time_text_view);

        number.setText(String.format(Locale.US,"%d",position+1));
        name.setText(ingredient);
        measure.setText(ATHomeList.get(position).measure);
        quantity.setText(String.format(Locale.US,"%d",ATHomeList.get(position).quantity));
        expTime.setText(sdf.format(insertDate));
    }
}
