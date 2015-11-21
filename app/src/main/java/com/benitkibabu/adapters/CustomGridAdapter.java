package com.benitkibabu.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.benitkibabu.models.GridItem;
import com.benitkibabu.ncigomobile.R;

import java.util.List;

public class CustomGridAdapter extends ArrayAdapter<GridItem> {
    Context context;
    int layoutId;
    List<GridItem> gridItems;

    public CustomGridAdapter(Context context, int layoutId, List<GridItem> gridItems){
        super(context, layoutId, gridItems);
        this.context = context;
        this.layoutId = layoutId;
        this.gridItems = gridItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;
        GridItemHolder gridItemHolder;
        if(view == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(layoutId, parent, false);
            gridItemHolder = new GridItemHolder();

            gridItemHolder.titleTv = (TextView) view.findViewById(R.id.g_i_textView);
            gridItemHolder.imageView = (ImageView) view.findViewById(R.id.g_i_imageView);

            view.setTag(gridItemHolder);

        }else{
            gridItemHolder = (GridItemHolder) view.getTag();
        }
        GridItem item = this.gridItems.get(position);

        gridItemHolder.titleTv.setText(item.getTitle());
        gridItemHolder.imageView.setImageDrawable(view.getResources().getDrawable(item.getIcon()));


        return view;
    }

    public static class GridItemHolder{
        TextView titleTv;
        ImageView imageView;
    }
}
