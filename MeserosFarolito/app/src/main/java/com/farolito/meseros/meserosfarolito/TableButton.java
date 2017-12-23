package com.farolito.meseros.meserosfarolito;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TableLayout;

/**
 * Created by Hector on 14/11/2017.
 */

public class TableButton extends android.support.v7.widget.AppCompatImageView{

    private static final int FIRST_COLOR = R.color.green;
    private static final int SECOND_COLOR = R.color.red;
    private static final int THIRD_COLOR = R.color.yellow;

    private static final int FIRST_STATE = 0;
    private static final int SECOND_STATE = 1;
    private static final int THIRD_STATE = 2;

    private static final int paddingInDp = 2;

    private final double mScale = 1.0;

    int[] colorRotation = {FIRST_COLOR, SECOND_COLOR, THIRD_COLOR};
    int[] imageRotation = {R.drawable.ic_table2_green, R.drawable.ic_table2_red, R.drawable.ic_table2_yellow};

    int state;

    Context mContext;

    public TableButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        this.setScaleType(ScaleType.FIT_XY);
        this.setImageResource( imageRotation[0] );
//        this.setImageResource(R.drawable.round_corners);
//        this.setBackgroundColor(getContext().getResources().getColor(R.color.veryLight_green));

        state = 0;
//        this.setColorFilter( getResources().getColor( colorRotation[this.state] ) );
        this.setScaleType(ScaleType.FIT_CENTER);

        this.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                performClick();

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        press();
                        break;
                }
                return true;
            }
        });

        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (paddingInDp*scale + 0.5f);

        this.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);

    }

    public int getState() {
        return this.state;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);

        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }

    public void press(){

        switch (this.state) {
            case FIRST_STATE:
                this.state = SECOND_STATE;
                break;
            case SECOND_STATE:
                this.state = THIRD_STATE;
                break;
            case THIRD_STATE:
                this.state = FIRST_STATE;
                break;
            default:
                break;
        }

        this.setImageResource( imageRotation[this.state] );

        ///this.setColorFilter( getResources().getColor( colorRotation[this.state] ) );
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

}
