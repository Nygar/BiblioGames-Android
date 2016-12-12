package com.bibliogames.nygar.bibliogames.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bibliogames.nygar.bibliogames.R;

/**
 * Esta es la clase del fragment que tendra el splash
 * localizada en {@link com.bibliogames.nygar.bibliogames.view.activity.LoginActivity}
 */
public class SplashFragment extends Fragment {

    //Constructor
    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_splash, container, false);
        return view;
    }//Fin oncreate

}//Finclase
