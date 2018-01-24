package org.androidtown.studyingmineral101;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mp;

    // AppManager appManager;
    FragmentManager fragmentManager;
    MainFragment mainFragment;
    RankingFragment rankingFragment;
    SettingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mp = MediaPlayer.create(this, R.raw.background);
        AppManager.getInstance().setBackgroundPlayer(mp);
        mp.setLooping(true);
        mp.start();

        mainFragment = new MainFragment();
        rankingFragment = new RankingFragment();
        settingFragment = new SettingFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.content, mainFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();

        switch(curId) {

            case R.id.menu_main:
                fragmentManager.beginTransaction().replace(R.id.content, mainFragment).commit();
                break;

            case R.id.menu_rank:
                fragmentManager.beginTransaction().replace(R.id.content, rankingFragment).commit();
                break;

            case R.id.menu_settings:
                fragmentManager.beginTransaction().replace(R.id.content, settingFragment).commit();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
