package dk.au.group02_mad22_spring_appproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import dk.au.group02_mad22_spring_appproject.R;
import dk.au.group02_mad22_spring_appproject.model.Meals;

//https://developer.android.com/reference/android/widget/Adapter
//Course Lesson3 ADAPTERS MAD
public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.ViewHolder> {
    private static final String TAG = "FavoriteAdapter";
    List<Meals.Meal> meals;
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public FavouritesAdapter(List<Meals.Meal> meals) {
        this.meals = meals;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    @Override
    public FavouritesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritesAdapter.ViewHolder holder, int position) {
        holder.faveMealName.setText(meals.get(position).getStrMeal());
      Picasso.get().load(meals.get(position).getStrMealThumb()).into(holder.faveMealImage);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    /* https://developer.android.com/reference/android/support/v7/widget/RecyclerView.ViewHolder */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView faveMealName;
        public ImageView faveMealImage;
        public ViewHolder(View itemView) {
            super(itemView);
            faveMealName = (TextView) itemView.findViewById(R.id.fave_meal_name);
            faveMealImage = (ImageView) itemView.findViewById(R.id.fave_meal_img);

            itemView.setOnClickListener(v -> {
                if(mListener != null) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(position);
                    }
                }
            });
        }
    }
}