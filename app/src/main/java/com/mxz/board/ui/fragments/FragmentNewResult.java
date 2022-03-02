package com.mxz.board.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.mxz.board.BoardBiz;
import com.mxz.board.entities.Game;
import com.mxz.board.entities.Rank;
import com.mxz.board.ui.R;
import com.mxz.board.ui.adapters.RankingsAdapter;
import com.mxz.board.ui.databinding.FragmentNewResultBinding;
import com.mxz.board.utils.DateInputMask;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentNewResult extends Fragment {
    private FragmentNewResultBinding binding;
    private RankingsAdapter adapter;

    public FragmentNewResult() {
    }

    public static FragmentNewResult newInstance() {
        return new FragmentNewResult();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewResultBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {

        readGames();

        initRecycler();
        binding.edtRankPosition.setText(String.valueOf(adapter.getItemCount() + 1));
        binding.btnWriteResult.setOnClickListener(v -> onClickWriteResult());
        binding.btnAddPlayer.setOnClickListener(v -> addPlayer());
        new DateInputMask(binding.edtDate);
    }

    private void initRecycler() {
        binding.rvRankings.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapter = new RankingsAdapter(new ArrayList<>(), true);
        binding.rvRankings.setAdapter(adapter);
        binding.rvRankings.setHasFixedSize(true);
    }

    private void onClickWriteResult() {
        String date = binding.edtDate.getText().toString();
        Game game = (Game) binding.spnGames.getSelectedItem();

        boolean hasError = false;
        if (date.isEmpty()) {
            hasError = true;
            Toast.makeText(getContext(), "Date is empty.", Toast.LENGTH_SHORT).show();
        }
        if (game == null) {
            hasError = true;
            Toast.makeText(getContext(), "Game isn't selected.", Toast.LENGTH_SHORT).show();
        }
        if (adapter.getData().size() < 2) {
            hasError = true;
            Toast.makeText(getContext(), "At least two players must be added", Toast.LENGTH_SHORT).show();
        }

        if (!hasError) {
            BoardBiz.saveNewResult(getContext(), this::clearFields, date, game, adapter.getData());
        }

    }

    private void clearFields() {
        binding.edtRankPosition.setText("1");
        binding.edtPlayer.setText("");
        adapter = new RankingsAdapter(new ArrayList<>(), true);
        binding.rvRankings.setAdapter(adapter);
    }

    public void readGames() {
        BoardBiz.getAllGames(getContext(), this::initSpinner);
    }

    private void initSpinner(List<Game> games) {
        binding.spnGames.setAdapter(new ArrayAdapter<>(getContext(), R.layout.spinner_item, R.id.textView0, games));
    }

    private void addPlayer() {
        Rank rank = new Rank();
        rank.PlayerName = binding.edtPlayer.getText().toString().trim();
        rank.Position = Integer.parseInt(binding.edtRankPosition.getText().toString());
        if (rank.PlayerName.isEmpty()) {
            Toast.makeText(getContext(), "Enter Player's Name.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (rank.Position < 1) {
            Toast.makeText(getContext(), "Enter a Valid Rank.", Toast.LENGTH_SHORT).show();
            return;
        }
        adapter.addToData(rank);
        binding.edtRankPosition.setText(String.valueOf(adapter.getItemCount() + 1));
        binding.edtPlayer.setText("");
    }
}