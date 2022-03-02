package com.mxz.board.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mxz.board.ui.databinding.ActivityMainBinding;
import com.mxz.board.ui.fragments.FragmentAccount;
import com.mxz.board.ui.fragments.FragmentGames;
import com.mxz.board.ui.fragments.FragmentNewResult;
import com.mxz.board.ui.fragments.FragmentResults;
import com.mxz.board.ui.fragments.FragmentTable;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FragmentManager fm;
    private FirebaseUser user;

    // region Overrides
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.getMenu().getItem(2).setEnabled(false);

        fm = getSupportFragmentManager();

        goToTable();

        checkUser();

        binding.fab.setOnClickListener(v -> onClickFab());

        binding.bottomNavigationView.setOnItemSelectedListener(this::onSelectionChanged);
    }

    @Override
    public void onBackPressed() {

        binding.fab.setVisibility(View.VISIBLE);
        binding.bottomAppBar.setVisibility(View.VISIBLE);

        if (binding.bottomNavigationView.getSelectedItemId() == R.id.table) {
            super.onBackPressed();
        } else {
            binding.bottomNavigationView.setSelectedItemId(R.id.table);
        }

    }

    // endregion

    // region Navigation
    private void goToTable() {
        FragmentTable fragment = FragmentTable.newInstance();
        fm.beginTransaction()
                .replace(binding.flFragments.getId(), fragment, "FragmentTable")
                .commit();
    }

    private void goToResults() {
        FragmentResults fragment = FragmentResults.newInstance();
        fm.beginTransaction()
                .replace(binding.flFragments.getId(), fragment, "FragmentResults")
                .commit();
    }

    private void goToGames() {
        FragmentGames fragment = FragmentGames.newInstance();
        fm.beginTransaction()
                .replace(binding.flFragments.getId(), fragment, "FragmentGames")
                .commit();
    }

    private void goToAccount() {
        FragmentAccount fragment = FragmentAccount.newInstance();
        fm.beginTransaction()
                .replace(binding.flFragments.getId(), fragment, "FragmentAccount")
                .commit();
    }
    // endregion

    // region User Interactions

    private boolean onSelectionChanged(MenuItem item) {

        if (item.getItemId() == R.id.table) {
            goToTable();
            return true;
        } else if (item.getItemId() == R.id.results) {
            goToResults();
            return true;
        } else if (item.getItemId() == R.id.games) {
            goToGames();
            return true;
        } else if (item.getItemId() == R.id.account) {
            goToAccount();
            return true;
        }

        return false;
    }

    private void onClickFab() {

        if (user == null) {
            binding.bottomNavigationView.setSelectedItemId(R.id.account);
        } else {
            binding.fab.setVisibility(View.INVISIBLE);
            binding.bottomAppBar.setVisibility(View.INVISIBLE);

            FragmentNewResult fragment = FragmentNewResult.newInstance();
            fm.beginTransaction()
                    .replace(binding.flFragments.getId(), fragment)
                    .addToBackStack(null)
                    .commit();
        }

    }

    public FirebaseUser checkUser() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            binding.fab.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                    R.drawable.ic_baseline_login_24,
                    getTheme()));
        } else {
            binding.fab.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                    R.drawable.ic_baseline_add_24,
                    getTheme()));
        }

        return user;
    }

    // endregion

}