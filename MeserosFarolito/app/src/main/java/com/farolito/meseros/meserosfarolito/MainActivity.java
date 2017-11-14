package com.farolito.meseros.meserosfarolito;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int state = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView table = (ImageView) findViewById(R.id.table_iv);

        table.setClickable(true);

        table.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                view.performClick();

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        state++;
                        if(state >= 3)
                            state = 0;

                        switch (state){
                            case 0:
                                table.setColorFilter(getResources().getColor(R.color.green));
                                break;
                            case 1:
                                table.setColorFilter(getResources().getColor(R.color.yellow));
                                break;
                            case 2:
                                table.setColorFilter(getResources().getColor(R.color.red));
                                break;

                        }
                        table.setAlpha(1.0f);
                        break;
                    case MotionEvent.ACTION_UP:
                        view.performClick();
                        break;
                }
                    return true;
            }
        });
    }
}
