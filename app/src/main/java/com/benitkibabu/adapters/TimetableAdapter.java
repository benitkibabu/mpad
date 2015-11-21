package com.benitkibabu.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.benitkibabu.models.Timetable;
import com.benitkibabu.ncigomobile.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 19/11/2015.
 */
public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.ViewHolder> {
    Context context;
    int layoutId;
    List<Timetable> items;

    OnItemClickListener clickListener;

    public TimetableAdapter(Context context, int layoutId){
        this.context = context;
        this.layoutId = layoutId;
       items = new ArrayList<>();
    }

    public void clear() {
        if(items != null && !items.isEmpty())
            items.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Timetable> list) {
        items.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        public ItemHolder itemHolder;
        public ViewHolder(View view) {
            super(view);
            itemHolder = new ItemHolder();
            itemHolder.startTime = (TextView) view.findViewById(R.id.ti_startT);
            itemHolder.endTime = (TextView) view.findViewById(R.id.ti_endT);
            itemHolder.roomName = (TextView) view.findViewById(R.id.ti_roomName);
            itemHolder.lecturerName = (TextView) view.findViewById(R.id.ti_lecturerTv);
            itemHolder.moduleName = (TextView) view.findViewById(R.id.ti_nameTv);
            itemHolder.clickable = (LinearLayout) view.findViewById(R.id.mainHolder);

            itemHolder.clickable.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onItemClick(itemView, getPosition());
            }
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Timetable item = items.get(position);
        holder.itemHolder.startTime.setText(item.getStart());
        holder.itemHolder.endTime.setText(item.getFinish()) ;
        holder.itemHolder.roomName.setText(item.getRoom()) ;
        holder.itemHolder.lecturerName.setText(item.getLecturer());
        holder.itemHolder.moduleName.setText(item.getModule());
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener clickListener){
        this.clickListener = clickListener;
    }

    public static class ItemHolder{
        TextView startTime, endTime, roomName, lecturerName,moduleName;
        LinearLayout clickable;
    }
}