package dk.au.group02_mad22_spring_appproject.activities.category;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dk.au.group02_mad22_spring_appproject.R;
import dk.au.group02_mad22_spring_appproject.activities.mainactivity.MainActivity;
import dk.au.group02_mad22_spring_appproject.adapters.CategoryAdapter;
import dk.au.group02_mad22_spring_appproject.model.Categories;

//https://www.journaldev.com/10439/android-butterknife-example
//https://developer.android.com/reference/android/content/Intent
public class CategoryActivity extends AppCompatActivity {
    private static final String TAG = "CategoryActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "OnCreate created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);

        setupActionBar();
        setupIntent();

    }

    private void setupIntent() {
        Log.d(TAG, "Intent is up");
        Intent intent = getIntent();
        List<Categories.Category> categories =
                (List<Categories.Category>) intent.getSerializableExtra
                        (MainActivity.EXTRA_CATEGORY);
        int position = intent.getIntExtra(MainActivity.EXTRA_POSITION, 0);

        CategoryAdapter adapter = new CategoryAdapter(
                getSupportFragmentManager(), categories);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(position, true);
        adapter.notifyDataSetChanged();
    }

    private void setupActionBar() {
        Log.d(TAG, "Actionbar is up");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                Log.d(TAG, "Going back");
                break;
        }
        return true;
    }
}
