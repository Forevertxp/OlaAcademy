package com.snail.olaxueyuan.ui.examination;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.ui.SuperFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamFragment extends SuperFragment {


    public ExamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exam, container, false);
    }


    @Override
    public void onClick(View v) {

    }
}