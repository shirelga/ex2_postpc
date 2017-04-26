package postpc.todolistmanger;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by shirelga on 24/04/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "toDOList.db";
    public static final String TABLE_NAME = "toDoList";
    public static final String YEAR_COL = "year";
    public static final String MONTH_COL = "day";
    public static final String DAY_COL = "month";
    public static final String TASK_COL = "task";
    public static final String ID_COL = "rownum";
    public static final String TASK_TABLE =
            "CREATE TABLE " +
            TABLE_NAME + "(" + ID_COL + " INTEGER NOT NULL," +
                                TASK_COL + " TEXT,"  +
                                DAY_COL + " INTEGER NOT NULL," +
                                MONTH_COL + " INTEGER NOT NULL," +
                                YEAR_COL + " INTEGER NOT NULL)";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("-DB-", TASK_TABLE);
    }
    
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TASK_TABLE);
        Log.i("-DB-", TASK_TABLE);
    }
    
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
