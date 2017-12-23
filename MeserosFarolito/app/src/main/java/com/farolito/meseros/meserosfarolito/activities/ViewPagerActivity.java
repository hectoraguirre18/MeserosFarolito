package com.farolito.meseros.meserosfarolito.activities;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import android.view.Window;
import android.view.WindowManager;

import com.farolito.meseros.meserosfarolito.fragments.TablesFragment;
import com.farolito.meseros.meserosfarolito.R;
import com.farolito.meseros.meserosfarolito.fragments.WaitlistFragment;
import com.farolito.meseros.meserosfarolito.customClasses.VerticalViewPager;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity implements  TablesFragment.OnFragmentInteractionListener, WaitlistFragment.OnFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private VerticalViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_view_pager);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        TablesFragment tablesFragment = new TablesFragment();
        WaitlistFragment waitlistFragment = new WaitlistFragment();

        List<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add(tablesFragment);
        fragmentList.add(waitlistFragment);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), fragmentList);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (VerticalViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
//        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());

    }

    @Override
    public void onFragmentInteraction(View view) {

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentList = new ArrayList<>();
        }

        public SectionsPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
