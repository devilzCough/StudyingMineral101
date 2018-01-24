package org.androidtown.studyingmineral101;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by iseungjin on 2018. 1. 24..
 */

public class RankingFragment extends Fragment {

    private ArrayList<Rank> ranks;

//    ArrayList<TextView> names;
//    ArrayList<TextView> scores;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //AppManager.getInstance().setRankingFragment(this);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_ranking, container, false);

        ranks = new ArrayList<>();
//        names = new ArrayList<>();
//        scores = new ArrayList<>();

        AssetManager am = getActivity().getAssets() ;
        InputStream is = null ;

        try {
            is = am.open("rank.txt") ;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is)) ;
            String line;
            while ((line = bufferedReader.readLine()) != null) {

                String[] rankInfomation = line.split(",");
                Rank rank;

                rank = new Rank(rankInfomation[0], rankInfomation[1], rankInfomation[2]);
                Log.d("rank", rankInfomation[0]+rankInfomation[1]+ rankInfomation[2]);
                ranks.add(rank);
            }
            is.close() ;
        } catch (Exception e) {
            e.printStackTrace() ;
        }

        for (int i = 0; i < 3; i++) {

            int nameId = getResources().getIdentifier("name" + (i+1), "id", getActivity().getPackageName());
            int scoreId = getResources().getIdentifier("score" + (i+1), "id", getActivity().getPackageName());
            TextView tvName = (TextView) rootView.findViewById(nameId);
            TextView tvScore = (TextView) rootView.findViewById(scoreId);

            Rank tmp = ranks.get(i);
            tvName.setText(tmp.getName());
            tvScore.setText(tmp.getScore() + " / 15");

//            names.add(tvName);
//            scores.add(tvScore);
        }

        return rootView;
    }
}
