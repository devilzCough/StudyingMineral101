package org.androidtown.studyingmineral101;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class ShowScoreActivity extends AppCompatActivity {

    Button openTwitterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showscore);

        openTwitterBtn =(Button) findViewById(R.id.twitterBtn);
        openTwitterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowScoreActivity.this, TwitterActivity.class);
                startActivity(intent);
            }
        });

    }

}

