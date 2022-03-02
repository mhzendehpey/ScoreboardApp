package com.mxz.board.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mxz.board.BoardBiz;
import com.mxz.board.entities.GameResult;
import com.mxz.board.ui.adapters.ResultsAdapter;
import com.mxz.board.ui.databinding.FragmentResultsBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentResults extends Fragment {

    FragmentResultsBinding binding;

    public FragmentResults() {
        // Required empty public constructor
    }

    public static FragmentResults newInstance() {
        return new FragmentResults();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultsBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        getResults();
    }

    private void getResults() {
        BoardBiz.getAllResults(getContext(), this::initRecyclerView);
    }

    private void initRecyclerView(List<GameResult> results) {
        binding.rvResults.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvResults.setHasFixedSize(true);
        binding.rvResults.setAdapter(new ResultsAdapter(results));
    }

}