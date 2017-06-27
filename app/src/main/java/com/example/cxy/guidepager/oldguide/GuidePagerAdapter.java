package com.example.cxy.guidepager.oldguide;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.List;

/**
 * Created by HaodaHw on 2017/2/23.
 * 自定义适配器:必须重写的四个方法
 */

public class GuidePagerAdapter extends PagerAdapter {
    private List<View> mViewList;

    public GuidePagerAdapter(List<View> viewList) {
        mViewList = viewList;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;/**让ViewPager可以左右循环轮播*/
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {//将当前视图添加到container中，再返回当前View
        position %= mViewList.size();

        View view = mViewList.get(position);
        ViewParent viewParent = view.getParent();
        if (viewParent != null) {
            ViewGroup parent = (ViewGroup) viewParent;/**让ViewPager可以左右循环轮播*/
            parent.removeView(view);
        }

        container.addView(view);
        Log.d("instantiateItem", "position:" + position);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        /**为解决ViewPager左右循环轮播时的报错，注释该方法的返回值*/
        container.removeView((View) object);
    }
}
