package com.mxz.board.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mxz.board.ui.databinding.RowRankingsBinding;
import com.mxz.board.entities.Rank;
import com.mxz.board.ui.R;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RankingsAdapter extends RecyclerView.Adapter<RankingsAdapter.RankingsViewHolder> {

    private final List<Rank> mData;
    private final boolean editable;

    public RankingsAdapter(List<Rank> mData, boolean editable) {
        this.mData = mData;
        this.editable = editable;

    }

    @NonNull
    @Override
    public RankingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rankings, parent, false);
        RowRankingsBinding binding = RowRankingsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new RankingsViewHolder(binding, editable);
    }

    @Override
    public void onBindViewHolder(@NonNull RankingsViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public List<Rank> getData() {
        return mData;
    }

    public void addToData(Rank rank) {
        mData.add(rank);
        notifyItemInserted(mData.size() - 1);

        Collections.sort(mData, (o1, o2) -> Integer.compare(o1.Position, o2.Position));
        notifyItemRangeChanged(0, getItemCount());
    }

    public void removeItem(Rank rank) {
        int pos = mData.indexOf(rank);
        mData.remove(pos);
        notifyItemRemoved(pos);
    }

    public class RankingsViewHolder extends RecyclerView.ViewHolder {

        RowRankingsBinding binding;
        boolean editable;

        public RankingsViewHolder(@NonNull RowRankingsBinding binding, boolean editable) {
            super(binding.getRoot());
            this.binding = binding;
            this.editable = editable;
        }

        public void bind(Rank rank) {
            binding.tvPlayer.setText(rank.PlayerName);
            binding.btnRemove.setOnClickListener(v -> removeItem(rank));

            if (rank.Position < 4) {
                binding.ivMedal.setVisibility(View.VISIBLE);
                binding.tvPosition.setVisibility(View.GONE);
            } else {
                binding.ivMedal.setVisibility(View.GONE);
                binding.tvPosition.setVisibility(View.VISIBLE);
            }

            switch (rank.Position) {
                case 1: {
                    binding.ivMedal.setImageResource(R.drawable.gold_medal);
                    break;
                }
                case 2: {
                    binding.ivMedal.setImageResource(R.drawable.silver_medal);
                    break;
                }
                case 3: {
                    binding.ivMedal.setImageResource(R.drawable.bronze_medal);
                    break;
                }
                default: {
                    binding.tvPosition.setText(String.valueOf(rank.Position));
                }
            }

            if (editable) {
                binding.btnRemove.setVisibility(View.VISIBLE);
            } else {
                binding.btnRemove.setVisibility(View.GONE);
            }

        }
    }

}


