package util;

import android.view.View;

/**
 * @author oceane
 * Interface utilisé dans la class RecyclerAdapter
 */
public interface RecyclerViewItemClickListener {
    void onItemClick(View v, int position);
    void onItemLongClick(View v, int position);
}
