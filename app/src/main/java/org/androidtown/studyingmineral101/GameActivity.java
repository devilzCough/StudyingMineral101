package org.androidtown.studyingmineral101;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GameActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    // My Instance
    private int quizNum, totalLife, chanceAtOnce;
    private String[] category = {"Hardness", "Specific Gravity", "Cleavage", "Crustal Abundance", "Economic Value"};

    private ArrayList<Card> deck;
    private ArrayList<Card> tmpDeck;
    private Card answer;
    private int answerNum;

    TextView strQuizNum, strLife;
    TextView hardness, specificGravity, cleavage, crustalAbundance, economicValue;
    ImageView[] images;

    // private Drawable digging;
    // private Bitmap[] bitmaps = new Bitmap[4];

    // Sensor..
    private SensorManager sm;
    private Display mDisplay; // 디스플레이 크기의 정보값을 받아옵니다.
    private WindowManager mWin; // 디스플레이의 context를 얻어온다. (방향 전환)
    private SensorEventListener accL; // 가속도
    private SensorEventListener gyroL; // 회전
    private Sensor accSensor; // 가속도
    private Sensor gyroSensor; // 회전
    private DiggingMineral image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        mWin = (WindowManager) getSystemService(WINDOW_SERVICE);
        mDisplay = mWin.getDefaultDisplay();
        // image = new DiggingMineral(this, null);
        image = (DiggingMineral) findViewById(R.id.picker);
        // setContentView(image);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        accSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        accL = new SensorListener();


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        //findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);

        // My Code
        quizNum = 1;
        totalLife = 20;
        chanceAtOnce = 2;

        strQuizNum = (TextView) findViewById(R.id.quizNum);
        strLife = (TextView) findViewById(R.id.nLife);

        images = new ImageView[4];
        // digging = getResources().getDrawable(R.drawable.dig);


        deck = new ArrayList<>();
        tmpDeck = new ArrayList<>();

        AssetManager am = getAssets() ;
        InputStream is = null ;

        try {
            is = am.open("cards.txt") ;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is)) ;
            String line;
            while ((line = bufferedReader.readLine()) != null) {

                String[] cardInformation = line.split(",");
                Card card;

                card = new Card(cardInformation[0], cardInformation[1], cardInformation[2], cardInformation[3],
                            cardInformation[4], cardInformation[5]);

                deck.add(card);
            }
            is.close() ;
        } catch (Exception e) {
            e.printStackTrace() ;
        }


        // Collections.shuffle(tmpDeck);

        /*for (int i = 0; i < 4; i++) {
            Card card;
            card = tmpDeck.get(i);

            int drawableId = getResources().getIdentifier(card.getCardName(), "drawable", getPackageName());
            images[i] = (ImageView) findViewById(R.id.image+(i+1));
            images[i].setImageResource(drawableId);
        }*/
        hardness = (TextView) findViewById(R.id.hardness);
        specificGravity = (TextView) findViewById(R.id.specificGravity);
        cleavage = (TextView) findViewById(R.id.cleavage);
        crustalAbundance = (TextView) findViewById(R.id.crustalAbundance);
        economicValue = (TextView) findViewById(R.id.economicValue);



        /*for (int i = 0; i < 4; i++) {
            if (answer.getCardName().equals(tmpDeck.get(i).getCardName()))
                answerNum = i + 1;
        }*/

        setQuiz();

    }

    public void setQuiz() {

        chanceAtOnce = 2;
        Collections.shuffle(deck);

        for (int i = 0; i < 4; i++) {
            tmpDeck.add(deck.get(i));

            Card card;
            card = tmpDeck.get(i);

            int drawableId = getResources().getIdentifier(card.getCardName(), "drawable", getPackageName());
            images[i] = (ImageView) findViewById(R.id.image+(i+1));
            images[i].setImageResource(drawableId);
        }

        answerNum = (int) (Math.random() * 4) + 1;
        answer = tmpDeck.get(answerNum-1);
        hardness.setText("* " + category[0] + " : " + answer.getCardInfo(0));
        specificGravity.setText("* " + category[1] + " : " + answer.getCardInfo(1));
        cleavage.setText("* " + category[2] + " : " + answer.getCardInfo(2));
        crustalAbundance.setText("* " + category[3] + " : " + answer.getCardInfo(3));
        economicValue.setText("* " + category[4] + " : " + answer.getCardInfo(4));

        image.setCoor();

        Log.d("answer", "" + answerNum);
    }

    public void setDashBoard() {

        quizNum++;
        strQuizNum.setText("Quiz : " + quizNum + "/ 15");

        for (int i = 0; i < 4; i++)
            tmpDeck.remove(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(accL, accSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sm != null) {
            sm.unregisterListener(accL);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sm != null) {
            sm.unregisterListener(accL);
        }
    }

    private class SensorListener implements SensorEventListener {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
                return;

            float x = image.getX();
            float y = image.getY();

            if ((260 < x && x < 440) && (10 < y && y < 190)) {
                // Log.d("image1", "x : " + x + ", y : " + y);
                if (answerNum == 1) {
                    Log.d("Result", "1 right!");

                    setDashBoard();
                    setQuiz();

                } else {
                    Log.d("Result", "1 wrong!");
                    chanceAtOnce--;
                    totalLife--;
                    strLife.setText("Life : " + totalLife + " / 20");

                    if (chanceAtOnce == 0) {
                        setDashBoard();
                        setQuiz();
                    } else {
                        image.setCoor();
                    }
                }
            } else if ((10 < x &&  x < 190) && (310 < y && y < 490)) {
                // Log.d("image2", "x : " + x + ", y : " + y);
                if (answerNum == 2) {
                    Log.d("Result", "2 right!");

                    setDashBoard();
                    setQuiz();

                } else {
                    Log.d("Result", "2 wrong!");
                    chanceAtOnce--;
                    totalLife--;
                    strLife.setText("Life : " + totalLife + " / 20");

                    if (chanceAtOnce == 0) {
                        setDashBoard();
                        setQuiz();
                    } else {
                        image.setCoor();
                    }
                }
            } else if ((460 < x &&  x < 690) && (310 < y && y < 490)) {
                // Log.d("image3", "x : " + x + ", y : " + y);
                if (answerNum == 3) {
                    Log.d("Result", "3 right!");

                    setDashBoard();
                    setQuiz();

                } else {
                    Log.d("Result", "3 wrong!");
                    chanceAtOnce--;
                    totalLife--;
                    strLife.setText("Life : " + totalLife + " / 20");

                    if (chanceAtOnce == 0) {
                        setDashBoard();
                        setQuiz();
                    } else {
                        image.setCoor();
                    }
                }
            } else if ((260 < x && x < 440) && (610 < y && y < 790)) {
                // Log.d("image4", "x : " + x + ", y : " + y);
                if (answerNum == 4) {
                    Log.d("Result", "4 right!");

                    setDashBoard();
                    setQuiz();

                } else {
                    Log.d("Result", "4 wrong!");
                    chanceAtOnce--;
                    totalLife--;
                    strLife.setText("Life : " + totalLife + " / 20");

                    if (chanceAtOnce == 0) {
                        setDashBoard();
                        setQuiz();
                    } else {
                        image.setCoor();
                    }
                }
            }

            switch (mDisplay.getRotation()) {
                case Surface.ROTATION_0:
                    image.move(event.values[SensorManager.DATA_X], event.values[SensorManager.AXIS_Y]);
                    break;
                case Surface.ROTATION_90:
                    image.move(-event.values[SensorManager.AXIS_X], event.values[SensorManager.AXIS_Y]);
                    break;
                case Surface.ROTATION_270:
                    image.move(event.values[SensorManager.AXIS_X], -event.values[SensorManager.AXIS_Y]);
                    break;
            }
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
