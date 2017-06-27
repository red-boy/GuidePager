package com.example.cxy.guidepager.newguide;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HaodaHw on 2017/6/27.
 * <p>
 * 为了实现连续滑动，将数组count放大10000倍，实际上未放大1000View的个数，只是用了除数取余的方法
 * 由于ViewPager会缓存前后两个View,所以导致在个数小于3的时候会出现重复添加的情况，故如果内容页
 * 小于等于三，将数组放大到大于等于三即可（4,6），如果内容页大于3，则可直接使用不好理解
 */

public class HwPagerAdapter extends PagerAdapter {
    private List<ImageView> mImageViewList = new ArrayList<>();
    private int oldViewCount;
    private int newViewCount; //如果内容页小于等于三，放大倍数到大于3

    public HwPagerAdapter(Context context, List<Bitmap> imageViewList) {
        oldViewCount = imageViewList.size();
        List<Bitmap> newList = plusImageViewList(imageViewList);//将图片数量增加到>=4
        newViewCount = newList.size();
        for (int i = 0; i < newList.size(); i++) {
            ImageView imageview = new ImageView(context);
            imageview.setImageBitmap(newList.get(i));
            mImageViewList.add(imageview);
        }
    }

    /**
     * 扩大图片列表数量到 >=4 的状态
     */
    private List<Bitmap> plusImageViewList(List<Bitmap> imageViewList) {
        if (imageViewList.size() == 1) { //添加到4个数据
            for (int i = 0; i < 3; i++) {
                imageViewList.add(imageViewList.get(0));
            }
        }
        if (imageViewList.size() == 2) { //添加到4个数据
            imageViewList.add(imageViewList.get(0));
            imageViewList.add(imageViewList.get(1));
        }
        if (imageViewList.size() == 3) { //添加到6个数据
            imageViewList.add(imageViewList.get(0));
            imageViewList.add(imageViewList.get(1));
            imageViewList.add(imageViewList.get(2));
        }
        return imageViewList;
    }

    @Override
    public int getCount() {
        return oldViewCount * 1000;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mImageViewList.get(position % newViewCount), 0);
        return mImageViewList.get(position % newViewCount);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mImageViewList.get(position % newViewCount));
    }
}
