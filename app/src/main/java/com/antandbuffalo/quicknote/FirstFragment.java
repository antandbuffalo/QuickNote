package com.antandbuffalo.quicknote;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.antandbuffalo.quicknote.databinding.FragmentFirstBinding;
import com.antandbuffalo.quicknote.service.DataHolder;
import com.antandbuffalo.quicknote.service.QuickNoteResponse;
import com.antandbuffalo.quicknote.utilities.Constants;
import com.antandbuffalo.quicknote.utilities.Debouncer;
import com.antandbuffalo.quicknote.utilities.Util;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.IOException;

import retrofit2.Response;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private Debouncer debouncer = new Debouncer();
    private Boolean isSyncEnabled = false;
    private FetchData fetchData;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void refresh() {
        new FetchData().execute();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadAd();
        isSyncEnabled = Storage.getBoolean(getContext(), Constants.STORAGE_KEY_AUTO_SYNC, false);

        binding.editTextTextMultiLine.setText(Storage.getString(getContext(), Constants.STORAGE_KEY_TEXT, ""));

//        new FetchData().execute();
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
                if (isSyncEnabled) {
                    debouncer.debounce("sendData", new Runnable() {
                        @Override
                        public void run() {
                            Util.sendData(getActivity().getContentResolver(), getContext());
                        }
                    }, 2000);
                }
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

    @Override
    public void onResume() {
        super.onResume();
        isSyncEnabled = Storage.getBoolean(getContext(), Constants.STORAGE_KEY_AUTO_SYNC, false);
    }

    public class FetchData extends AsyncTask<Void, Void, QuickNoteResponse> {
        @Override
        protected QuickNoteResponse doInBackground(Void... voids) {
            DataHolder dataHolder = DataHolder.getDataHolder(getActivity().getApplicationContext());
            try {
                Response<QuickNoteResponse> response = dataHolder.apiService.getNote(Util.getUniqueId(getActivity().getContentResolver(), getActivity().getApplicationContext())).execute();
                if (response.isSuccessful()) {
                    return response.body();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(QuickNoteResponse data) {
            if (data != null && data.getText() != null && !data.getText().trim().equalsIgnoreCase("")) {
                binding.editTextTextMultiLine.setText(data.getText());
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Error getting the data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}