package dk.au.group02_mad22_spring_appproject.adapters;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

import dk.au.group02_mad22_spring_appproject.activities.category.CategoryFragment;
import dk.au.group02_mad22_spring_appproject.model.Categories;

public class CategoryAdapter extends FragmentPagerAdapter {
    //Course Lesson3 ADAPTERS MAD
    private List<Categories.Category> categories;

    public CategoryAdapter(FragmentManager fm, List<Categories.Category> categories) {
        // TODO Check this
        super(fm);
        this.categories = categories;
    }



    @Override
    public Fragment getItem(int i) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString("EXTRA_DATA_NAME", categories.get(i).getStrCategory());
        args.putString("EXTRA_DATA_DESC", categories.get(i).getStrCategoryDescription());
        args.putString("EXTRA_DATA_IMAGE", categories.get(i).getStrCategoryThumb());
        fragment.setArguments(args);
        return fragment;


    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categories.get(position).getStrCategory();
    }
}