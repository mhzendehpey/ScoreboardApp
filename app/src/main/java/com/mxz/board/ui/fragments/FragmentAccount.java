package com.mxz.board.ui.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mxz.board.ui.MainActivity;
import com.mxz.board.ui.databinding.FragmentAccountBinding;

import java.util.Arrays;
import java.util.List;

public class FragmentAccount extends Fragment {

    private static final int RC_SIGN_IN = 668;
    FragmentAccountBinding binding;

    public FragmentAccount() {
        // Required empty public constructor
    }

    public static FragmentAccount newInstance() {
        return new FragmentAccount();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);

        binding.btnGoogleSignIn.setOnClickListener(v -> createSignInIntent());
        binding.btnSignOut.setOnClickListener(v -> onSignOut());

        init(checkUser());
        return binding.getRoot();
    }

    private void onSignOut() {
        FirebaseAuth.getInstance().signOut();
        init(((MainActivity) getActivity()).checkUser());
    }


    // region AuthUI

    public void createSignInIntent() {

// Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    public FirebaseUser checkUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                FirebaseUser user = ((MainActivity) getActivity()).checkUser();
                init(user);
            } else {
                init(null);
            }
        }
    }

    private void init(FirebaseUser user) {
        if (user == null) {
            binding.lnlNotSignedIn.setVisibility(View.VISIBLE);
            binding.lnlSignedIn.setVisibility(View.GONE);
        } else {
            binding.lnlNotSignedIn.setVisibility(View.GONE);
            binding.lnlSignedIn.setVisibility(View.VISIBLE);

            Glide.with(this)
                    .load(user.getPhotoUrl())
                    .override(145)
                    .centerCrop()
                    .transform(new CircleCrop())
                    .into(binding.ivProfile);

            binding.tvAccountName.setText(user.getDisplayName());


        }
    }

    // endregion
}