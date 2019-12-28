package com.tkkd.mealplanner;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tkkd.mealplanner.Database.AppDatabase;
import com.tkkd.mealplanner.Database.DAO.HomeDAO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShowATHomeRecyclerAdapter extends RecyclerView.Adapter<ShowATHomeRecyclerAdapter.ViewHolder> {
    private List<HomeDAO.ATHome> ATHomeList;
    private AppDatabase database;

    class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout linearLayout;

        ViewHolder(LinearLayout linearLayout){
            super(linearLayout);
            this.linearLayout = linearLayout;
        }
    }

    ShowATHomeRecyclerAdapter(List<HomeDAO.ATHome> list,AppDatabase database){
        this.ATHomeList = list;
        this.database = database;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final LinearLayout linearLayout = holder.linearLayout;
        long expTimeLong;
        String dateString;
        Date expDate = new Date();
        SimpleDateFormat sdf;

        if(ATHomeList.get(position).expTime.equals("")){
            expTimeLong = ATHomeList.get(position).insertTime + 7*86400000;
            expDate.setTime(expTimeLong);
        }else{
            dateString = ATHomeList.get(position).expTime;
            try {
                Date date = new SimpleDateFormat("dd/MM/yy",Locale.US).parse(dateString);
                expDate.setTime(date.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (expDate.getYear() == new Date().getYear()) {
            sdf = new SimpleDateFormat("dd/MM",Locale.US);
        } else {
            sdf = new SimpleDateFormat("MM/yyyy",Locale.US);
        }
        String ingredient = ATHomeList.get(position).ingName.substring(0,1).toUpperCase() + ATHomeList.get(position).ingName.substring(1);

        //Get rows' objects
        TextView number = linearLayout.findViewById(R.id.number_text_view);
        TextView name = linearLayout.findViewById(R.id.name_text_view);
        TextView measure = linearLayout.findViewById(R.id.measure_text_view);
        TextView quantity = linearLayout.findViewById(R.id.quantity_text_view);
        TextView expTime = linearLayout.findViewById(R.id.exp_time_text_view);
        ImageButton del = linearLayout.findViewById(R.id.delete_button);
        View color = linearLayout.findViewById(R.id.color_rect);

        double colorPicker = (expDate.getTime() - (new Date().getTime()))/86400000;

        //Set objects' display
        number.setText(String.format(Locale.US,"%d",position+1));
        name.setText(ingredient);
        measure.setText(ATHomeList.get(position).measure);
        quantity.setText(String.format(Locale.US,"%.1f",ATHomeList.get(position).quantity));
        expTime.setText(sdf.format(expDate));

        if(colorPicker < 2){
            color.setBackgroundColor(Color.RED);
        }
        else if(colorPicker < 6){
            color.setBackgroundColor(Color.YELLOW);
        }
        else{
            color.setBackgroundColor(Color.GREEN);
        }

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long ingIndex = database.getIngredientDAO().getOneIngredient(ATHomeList.get(position).ingName).id;
                Toast.makeText(linearLayout.getContext(),"Test delete",Toast.LENGTH_LONG).show();
                database.getHomeDAO().deleteATHome(ATHomeList.get(position).quantity, ATHomeList.get(position).insertTime,ingIndex);
                ATHomeList.remove(position);
                notifyDataSetChanged();
                ShowATHomeActivity.isEmpty();
            }
        });
    }
}
