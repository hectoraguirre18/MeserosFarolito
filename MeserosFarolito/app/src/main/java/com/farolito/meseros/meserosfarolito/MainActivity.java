package com.farolito.meseros.meserosfarolito;

import android.animation.Animator;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TablesFragment.OnFragmentInteractionListener, WaitlistFragment.OnFragmentInteractionListener, AddingFragment.OnFragmentInteractionListener{

    private float y1,y2;
    static final int MIN_DISTANCE = 150;

    int state = 0;

    WaitlistFragment waitlistFragment;

    Boolean addingFragmentVisible = false;
    Boolean waitlistFragmentVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        waitlistFragment = new WaitlistFragment();

        setContentView(R.layout.activity_main);

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();

        ft.add(R.id.tables_container, new TablesFragment());

        ft.commit();

    }

    @Override
    public void onBackPressed() {
        if(addingFragmentVisible){
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            final android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();

            ((FrameLayout)findViewById(R.id.adding_container)).animate().scaleX(0f).scaleY(0f).setDuration(600).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    ft.replace(R.id.adding_container, new Fragment());
                    ft.commit();

                    addingFragmentVisible = false;
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });


        } else {
            if(waitlistFragmentVisible)
                waitlistFragmentVisible = false;
            super.onBackPressed();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                y2 = event.getY();
                float deltaY = y2 - y1;
                if (Math.abs(deltaY) > MIN_DISTANCE)
                {
                    if (y2 > y1)
                    {
                        if(waitlistFragmentVisible && !addingFragmentVisible){
                            onBackPressed();
                        }
                    }

                    // Right to left swipe action
                    else
                    {
                        if(!waitlistFragmentVisible)
                            openWaitlist();
                    }
                }
                else
                {
                    // consider as something else - a screen tap for example
                }
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        return super.onTouchEvent(event);
    }

    void openWaitlist(){
        waitlistFragmentVisible = true;

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();

        ft.setCustomAnimations(R.anim.enter_from_bottom, R.anim.enter_from_bottom
                , R.anim.exit_to_bottom, R.anim.exit_to_bottom);
        ft.add(R.id.waitlist_container, waitlistFragment);
        ft.addToBackStack(null);

        ft.commit();
    }

    @Override
    public void onFragmentInteraction(View view) {

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();

        switch (view.getId()) {

            case R.id.waitlist_fab:
                if(addingFragmentVisible)
                    break;

                addingFragmentVisible = true;

                findViewById(R.id.adding_container).setScaleX(1.0f);
                findViewById(R.id.adding_container).setScaleY(1.0f);

                ft.setCustomAnimations(R.anim.enter_by_zoom, R.anim.enter_by_zoom
                        , R.anim.exit_by_zoom, R.anim.exit_by_zoom);
                ft.replace(R.id.adding_container, new AddingFragment());

                ft.commit();
                break;
            case R.id.button_add_client:
                if(addingFragmentVisible) {
                    waitlistFragment.updateList();
                    onBackPressed();
                }
                break;
        }
    }
}
