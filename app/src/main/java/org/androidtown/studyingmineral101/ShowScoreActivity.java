package org.androidtown.studyingmineral101;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ShowScoreActivity extends AppCompatActivity {

    String name;
    int score;

    TextView scoreView;
    Button openTwitterBtn;

    AppManager appManager;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showscore);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        score = intent.getExtras().getInt("score");

        scoreView = (TextView) findViewById(R.id.scoreView);
        scoreView.setText(score + " / 15");

        openTwitterBtn =(Button) findViewById(R.id.twitterBtn);
        openTwitterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowScoreActivity.this, TwitterActivity.class);
                startActivity(intent);
            }
        });

    }

    public void onMainBtnClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        /*Fragment rankingFragment = new RankingFragment();
        // Fragment rankingFragment = appManager.getRankingFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content, rankingFragment).commit();*/
        finish();
    }

    public void onCloseBtnClicked(View v) {
//        MainFragment mainFragment = new MainFragment();
//
//        fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().add(R.id.content, mainFragment).commit();
        finish();
    }

}

