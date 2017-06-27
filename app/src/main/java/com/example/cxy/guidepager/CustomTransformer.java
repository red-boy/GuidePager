package com.example.cxy.guidepager;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by HaodaHw on 2017/6/14.
 * 自定义ViewPager动画切换效果
 */

public class CustomTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.9F;

    @Override
    public void transformPage(View page, float position) {
        if (position < -1) {
            page.setScaleY(MIN_SCALE);
        } else if (position <= 1) {
            Float scale = Math.max(MIN_SCALE, 1 - Math.abs(position));
            page.setScaleY(scale);
        }else {
            page.setScaleY(MIN_SCALE);
        }

    }
}
