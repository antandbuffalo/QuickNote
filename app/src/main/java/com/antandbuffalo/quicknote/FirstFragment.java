package com.antandbuffalo.quicknote;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.antandbuffalo.quicknote.databinding.FragmentFirstBinding;
import com.antandbuffalo.quicknote.utilities.Constants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadAd();
        System.out.println("asdf" + Storage.getString(getContext(), Constants.STORAGE_KEY_TEXT, ""));
        binding.editTextTextMultiLine.setText(Storage.getString(getContext(), Constants.STORAGE_KEY_TEXT, ""));

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // This method is called before text is changed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // This method is called when text is being changed
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // This method is called after text has been changed
                String newText = editable.toString();
                Storage.putString(getContext(), Constants.STORAGE_KEY_TEXT, newText);
            }
        };

        binding.editTextTextMultiLine.addTextChangedListener(textWatcher);

//        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//            }
//        });
    }

    public void loadAd() {
        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);
    }

    public String getText() {
        return binding.editTextTextMultiLine.getText().toString();
    }

    public void clearText() {
        binding.editTextTextMultiLine.setText("");
        Storage.putString(getContext(), Constants.STORAGE_KEY_TEXT, "");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}