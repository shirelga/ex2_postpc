package postpc.todolistmanger;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FloatingContextMenuRecyclerView mRecyclerView;
    private FloatingContextMenuRecyclerView.Adapter mAdapter;
    private FloatingContextMenuRecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton mFab;
    private EditText mEditText;
    private List<String> mTaks_List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTaks_List = new ArrayList<>();

        mRecyclerView = (FloatingContextMenuRecyclerView) findViewById(R.id.recycler_view);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

//        // specify an adapter (see also next example)
        mAdapter = new RvAdapter(mTaks_List);
        mRecyclerView.setAdapter(mAdapter);

        mEditText = (EditText) findViewById(R.id.etxt);

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
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        FloatingContextMenuRecyclerView.RecyclerContextMenuInfo info = (FloatingContextMenuRecyclerView.RecyclerContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId())
        {
            case R.id.delete_b:
                mTaks_List.remove(info.position);
                mAdapter.notifyItemRemoved(info.position);
                mAdapter.notifyItemRangeChanged(info.position, mAdapter.getItemCount());
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void FabClick(RecyclerView.Adapter adapter, RecyclerView recyclerView) {
        Editable txt = mEditText.getText();
        String txtStr = txt.toString();
        mTaks_List.add(txtStr);
        FloatingContextMenuRecyclerView.ViewHolder vh = adapter.createViewHolder(recyclerView, 0);
        int pos = mTaks_List.size() - 1;
        adapter.bindViewHolder(vh, pos);
        adapter.notifyItemInserted(pos);
        mEditText.setText("");
        mEditText.clearFocus();
    }
}

