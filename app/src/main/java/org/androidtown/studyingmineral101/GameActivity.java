package org.androidtown.studyingmineral101;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
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

    TextView strQuizNum, strLife;
    TextView hardness, specificGravity, cleavage, crustalAbundance, economicValue;
    ImageView[] images;
    // private Bitmap[] bitmaps = new Bitmap[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);


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

        Collections.shuffle(deck);

        for (int i = 0; i < 4; i++)
            tmpDeck.add(deck.get(i));
        Collections.shuffle(tmpDeck);

        for (int i = 0; i < 4; i++) {
            Card card;
            card = tmpDeck.get(i);

            int drawableId = getResources().getIdentifier(card.getCardName(), "drawable", getPackageName());
            images[i] = (ImageView) findViewById(R.id.image+(i+1));
            images[i].setImageResource(drawableId);
        }
        hardness = (TextView) findViewById(R.id.hardness);
        specificGravity = (TextView) findViewById(R.id.specificGravity);
        cleavage = (TextView) findViewById(R.id.cleavage);
        crustalAbundance = (TextView) findViewById(R.id.crustalAbundance);
        economicValue = (TextView) findViewById(R.id.economicValue);
        answer = tmpDeck.get(0);
        hardness.setText("* " + category[0] + " : " + answer.getCardInfo(0));
        specificGravity.setText("* " + category[1] + " : " + answer.getCardInfo(1));
        cleavage.setText("* " + category[2] + " : " + answer.getCardInfo(2));
        crustalAbundance.setText("* " + category[3] + " : " + answer.getCardInfo(3));
        economicValue.setText("* " + category[4] + " : " + answer.getCardInfo(4));

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
