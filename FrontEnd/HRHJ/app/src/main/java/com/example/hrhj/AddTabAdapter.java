package com.example.hrhj;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

public class AddTabAdapter extends FragmentPagerAdapter {

    private int tabCount;

    public AddTabAdapter(FragmentManager manager, int tabCount) {
        super(manager);
        this.tabCount = tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch(position) {
            case 0:
                fragment =  new AddGalleryFragment();
                break;
            case 1:
                fragment = new AddBasicFragment();
                break;
            case 2:
                fragment =  new AddCameraFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }
}
