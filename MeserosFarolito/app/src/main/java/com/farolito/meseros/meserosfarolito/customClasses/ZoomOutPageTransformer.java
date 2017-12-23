package com.farolito.meseros.meserosfarolito.customClasses;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Hector on 10/12/2017.
 */

public class ZoomOutPageTransformer implements ViewPager.PageTransformer {

    public void transformPage(View view, float position) {
        view.setTranslationY(position < 0 ? 0f : view.getHeight() * position);
    }
}
