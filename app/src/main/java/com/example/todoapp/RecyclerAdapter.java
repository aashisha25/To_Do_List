package com.example.todoapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.viewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public RecyclerAdapter(Context context, Cursor cursor ) {
        mContext = context;
        mCursor = cursor;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        public TextView taskText;
        public TextView dateText;
        public TextView timeText;

        public viewHolder(View itemView) {
            super(itemView);

            taskText = itemView.findViewById(R.id.textVTask);
            dateText = itemView.findViewById(R.id.textVDate);
            timeText = itemView.findViewById(R.id.textVTime);
        }

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.task_layout, null);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String task = mCursor.getString(mCursor.getColumnIndex(DataBaseHelper.COL_2));
        String date = mCursor.getString(mCursor.getColumnIndex(DataBaseHelper.COL_3));
        String time = mCursor.getString(mCursor.getColumnIndex(DataBaseHelper.COL_4));

        holder.taskText.setText(task);
        holder.dateText.setText(date);
        holder.timeText.setText(time);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapcursor(Cursor newcursor) {
        if (mCursor != null) {
            mCursor.close();
        }

        mCursor = newcursor;

        if (newcursor != null) {
            notifyDataSetChanged();
        }
    }


}
