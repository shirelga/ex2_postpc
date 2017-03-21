package postpc.todolistmanger;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu.ContextMenuInfo;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.View;

/**
 * Created by shirelga on 21/03/2017.
 */

public class FloatingContextMenuRecyclerView extends RecyclerView {

    public FloatingContextMenuRecyclerView(Context context) {
        super(context);
    }

    public FloatingContextMenuRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatingContextMenuRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private ContextMenuInfo mContextMenuInfo = null;

    @Override
    protected ContextMenuInfo getContextMenuInfo() {
        return mContextMenuInfo;
    }


    @Override
    public boolean showContextMenuForChild(View originalView) {
        final int longPressPosition = getChildAdapterPosition(originalView);
        if (longPressPosition >= 0) {
            final long longPressId = getAdapter().getItemId(longPressPosition);
            mContextMenuInfo = createContextMenuInfo(longPressPosition, longPressId);
            return super.showContextMenuForChild(originalView);
        }
        return false;
    }

    ContextMenuInfo createContextMenuInfo(int position, long id) {
        return new RecyclerContextMenuInfo(position, id);
    }

    /**
     * Extra menu information provided to the
     * {@link android.view.View.OnCreateContextMenuListener#onCreateContextMenu(android.view.ContextMenu, View, ContextMenuInfo) }
     * callback when a context menu is brought up for this AdapterView.
     */
    public static class RecyclerContextMenuInfo implements ContextMenu.ContextMenuInfo {

        public RecyclerContextMenuInfo(int position, long id) {
            this.position = position;
            this.id = id;
        }

        /**
         * The position in the adapter for which the context menu is being
         * displayed.
         */
        public int position;

        /**
         * The row id of the item for which the context menu is being displayed.
         */
        public long id;
    }
}
