package com.mxz.board.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mxz.board.BoardBiz;
import com.mxz.board.entities.Game;
import com.mxz.board.ui.adapters.GamesAdapter;
import com.mxz.board.ui.databinding.FragmentNewGameBinding;

import java.util.List;

public class FragmentGames extends Fragment {

    FragmentNewGameBinding binding;
    private GamesAdapter adapter;

    public FragmentGames() {
    }

    public static FragmentGames newInstance() {
        return new FragmentGames();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewGameBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        getGames();
        binding.btnWriteGame.setOnClickListener(v -> onClickWriteGame(binding.edtGameName.getText().toString()));
    }

    private void initRecyclerView(List<Game> games) {
        if (adapter == null) {
            adapter = new GamesAdapter(games);
            binding.rvGames.setAdapter(adapter);
            binding.rvGames.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            binding.rvGames.setHasFixedSize(true);
        } else {
            adapter.updateData(games);
        }

    }

    private void onClickWriteGame(String name) {
        BoardBiz.saveNewGame(getContext(), name, () -> binding.edtGameName.setText(""));
    }

    private void getGames() {
        BoardBiz.listenGames(getContext(), this::initRecyclerView);
    }
}

