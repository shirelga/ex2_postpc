package postpc.todolistmanger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by shirelga on 20/03/2017.
 */

public class RvAdapter extends FloatingContextMenuRecyclerView.Adapter<RvAdapter.ViewHolder>{

    private List<Pair<Date, String>> mDataset;
    private int taskDbSize;
    private SQLiteDatabase taskDb;
    private DbHelper dbHelper;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends FloatingContextMenuRecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(TextView v) {
            super(v);
            mTextView = v;
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mTextView.showContextMenu();
                    return true;
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RvAdapter(Context context) {
        dbHelper = new DbHelper(context);
        taskDb = dbHelper.getWritableDatabase();
        Cursor c = taskDb.rawQuery("SELECT count(*) FROM " + DbHelper.TABLE_NAME, null);
        if(c.isBeforeFirst())
        {
            c.moveToNext();
        }
        int count = c.getInt(c.getColumnIndex("count(*)"));
        taskDbSize = count;
        mDataset = new ArrayList<>();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int position) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        Log.i("-I-", mDataset.get(position).first + " " + mDataset.get(position).second);

        Cursor c = taskDb.rawQuery("SELECT " + DbHelper.TASK_COL
                        + " FROM " + DbHelper.TABLE_NAME
                        + " WHERE " + DbHelper.ID_COL + " = ?"
                , new String[] {(Integer.valueOf(position)).toString()});
        if (c == null)
        {
            Log.i("-NULL-", "null");
        }

        if(c.isBeforeFirst())
        {
            c.moveToNext();
            if(c.isAfterLast())
            {
                return;
            }
        }
        String task = c.getString(c.getColumnIndex(DbHelper.TASK_COL));
        holder.mTextView.setText(task);
        if((position % 2) == 0)
        {
            holder.mTextView.setBackgroundColor(Color.RED);
        }
        else
        {
            holder.mTextView.setBackgroundColor(Color.BLUE);

        }
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return taskDbSize;
    }

    public void addItem(Date date, String task)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.DATE);

        ContentValues values = new ContentValues();
        values.put(DbHelper.ID_COL, taskDbSize);
        taskDbSize++;
        values.put(DbHelper.TASK_COL, task);
        values.put(DbHelper.DAY_COL, day);
        values.put(DbHelper.MONTH_COL, month);
        values.put(DbHelper.YEAR_COL, year);

        taskDb.insert(DbHelper.TABLE_NAME, null, values);

//        Pair<Date, String> taskPair = new Pair<>(date, task);
//        mDataset.add(taskPair);
    }

    public int getItemByTask(String task)
    {

        for (int i = 0; i < mDataset.size(); i++){
            if(mDataset.get(i).second.equals(task))
            {
                return i;
            }
        }
        return -1;
    }

    public void setItem(Pair<Date, String> dateTask, int pos)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTask.first);
        int day = cal.DAY_OF_MONTH;
        int month = cal.MONTH;
        int year = cal.YEAR;

        ContentValues values = new ContentValues();
        values.put(DbHelper.TASK_COL, dateTask.second);
        values.put(DbHelper.DAY_COL, day);
        values.put(DbHelper.MONTH_COL, month);
        values.put(DbHelper.YEAR_COL, year);

        taskDb.update(DbHelper.TABLE_NAME, values,DbHelper.ID_COL + " = " + pos, null);
//        mDataset.set(pos, dateTask);
    }

    public void deleteItem(int pos)
    {
        taskDb.delete(DbHelper.TABLE_NAME, DbHelper.ID_COL + " = " + pos, null);
        taskDb.execSQL("UPDATE " + DbHelper.TABLE_NAME
                + " SET " + DbHelper.ID_COL + " = " + DbHelper.ID_COL + " -1 "
                + " WHERE " + DbHelper.ID_COL + " > " + pos);
        taskDbSize--;
//        mDataset.remove(pos);
    }

    public Pair<Date, String> getItem(int pos)
    {
        Cursor c = taskDb.rawQuery("SELECT " + DbHelper.TASK_COL
                + " , " + DbHelper.YEAR_COL + " , "
                + DbHelper.MONTH_COL + " , " + DbHelper.DAY_COL
                + " FROM " + DbHelper.TABLE_NAME
                + " WHERE " + DbHelper.ID_COL + " = ?"
                , new String[] {(Integer.valueOf(pos)).toString()});
        if(c.isBeforeFirst())
        {
            c.moveToNext();
        }
        String task = c.getString(c.getColumnIndex(DbHelper.TASK_COL));
        String year = c.getString(c.getColumnIndex(DbHelper.YEAR_COL));
        String month = c.getString(c.getColumnIndex(DbHelper.MONTH_COL));
        String day = c.getString(c.getColumnIndex(DbHelper.DAY_COL));
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day));
        Date date = cal.getTime();
        return new Pair<>(date, task);
    }
}