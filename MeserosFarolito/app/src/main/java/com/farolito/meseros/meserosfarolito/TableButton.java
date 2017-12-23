package com.farolito.meseros.meserosfarolito;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Hector on 14/11/2017.
 */

public class TableView extends android.support.v7.widget.AppCompatImageView{

    private static final int FIRST_COLOR = R.color.green;
    private static final int SECOND_COLOR = R.color.red;
    private static final int THIRD_COLOR = R.color.yellow;

    private static final int FIRST_STATE = 0;
    private static final int SECOND_STATE = 1;
    private static final int THIRD_STATE = 2;

    private final double mScale = 1.0;

    int[] colorRotation = {FIRST_COLOR, SECOND_COLOR, THIRD_COLOR};

    int state;

    Context mContext;

    public TableView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        this.setScaleType(ScaleType.FIT_XY);
        this.setImageResource(R.drawable.ic_table1);

        state = 0;
        this.setColorFilter( getResources().getColor( colorRotation[this.state] ) );

        this.setOnTouchListener(new View.OnTouchListener() {

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

    }

    public int getState() {
        return this.state;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (width > (int)((mScale * height) + 0.5)) {
            width = (int)((mScale * height) + 0.5);
        } else {
            height = (int)((width / mScale) + 0.5);
        }

        super.onMeasure(
                MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        );
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

        this.setColorFilter( getResources().getColor( colorRotation[this.state] ) );
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

}
