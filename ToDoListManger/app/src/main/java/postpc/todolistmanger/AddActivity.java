package postpc.todolistmanger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.view.View.OnClickListener;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by shirelga on 16/04/2017.
 */

public class AddActivity extends Activity {

    private String mOldTask;

    public AddActivity() {
        super();
        mOldTask = null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add);

        Bundle extra = getIntent().getExtras();
        if(extra != null)
        {
            mOldTask = extra.getString("task");
            EditText editTask = (EditText)findViewById(R.id.edtNewItem);
            editTask.setText(mOldTask);
        }

        findViewById(R.id.btnOK).setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
                OnOKClick(v);
            }
        });

        findViewById(R.id.btnCancel).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                OnCancelClick(v);
            }
        });

    }

    private void OnOKClick(View v) {
        EditText editTask = (EditText) findViewById(R.id.edtNewItem);
        String task = editTask.getText().toString();

        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        Date date = cal.getTime();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("title", task);
        resultIntent.putExtra("dueDate", date);

        if(mOldTask != null)
        {
            resultIntent.putExtra("oldTask", mOldTask);
        }

        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void OnCancelClick(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }
}


