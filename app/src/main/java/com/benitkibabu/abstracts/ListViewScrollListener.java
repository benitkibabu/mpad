package com.benitkibabu.abstracts;

import android.content.Context;
import android.content.res.TypedArray;
import android.widget.AbsListView;

/**
 * Created by Ben on 22/10/2015.
 */
public class ListViewScrollListener implements AbsListView.OnScrollListener {

    private int toolbarOffset = 0;
    private int toolbarHeight;

    public ListViewScrollListener(Context context){
        int[] actionBarAttr = new int[] { android.R.attr.actionBarSize };
        TypedArray a = context.obtainStyledAttributes(actionBarAttr);
        toolbarHeight = (int) a.getDimension(0, 0) + 10;
        a.recycle();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
