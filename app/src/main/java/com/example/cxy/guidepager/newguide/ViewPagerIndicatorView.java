package com.example.cxy.guidepager.newguide;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by HaodaHw on 2017/6/27.
 * 自定义控件
 * ViewPager之类的页面滑动指示器,使用的时候只需要设置指示器高度，
 * 显示宽度和页面个数相关,注意请勿将RelativeLayout作为该控件的直接父布局，否则
 * 可能导致宽度显示不全
 * PS：页数从第0页开始
 */

public class ViewPagerIndicatorView extends View {
    private int mPageSelected = 0;   //默认选中第0页
    private int mPageCount = 3;      //默认3页
    private Paint mPaint;
    private int height;

    public void setPageCount(int pageCount) {
        mPageCount = pageCount;
        requestLayout();  //页面数量改变，重新请求布局  区别于invalidate,仅仅刷新页面
    }

    public ViewPagerIndicatorView(Context context) {
        this(context, null);
    }

    public ViewPagerIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.argb(255, 143, 143, 143));
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.FILL);//设置画笔填充样式 填充内部
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        height = getMeasureLength(heightMeasureSpec);
//        int width = (2*mPageCount-1) * height;  三个点长度距离+两个间隔点距离
        int width = (mPageCount - 1) * height + mPageCount * height;
        setMeasuredDimension(width, height); //决定了当前View的大小  onMeasure方法里必须调用该方法来保存测量结果
    }

    private int getMeasureLength(int height) {
        int mode = MeasureSpec.getMode(height);      //获得模式
        int size = MeasureSpec.getSize(height);     //获得大小
        int meaResult;                              //测量结果
        if (mode == MeasureSpec.EXACTLY) {          //强加给子view确切的值
            meaResult = size;                       //高度跟随布局中设置
        } else {
            meaResult = 20;                         //高度默认值为20
        }
        return meaResult;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < mPageCount; i++) {
            if (i == mPageSelected) {
                mPaint.setColor(Color.argb(255, 50, 205, 50)); //选中的点设置为绿色，未选中的点为灰色
                drawDots(canvas, i);
            } else {
                mPaint.setColor(Color.argb(255, 143, 143, 143));
                drawDots(canvas, i);
            }
        }
    }

    /**
     * 绘制点的方法
     */
    private void drawDots(Canvas canvas, int i) {
        float cx = i * height + (i + 1) * height - height / 2f;   //点与点之间的距离是点的直径
        canvas.drawCircle(cx, height / 2, height / 2, mPaint);
    }


    /**
     * 更新选中页面
     *
     * @param pageSelected 选中页面脚标
     */
    public void onPageSelectedUpdate(int pageSelected) {
        this.mPageSelected = pageSelected;
        postInvalidate(); //刷新页面指示器
    }
}
