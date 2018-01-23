package org.androidtown.studyingmineral101;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by iseungjin on 2018. 1. 21..
 */

public class MainFragment extends Fragment {

    Button btnStart;
    Button introBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        btnStart = rootView.findViewById(R.id.startBtn);
        introBtn = rootView.findViewById(R.id.introBtn);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ActionBar actionBar = getActivity().getActionBar();
//                if (actionBar != null) {
//                    actionBar.hide();
//                }

                Intent intent = new Intent(getActivity(), StartActivity.class);
                startActivity(intent);
            }
        });
        introBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IntroActivity.class);
                startActivity(intent);
            }
        });

        return rootView;

    }
}
