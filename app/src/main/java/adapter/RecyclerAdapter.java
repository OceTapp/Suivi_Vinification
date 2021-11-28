package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suivi_vinification.R;

import java.util.List;
import java.util.Objects;

import database.entity.CuveEntity;
import util.RecyclerViewItemClickListener;


/**
 * @author oceane
 * Recycle la view activity_cuve
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<CuveEntity> data;
    private RecyclerViewItemClickListener listener;

    /**
     * Avoir une référence pour chaque view
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView mImageView;
        TextView mNumber;
        TextView mCepage;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.RecyclerView_ImageView_Card);
            mNumber = itemView.findViewById(R.id.RecyclerView_TextView_TitleCard);
            mCepage = itemView.findViewById(R.id.RecyclerView_TextView_CepageCard);

        }
    }

    public RecyclerAdapter(RecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * Création de nouvelles vues si non existante
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent
                , false);
        ViewHolder holder = new ViewHolder(v);
        v.setOnClickListener(view -> listener.onItemClick(view, holder.getAdapterPosition()));
        v.setOnLongClickListener(view -> {
            listener.onItemLongClick(view, holder.getAdapterPosition());
            return true;
        });
        return holder;
    }

    /**
     * Réutilisation automatique des vues
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CuveEntity item = data.get(position);
        holder.mCepage.setText(item.getVariety());
        holder.mNumber.setText(String.valueOf(item.getNumber()));

    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }

    public void setData(final List<CuveEntity> data) {
        if (this.data == null) {
            this.data = data;
            notifyItemRangeInserted(0, data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return RecyclerAdapter.this.data.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {

                    if (RecyclerAdapter.this.data instanceof CuveEntity) {
                        return (RecyclerAdapter.this.data.get(oldItemPosition)).equals(
                                (data.get(newItemPosition)).getId());
                    }
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (RecyclerAdapter.this.data instanceof CuveEntity) {
                        CuveEntity newClient = data.get(newItemPosition);
                        CuveEntity oldClient = RecyclerAdapter.this.data.get(newItemPosition);
                        return Objects.equals(newClient.getNumber(), oldClient.getNumber())
                                && Objects.equals(newClient.getVariety(), oldClient.getVariety());
                    }
                    return false;
                }
            });
            this.data = data;
            result.dispatchUpdatesTo(this);
        }
    }
}