package com.mxz.board.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mxz.board.entities.GameResult;
import com.mxz.board.ui.R;
import com.mxz.board.ui.databinding.RowResultsBinding;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Slide;
import androidx.transition.TransitionManager;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultsViewHolder> {

    List<GameResult> mData;

    public ResultsAdapter(List<GameResult> data) {
        mData = data;
        Collections.sort(this.mData,
                (o1, o2) -> o2.getDateString().compareTo(o1.getDateString())
        );
    }

    @NonNull
    @Override
    public ResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResultsViewHolder(RowResultsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ResultsViewHolder extends RecyclerView.ViewHolder {
        RowResultsBinding binding;

        public ResultsViewHolder(@NonNull RowResultsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(GameResult result) {
            binding.tvDate.setText(result.Date);
            binding.tvGameName.setText(result.Game.Name);
//            binding.lnlHead.setOnClickListener(v -> onClickHead());
            binding.rvRankings.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext(), RecyclerView.VERTICAL, false));
            binding.rvRankings.setHasFixedSize(true);
            binding.rvRankings.setAdapter(new RankingsAdapter(result.Rankings, false));
        }

        private void onClickHead() {
            if (binding.lnlHidden.getVisibility() == View.GONE) {
//                TransitionManager.beginDelayedTransition(binding.lnlHidden, new Slide());
                binding.lnlHidden.setVisibility(View.VISIBLE);
                binding.ivIndicator.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24_white);


            } else {
//                TransitionManager.beginDelayedTransition(binding.lnlHidden, new Slide());
                binding.lnlHidden.setVisibility(View.GONE);
                binding.ivIndicator.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24_white);
            }
        }
    }
}
