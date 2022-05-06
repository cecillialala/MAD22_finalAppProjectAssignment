package dk.au.group02_mad22_spring_appproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

import dk.au.group02_mad22_spring_appproject.R;
import dk.au.group02_mad22_spring_appproject.model.Meals;

//https://developer.android.com/reference/android/widget/Adapter
//Course Lesson ADAPTERS MAD
public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {

    List<Meals.Meal> meals;
    private OnItemClickListener mListener;
    private static FavouriteAdapter.ClickListener clickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public FavouriteAdapter(List<Meals.Meal> meals) {
        this.meals = meals;

    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    @Override
    public FavouriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_row, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.ViewHolder holder, int position) {
        holder.faveMealName.setText(meals.get(position).getStrMeal());
        //Glide.with(holder.faveMealImage.getContext()).load(meals.get(position).getStrMealThumb()).into(holder.faveMealImage);
// TODO her
       //Glide.with(holder.faveMealImage.getContext()).load(meals.get(position).getStrMealThumb()).into(holder.faveMealImage);
        Picasso.get().load(meals.get(position).getStrMealThumb()).into(holder.faveMealImage);
    }

    @Override
    public int getItemCount() {
        if (meals == null){
            return 0;
        }
        return meals.size();
    }

    /* https://developer.android.com/reference/android/support/v7/widget/RecyclerView.ViewHolder */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView faveMealName;
        public ImageView faveMealImage;
        public ViewHolder(View itemView) {
            super(itemView);
            faveMealName = (TextView) itemView.findViewById(R.id.fave_meal_name);
            faveMealImage = (ImageView) itemView.findViewById(R.id.fave_meal_img);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getAdapterPosition());
        }
    }

    public void updateMealsList(List<Meals.Meal> _mealsList){
        meals = _mealsList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        FavouriteAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onClick(View view, int position);
    }
}