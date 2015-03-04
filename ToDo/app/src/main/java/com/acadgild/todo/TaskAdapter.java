package com.acadgild.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class TaskAdapter extends ArrayAdapter<TaskInfo> {

    private Context mContext;
    private int mLayoutResourceId;
    private List<TaskInfo> taskList;

    public TaskAdapter(Context context, int resource, List<TaskInfo> taskList) {
        super(context, resource, taskList);

        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.taskList = taskList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        Holder holder;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new Holder();
            holder.titleHolder = (TextView) row.findViewById(R.id.titleText);
            holder.dateHolder = (TextView) row.findViewById(R.id.dateText);
            holder.descriptionHolder = (TextView) row.findViewById(R.id.descriptionText);
            holder.imageHolder = (ImageView) row.findViewById(R.id.imageView);
            holder.dateHeaderHolder = (TextView) row.findViewById(R.id.dateHeaderText);

            row.setTag(holder);

        } else {
            holder = (Holder) row.getTag();
        }

        TaskInfo info = taskList.get(position);

        holder.titleHolder.setText(info.getTitle());
        holder.descriptionHolder.setText(info.getDescription());
        holder.dateHolder.setText(ConvertDate(info.getDate()));
        holder.dateHeaderHolder.setText(ConvertDate(info.getDate()));
        holder.imageHolder.setImageResource(info.getImage());

        return row;
    }

    @Override
    public TaskInfo getItem(int position) {
        return super.getItem(position);
    }

    private static class Holder {
        TextView titleHolder;
        TextView descriptionHolder;
        TextView dateHolder;
        ImageView imageHolder;
        TextView dateHeaderHolder;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    public void updateList(List<TaskInfo> newList) {
        taskList.clear();
        taskList.addAll(newList);
        this.notifyDataSetChanged();
    }

    private String ConvertDate(String date) {

        String year = date.substring(0,4);
        String month = date.substring(4,6);
        String day = date.substring(6,8);

        return month + "/" + day + "/" + year;
    }
}
