package org.androidtown.studyingmineral101;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;



public class IntroActivity extends AppCompatActivity {
    Button mainBtn;
    Button openTwitterBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_layout);

        mainBtn = (Button) findViewById(R.id.backToMainBtn);
        openTwitterBtn =(Button) findViewById(R.id.twitterBtn);
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        openTwitterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, TwitterActivity.class);
                startActivity(intent);
            }
        });

    }
}
