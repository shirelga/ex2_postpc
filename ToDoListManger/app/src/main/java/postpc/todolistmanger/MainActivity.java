package postpc.todolistmanger;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton mFab;
    private EditText mEditText;
    private TextView mText;
    private List<String> mTaks_List;
    static int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTaks_List = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
//        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

//        // specify an adapter (see also next example)
        mAdapter = new RvAdapter(mTaks_List);
        mRecyclerView.setAdapter(mAdapter);

        mText = (EditText) findViewById(R.id.txt);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FabClick(mAdapter, mRecyclerView);
            }
        });
    }

    private void FabClick(RecyclerView.Adapter adapter, RecyclerView recyclerView) {
        RecyclerView.ViewHolder vh = adapter.createViewHolder(recyclerView, 0);
        mTaks_List.add("");
        int pos = mTaks_List.size() - 1;
        adapter.bindViewHolder(vh, pos);
        adapter.notifyItemInserted(pos);
    }
}

