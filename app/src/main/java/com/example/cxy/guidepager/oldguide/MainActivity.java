package com.example.cxy.guidepager.oldguide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cxy.guidepager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * app的引导页面:图片的循环轮播ViewPager
 */

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<View> mViewList = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private GuidePagerAdapter mPagerAdapter;

    private Button mButton;
    private ImageView[] dots;//存储小点图的数组

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mViewPager = (ViewPager) findViewById(R.id.mviewPager);
        mLayoutInflater = LayoutInflater.from(this);//获取到对象实例

        mViewList.add(mLayoutInflater.inflate(R.layout.guide1, null));
        mViewList.add(mLayoutInflater.inflate(R.layout.guide2, null));
        mViewList.add(mLayoutInflater.inflate(R.layout.guide3, null));

        mPagerAdapter = new GuidePagerAdapter(mViewList);
        mViewPager.setAdapter(mPagerAdapter);

        // TODO: 2017/6/26 测试平滑滑动
        int item = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % mViewList.size());
        mViewPager.setCurrentItem(item);

        // TODO: 2017/6/14 测试:布局文件添加属性 android:clipChildren="false"。仿魅族Banner的静态效果就实现了
//        mViewPager.setPageTransformer(false,new CustomTransformer());

        mButton = (Button) findViewById(R.id.startApp);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "跳转到app主界面", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        /**ViewPager页面滑动监听器*/
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("onPageScrolled", "发生监听事件," + "positionOffset:" + positionOffset + ",positionOffsetPixels:" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                int num = position % 3;
                setUnEnabled();
                switch (num) {
                    case 0:
                        Log.d("onPageSelected", "轮播到第一张图");
                        mButton.setVisibility(View.INVISIBLE);
                        dots[num].setEnabled(true);
                        break;
                    case 1:
                        Log.d("onPageSelected", "轮播到第二张图");
                        mButton.setVisibility(View.INVISIBLE);
                        dots[num].setEnabled(true);
                        break;
                    case 2:
                        Log.d("onPageSelected", "轮播到第三张图");
                        mButton.setVisibility(View.VISIBLE);
                        dots[num].setEnabled(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("ScrollStateChanged", "发生监听事件");
            }
        });

        /**页面初始化加载小点*/
        initDoits();
    }

    private void initDoits() {
        dots = new ImageView[]{(ImageView) findViewById(R.id.first), (ImageView) findViewById(R.id.second), (ImageView) findViewById(R.id.third)};

        setUnEnabled();

        dots[0].setEnabled(true);//初始化加载时，第一个点设置为亮

    }

    /**
     * 设置所有小点状态：灰色
     */
    private void setUnEnabled() {
        for (int i = 0; i < dots.length; i++) {
            dots[i].setEnabled(false);
        }
    }


    public static void SwitchToMain(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);

    }


}
