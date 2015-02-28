package com.acadgild.todo;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TaskAdapter extends ArrayAdapter<TaskInfo> {

    private Context mContext;
    private int mLayoutResourceId;
    private TaskInfo[] mData;

    public TaskAdapter(Context context, int resource, TaskInfo[] data) {
        super(context, resource, data);

        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mData = data;
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

        TaskInfo info = mData[position];

        holder.titleHolder.setText(info.getTitle());
        holder.descriptionHolder.setText(info.getDescription());
        holder.dateHolder.setText(info.getDate());
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
}
