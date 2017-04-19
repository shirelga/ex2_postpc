package postpc.todolistmanger;

import android.graphics.Color;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shirelga on 20/03/2017.
 */

public class RvAdapter extends FloatingContextMenuRecyclerView.Adapter<RvAdapter.ViewHolder>{

    private List<Pair<Date, String>> mDataset;

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
    public RvAdapter() {
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
        Log.i("-I-", mDataset.get(position).first + " " + mDataset.get(position).second);
        holder.mTextView.setText(mDataset.get(position).second);
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
        return mDataset.size();
    }

    public void addItem(Date date, String task)
    {
        Pair<Date, String> taskPair = new Pair<>(date, task);
        mDataset.add(taskPair);
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
        mDataset.set(pos, dateTask);
    }

    public void deleteItem(int pos)
    {
        mDataset.remove(pos);
    }

    public Pair<Date, String> getItem(int pos)
    {
        return mDataset.get(pos);
    }
}