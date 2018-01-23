package org.androidtown.studyingmineral101;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by iseungjin on 2018. 1. 23..
 */

public class DiggingMineral extends View {

    private Drawable digging;

    private int viewWidth;
    private int viewHeight;
    private int imageWidth;
    private int imageHeight;
    private float x;
    private float y;

    public DiggingMineral(Context context, AttributeSet attrs) {
        super(context, attrs);
        digging = this.getResources().getDrawable(R.drawable.dig);

        //x = 320;
        //y = 370;
    }

    public float getX() { return x; }
    public float getY() { return y; }

    public void setCoor() {
        x = 0;
        y = 800;
    }


    public void move(float mx, float my) {
        this.x -= mx;
        this.y += my;
//        Log.i("viewWidth =", viewWidth + "");
//        Log.i("viewHeight =", viewHeight + "");
        if (this.x < 0) { this.x = 0; }
        else if ((this.x + imageWidth) > this.viewWidth) {
            this.x = this.viewWidth - imageWidth;
        }

        if (this.y < 0) { this.y = 0; }
        else if ((this.y + imageHeight) > this.viewHeight) {
            this.y = this.viewHeight - imageHeight;
        }
        //Log.i("SENSOR", "move-[this.x]" + x + "[this.y]" + y + "[mx]" + mx + "[my]" + my);
        this.invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//화면을 보여줄 view의 너비를 구해줌.
        this.measure(MeasureSpec.UNSPECIFIED,MeasureSpec.UNSPECIFIED);

        this.viewWidth = this.getWidth();
        this.viewHeight = this.getHeight();
        Log.i("viewWidth =", viewWidth + "");
        Log.i("viewHeight =", viewHeight + "");
        // viewHeight = 500;

        imageWidth = digging.getIntrinsicWidth();
        imageHeight = digging.getIntrinsicHeight();

        super.onSizeChanged(w, h, oldw, oldh);
//부모클래스에 있는 메서드를 불러 줌.
    }

    @Override
    protected void onDraw(Canvas canvas) {
//이미지를 그려줌.

        int xx = (int) (this.x);
        int yy = (int) (this.y);

        digging.setBounds(xx, yy, xx + imageWidth, yy + imageHeight);
        digging.draw(canvas);

        super.onDraw(canvas);

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dig);
//        canvas.drawBitmap(bitmap, 200, 500, null);

    }
}
