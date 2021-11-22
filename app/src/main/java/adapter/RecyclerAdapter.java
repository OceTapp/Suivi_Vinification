package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suivi_vinification.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import database.entity.CuveEntity;
import util.RecyclerViewItemClickListener;
import viewModel.Cuve;

/**
 * Recycle la vue pour chaque cuve
 */
//TODO ARRETER LA RéPARER
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<CuveEntity> data;
    private RecyclerViewItemClickListener listener;
    /**
     * Avoir une référence pour chaque view
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView mImageView;
        TextView mPeriod;
        TextView mCepage;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.RecyclerView_ImageView_Card);
            mPeriod = itemView.findViewById(R.id.RecyclerView_TextView_TitleCard);
            mCepage = itemView.findViewById(R.id.RecyclerView_TextView_CepageCard);
        }
    }

    public RecyclerAdapter(RecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent
                ,false);
        ViewHolder holder = new ViewHolder(v);
        v.setOnClickListener(view -> listener.onItemClick(view, holder.getAdapterPosition()));
        v.setOnLongClickListener(view -> {
            listener.onItemLongClick(view, holder.getAdapterPosition());
            return true;
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CuveEntity item = data.get(position);
        holder.mCepage.setText(item.getVariety());
        holder.mPeriod.setText(item.getPeriod());

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