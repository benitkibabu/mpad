package com.benitkibabu.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Ben on 17/10/2015.
 */
public class CustomPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList;
    String[] titles;

    public CustomPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] array) {
        super(fm);
        this.fragmentList = fragmentList;
        titles = array;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //return fragmentList.get(position).getTag();
        return titles[position];
    }
}
