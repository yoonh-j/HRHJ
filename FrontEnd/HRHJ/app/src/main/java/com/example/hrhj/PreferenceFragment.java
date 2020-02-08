package com.example.hrhj;


import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.preference.PreferenceFragmentCompat;

public class PreferenceFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.setShowHideAnimationEnabled(false);
        actionBar.hide();
        addPreferencesFromResource(R.xml.fragment_preference);
    }
}
