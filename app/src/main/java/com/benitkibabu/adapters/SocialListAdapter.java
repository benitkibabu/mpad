package com.benitkibabu.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.benitkibabu.helper.DbHelper;
import com.benitkibabu.models.Student;
import com.benitkibabu.ncigomobile.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anu on 11/02/16.
 */
public class SocialListAdapter extends RecyclerView.Adapter<SocialListAdapter.ViewHolder> {

    Context context;
    int layoutId;
    List<Student> studentList;
    OnItemClickListener clickListener;

    public SocialListAdapter(Context context, int layoutId){
        this.context = context;
        this.layoutId = layoutId;
        this.studentList = new ArrayList<>();

    }

    public void clear() {
        if(studentList != null && !studentList.isEmpty())
            studentList.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Student> list) {
        studentList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Student student = studentList.get(position);

        holder.itemHolder.statusView.setText(student.getStatus());
        holder.itemHolder.nameView.setText(student.getStudentID());

        if(student.getStatus().equalsIgnoreCase("offline")){
            holder.itemHolder.statusView.setTextColor(
                    context.getResources().getColor(android.R.color.holo_red_light));
        }else{
            holder.itemHolder.statusView.setTextColor(
                    context.getResources().getColor(android.R.color.holo_green_light));
        }



    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private StudentItemHolder itemHolder;

        public ViewHolder(View itemView) {
            super(itemView);
            itemHolder = new StudentItemHolder();
            itemHolder.placeHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
            itemHolder.nameView = (TextView) itemView.findViewById(R.id.nameTv);
            itemHolder.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            itemHolder.statusView = (TextView) itemView.findViewById(R.id.statusTv);

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

    public static class StudentItemHolder{
        LinearLayout placeHolder;
        TextView nameView, statusView;
        ImageView imageView;
    }
}
