package com.benitkibabu.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.benitkibabu.app.AppConfig;
import com.benitkibabu.helper.DbHelper;
import com.benitkibabu.models.ReminderItem;
import com.benitkibabu.ncigomobile.R;
import com.daimajia.swipe.SwipeLayout;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by Ben on 30/09/2015.
 */
public class ReminderAdapter extends ArrayAdapter<ReminderItem> implements ListAdapter{
    Context context;
    int layoutId;
    List<ReminderItem> items;
    ReminderItemHolder itemHolder;
    ReminderItem item;
    DbHelper db;

    public ReminderAdapter(Context context, int layoutId, List<ReminderItem> items){
        super(context, layoutId, items);
        this.context = context;
        this.layoutId = layoutId;
        this.items = items;

        db = new DbHelper(this.context);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent){
        View view = convertView;

        if(view == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            view  = inflater.inflate(layoutId, parent, false);
            itemHolder = new ReminderItemHolder();

            itemHolder.moduleTv = (TextView)view.findViewById(R.id.moduleNameTv);
            itemHolder.descTv = (TextView)view.findViewById(R.id.descriptionTv);
            itemHolder.dateTv = (TextView) view.findViewById(R.id.dateTv);
            itemHolder.deleteBtn = (ImageButton) view.findViewById(R.id.del_button);
            itemHolder.swipeLayout = (SwipeLayout) view.findViewById(R.id.swipeLayout);
            //itemHolder.itemLayout = (LinearLayout) view.findViewById(R.id.item_layout_id);
            itemHolder.remTv = (TextView) view.findViewById(R.id.remTv);

            view.setTag(itemHolder);
        }else{
            itemHolder = (ReminderItemHolder) view.getTag();
        }

        item = this.items.get(position);

        itemHolder.moduleTv.setText(item.getModuleName());
        itemHolder.descTv.setText(item.getDescription());
        itemHolder.dateTv.setText(item.getDueDate());

        itemHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = items.get(position);
                Snackbar.make(v, "item clicked at position: " + item.getId(), Snackbar.LENGTH_LONG)
                        .show();
                db.removeReminder(item.getId());
                items.remove(item);
                notifyDataSetChanged();
            }
        });

        Date now = new Date();
        Date due;

        try {
            due = AppConfig.getDate(item.getDueDate());
            long startTime = now.getTime();
            long endTime = due.getTime();
            long diffTime = endTime - startTime;
            long diffDays = diffTime / (1000 * 60 * 60 * 24);

            if(diffDays <= 0) {
               // itemHolder.itemLayout.setBackgroundColor(Color.RED);
                itemHolder.remTv.setVisibility(View.VISIBLE);
                itemHolder.remTv.setText("DUE");
            }
            else if(diffDays < 3 && diffDays >0) {
               // itemHolder.itemLayout.setBackgroundColor(context.getResources().getColor(R.color.orange));
                itemHolder.remTv.setVisibility(View.VISIBLE);
                itemHolder.remTv.setText("VERY SOON!");
            }else if(diffDays >= 3 && diffDays <= 5){
                //itemHolder.itemLayout.setBackgroundColor(context.getResources().getColor(R.color.yellow));
                itemHolder.remTv.setVisibility(View.VISIBLE);
                itemHolder.remTv.setText("SOON!");
            }
            else if(diffDays >5 && diffDays <= 10){
               // itemHolder.itemLayout.setBackgroundColor(context.getResources().getColor(R.color.green));
                itemHolder.remTv.setVisibility(View.INVISIBLE);
            }
            else{
               // itemHolder.itemLayout.setBackgroundColor(context.getResources().getColor(R.color.light_blue));
                itemHolder.remTv.setVisibility(View.INVISIBLE);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        itemHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        return view;
    }

    public static class ReminderItemHolder {
        TextView moduleTv, descTv, dateTv, remTv;
        ImageButton deleteBtn;
        SwipeLayout swipeLayout;
        LinearLayout itemLayout;
    }
}
