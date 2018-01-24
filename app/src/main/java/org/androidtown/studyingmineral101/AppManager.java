package org.androidtown.studyingmineral101;

import android.media.MediaPlayer;

/**
 * Created by iseungjin on 2018. 1. 24..
 */

public class AppManager {
    private static AppManager appManager;

    private MainFragment mainFragment;
    // private RankingFragment rankingFragment;

    private static MediaPlayer mp;

    public static AppManager getInstance() {
        if(appManager == null) appManager = new AppManager();
        return appManager;
    }

    MediaPlayer getBackgroundPlayer() {
        return mp;
    }
    void setBackgroundPlayer(MediaPlayer _mp){
        mp = _mp;
    }

    public MainFragment getMainFragment() {
        return mainFragment;
    }
    public void setMainFragment(MainFragment floorFragment) {
        this.mainFragment = floorFragment;
    }

    /*public RankingFragment getRankingFragment() { return rankingFragment; }
    public void setRankingFragment(RankingFragment rankingFragment) {
        this.rankingFragment = rankingFragment;
    }*/
}
