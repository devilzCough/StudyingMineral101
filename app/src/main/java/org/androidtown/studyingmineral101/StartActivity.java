package org.androidtown.studyingmineral101;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {

    EditText inputName;
    TextView alertView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);

        inputName = (EditText) findViewById(R.id.etName);
        alertView = (TextView) findViewById(R.id.alertView);
//        ActionBar actionBar = getActionBar();
//        if (actionBar != null) {
//            actionBar.hide();
//        }
    }

    public void onStartGameBtnClicked(View v) {

        String name = inputName.getText().toString();
        if (name.equals("")) {
            alertView.setText("input your name!!!");
        } else {
            alertView.setText("");
            Intent intent = new Intent(getApplicationContext(), GameActivity.class);
            intent.putExtra("name", name);
            startActivity(intent);
            finish();
        }
    }

    public void onCloseBtnClicked(View v) {
        finish();
    }
}
