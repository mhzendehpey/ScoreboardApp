package com.mxz.board.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mxz.board.entities.Game;
import com.mxz.board.ui.databinding.RowGameBinding;

import java.util.List;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.GamesViewHolder> {

    List<Game> mGames;
    RowGameBinding binding;

    public GamesAdapter(List<Game> games) {
        mGames = games;
    }

    @NonNull
    @Override
    public GamesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RowGameBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new GamesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GamesViewHolder holder, int position) {
        holder.bind(mGames.get(position));
    }

    @Override
    public int getItemCount() {
        return mGames.size();
    }

    public void updateData(List<Game> games) {
        mGames = games;
        notifyDataSetChanged();
    }

    static class GamesViewHolder extends RecyclerView.ViewHolder {

        RowGameBinding binding;

        public GamesViewHolder(RowGameBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Game game) {
            binding.tvGameName.setText(game.Name);
            binding.btnEditGameName.setOnClickListener(
                    v -> Toast.makeText(binding.getRoot().getContext(), "Under Development", Toast.LENGTH_SHORT).show());
        }
    }

}
