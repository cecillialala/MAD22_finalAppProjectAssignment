package dk.au.group02_mad22_spring_appproject.activities.searchactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dk.au.group02_mad22_spring_appproject.R;
import dk.au.group02_mad22_spring_appproject.activities.detailsactivity.DetailActivity;
import dk.au.group02_mad22_spring_appproject.adapters.RecyclerViewMealByCategory;
import dk.au.group02_mad22_spring_appproject.adapters.SearchRecyclerViewAdapter;
import dk.au.group02_mad22_spring_appproject.model.Meals;
import dk.au.group02_mad22_spring_appproject.repository.Repository;
import dk.au.group02_mad22_spring_appproject.repository.SearchView;
import dk.au.group02_mad22_spring_appproject.viewmodels.SearchViewModel;

public class SearchActivity extends AppCompatActivity implements SearchView {

    private static final String TAG = "SearchActivity";
    public static final String EXTRA_DETAIL = "detail";
    Button backBtn, searchBtn;
    EditText editSearchText;
    String searchTxt;

    @BindView(R.id.searchRV) RecyclerView recyclerViewSearch;

    Repository.SearchPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initUI();
    }

    private void initUI() {
        ButterKnife.bind(this);
        presenter = new Repository.SearchPresenter(this);

        editSearchText = findViewById(R.id.editSearch);

        RecyclerView rv = findViewById(R.id.searchRV);
        rv.setLayoutManager(new LinearLayoutManager(this));

        searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSearch();
            }
        });

        backBtn = findViewById(R.id.searchBackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBack();
            }
        });
    }

    private void onBack() {
        finish();
    }

    private void onSearch() {
        searchTxt = editSearchText.getText().toString().toLowerCase();
        presenter.getMealByName(searchTxt);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setMeals(List<Meals.Meal> meals) {
        SearchRecyclerViewAdapter searchAdapter = new SearchRecyclerViewAdapter(meals, this);
        recyclerViewSearch.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();

        searchAdapter.setOnItemClickListener((view, position) -> {
            TextView mealName = view.findViewById(R.id.mealName);
            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            intent.putExtra(EXTRA_DETAIL, mealName.getText().toString());
            startActivity(intent);
        });
    }

    @Override
    public void onErrorLoading(String message) {

    }
}