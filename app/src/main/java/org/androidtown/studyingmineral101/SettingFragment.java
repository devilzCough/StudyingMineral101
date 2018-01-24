package org.androidtown.studyingmineral101;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

/**
 * Created by iseungjin on 2018. 1. 23..
 */

public class SettingFragment extends Fragment {

    MediaPlayer mp;
    private Switch backgroundM;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_setting, container, false);

        mp = AppManager.getInstance().getBackgroundPlayer();
        mp.setLooping(true);

        backgroundM = rootView.findViewById(R.id.background);
        backgroundM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    mp.start();
                } else {
                    mp.pause();
                }
            }
        });

        return rootView;
    }
}
