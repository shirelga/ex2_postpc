package postpc.todolistmanger;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private FloatingContextMenuRecyclerView mRecyclerView;
    private FloatingContextMenuRecyclerView.Adapter mAdapter;
    private FloatingContextMenuRecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton mFab;
    final private String CALL = "call";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (FloatingContextMenuRecyclerView) findViewById(R.id.recycler_view);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

//        // specify an adapter (see also next example)
        mAdapter = new RvAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FabClick(mAdapter, mRecyclerView);
            }
        });

        registerForContextMenu(mRecyclerView);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.floating_menu, menu);
        FloatingContextMenuRecyclerView.RecyclerContextMenuInfo info = (FloatingContextMenuRecyclerView.RecyclerContextMenuInfo) menuInfo;
        Pair<Date, String> DateTask = ((RvAdapter)mAdapter).getItem(info.position);
        if(DateTask.second.startsWith(CALL))
        {
            MenuItem callItem = menu.findItem(R.id.makeCall);
            callItem.setVisible(true);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        FloatingContextMenuRecyclerView.RecyclerContextMenuInfo info = (FloatingContextMenuRecyclerView.RecyclerContextMenuInfo) item.getMenuInfo();
        Pair<Date, String> dateTask = ((RvAdapter)mAdapter).getItem(info.position);
        switch(item.getItemId())
        {
            case R.id.delete_b:
            case R.id.done:
                ((RvAdapter)mAdapter).deleteItem(info.position);
                mAdapter.notifyItemRemoved(info.position);
                mAdapter.notifyItemRangeChanged(info.position, mAdapter.getItemCount());
                return true;
            case R.id.new_date:
                Intent newDateDialog = new Intent(this, AddActivity.class);
                newDateDialog.putExtra("task", dateTask.second);
                startActivityForResult(newDateDialog, 1134);
                return true;
            case R.id.makeCall:
                String num = dateTask.second.substring(CALL.length());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + num));
                startActivity(callIntent);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void FabClick(RecyclerView.Adapter adapter, RecyclerView recyclerView) {
        Intent addDialog = new Intent(this, AddActivity.class);
        startActivityForResult(addDialog, 1133);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK)
        {
            String taskName = data.getStringExtra("title");
            Date dueDate = (Date)data.getSerializableExtra("dueDate");
            if (requestCode == 1133) {
                FloatingContextMenuRecyclerView.ViewHolder vh = mAdapter.createViewHolder(mRecyclerView, 0);
                int pos = mAdapter.getItemCount();
                ((RvAdapter)mAdapter).addItem(dueDate, taskName);
                mAdapter.bindViewHolder(vh, pos);
                mAdapter.notifyItemInserted(pos);
            }
            else if (requestCode == 1134)
            {
                String OldTaskName = data.getStringExtra("oldTask");
                int pos = ((RvAdapter)mAdapter).getItemByTask(OldTaskName);
                ((RvAdapter)mAdapter).setItem(new Pair<>(dueDate, taskName), pos);
                mAdapter.notifyItemChanged(pos);
            }
        }
    }
}

