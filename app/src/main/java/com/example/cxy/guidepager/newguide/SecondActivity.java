package com.example.cxy.guidepager.newguide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.cxy.guidepager.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SecondActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private ViewPagerIndicatorView mIndicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mIndicatorView = (ViewPagerIndicatorView) findViewById(R.id.pci_indicator);

        mViewPager.setAdapter(new HwPagerAdapter(SecondActivity.this, getBitmapList()));
        mViewPager.addOnPageChangeListener(this);//监听
        mIndicatorView.setPageCount(getBitmapList().size());//设置下标点的个数

        //设置间隔3秒自动切换  在这里使用RxJava的interval()方法实现
        Observable.interval(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())  //指定 subscribe() 发生在新线程（用来确定数据发射所在的线程）
                .observeOn(AndroidSchedulers.mainThread()) //指定 Subscriber 的回调发生在主线程 （observeOn 决定他下面的方法执行时所在的线程。）
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
                    }
                });
    }


    /**
     * 创建一个bitmap列表模拟数据
     *
     * @return bitmap列表
     */
    private List<Bitmap> getBitmapList() {
        List<Bitmap> bitMapLists = new ArrayList<>();
        int drawableInt[] = {R.drawable.bitmap01, R.drawable.bitmap02, R.drawable.bitmap03};
        for (int i = 0; i < 3; i++) {
            bitMapLists.add(BitmapFactory.decodeResource(getResources(), drawableInt[i]));//drawable资源转bitmap,注意OOM
        }

        return bitMapLists;
    }

    //通过在监听ViewPager页面切换时替换成自定义滑动指示器的方法，切换下标
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mIndicatorView.onPageSelectedUpdate(position % getBitmapList().size());   //根据位置切换下标
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
