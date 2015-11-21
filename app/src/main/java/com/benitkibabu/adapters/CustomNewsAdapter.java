package com.benitkibabu.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.benitkibabu.models.NewsItem;
import com.benitkibabu.ncigomobile.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 30/09/2015.
 */
public class CustomNewsAdapter extends RecyclerView.Adapter<CustomNewsAdapter.ViewHolder> {

    Context context;
    int layoutId;
    List<NewsItem> items;
    OnItemClickListener clickListener;

    public CustomNewsAdapter(Context context, int layoutId){
        this.context = context;
        this.items = new ArrayList<>();
        this.layoutId = layoutId;
    }

    public void clear() {
        if(items != null && !items.isEmpty())
            items.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<NewsItem> list) {
        items.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final NewsItem item = items.get(position);
        holder.itemHolder.titleView.setText(item.getTitle());
        if(item.getBody().length() > 55) {
            String body = item.getBody().substring(0, 50) + "...";
            holder.itemHolder.bodyView.setText(body);
        }
        else
            holder.itemHolder.bodyView.setText(item.getBody());

        holder.itemHolder.dateTv.setText(item.getDate());

     //   Picasso.with(context).load(imageRes).into(holder.itemHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public NewsItemHolder itemHolder;

        public ViewHolder(View view) {
            super(view);
            itemHolder = new NewsItemHolder();
            itemHolder.placeHolder = (LinearLayout) view.findViewById(R.id.mainHolder);
            itemHolder.titleView = (TextView)view.findViewById(R.id.titleTv);
            itemHolder.bodyView = (TextView)view.findViewById(R.id.bodyTv);
            itemHolder.imageView = (ImageView) view.findViewById(R.id.imageView);
            itemHolder.dateTv = (TextView) view.findViewById(R.id.dateTv);

            itemHolder.placeHolder.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onItemClick(itemView, getPosition());
            }
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener clickListener){
        this.clickListener = clickListener;
    }

    public static class NewsItemHolder{
        LinearLayout placeHolder;
        TextView titleView, bodyView, dateTv;
        ImageView imageView;
    }


}
