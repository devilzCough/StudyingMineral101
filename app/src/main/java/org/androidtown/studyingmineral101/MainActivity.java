package org.androidtown.studyingmineral101;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

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
                Toast.makeText(this, "main selected", Toast.LENGTH_SHORT).show(); break;
            case R.id.menu_rank:
                Toast.makeText(this, "rank selected", Toast.LENGTH_SHORT).show(); break;
            case R.id.menu_settings:
                Toast.makeText(this, "settings selected", Toast.LENGTH_SHORT).show(); break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
