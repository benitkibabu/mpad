package com.benitkibabu.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.benitkibabu.fragments.ItemFragment;


public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private String[] daysOfWeeks;

    public SectionsPagerAdapter(FragmentManager fm, String[] array) {
        super(fm);
        daysOfWeeks = array;
    }

    @Override
    public Fragment getItem(int position) {
        return ItemFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return daysOfWeeks.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return daysOfWeeks[position];
    }
}