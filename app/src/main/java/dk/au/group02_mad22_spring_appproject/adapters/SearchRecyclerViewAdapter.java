package dk.au.group02_mad22_spring_appproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dk.au.group02_mad22_spring_appproject.R;
import dk.au.group02_mad22_spring_appproject.model.Categories;
import dk.au.group02_mad22_spring_appproject.model.Meals;
//https://developer.android.com/reference/android/widget/Adapter
//Course Lesson3 ADAPTERS MAD
public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.SearchViewHolder> {

    private List<Meals.Meal> mealsList;
    private Context context;
    private static SearchRecyclerViewAdapter.ClickListener clickListener;

    public SearchRecyclerViewAdapter(List<Meals.Meal> mealsList, Context context) {
        this.mealsList = mealsList;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_meal,
                parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerViewAdapter.SearchViewHolder holder, int position) {
        String strMealThumb = mealsList.get(position).getStrMealThumb();
        Picasso.get().load(strMealThumb).placeholder(R.drawable.ic_circle).into(holder.mealThumb);

        String strMealName = mealsList.get(position).getStrMeal();
        holder.mealName.setText(strMealName);
    }

    @Override
    public int getItemCount() {
        if (mealsList == null){
            Toast.makeText(context, "No such thing exists", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return mealsList.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.mealThumb)
        ImageView mealThumb;
        @BindView(R.id.mealName)
        TextView mealName;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getAdapterPosition());
        }
    }

    public void setOnItemClickListener(SearchRecyclerViewAdapter.ClickListener clickListener) {
        SearchRecyclerViewAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onClick(View view, int adapterPosition);

    }
}
