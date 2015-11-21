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

    public CustomPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
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
        switch (position) {
            case 0:
                return "Home";
            case 1:
                return "Tickets";
            case 2:
                return "Appointment";
        }
        return null;
    }
}
