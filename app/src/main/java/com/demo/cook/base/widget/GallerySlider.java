package com.demo.cook.base.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Scroller;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * 功能描述: 实现viewPager有限页数的 前后无缝 循环播放 与页码显示
 * 实现要求: 1不依赖项目文件 2不依赖图片加载器 3 对切换速度的调整
 * 实现思路:
 *  1 自定义 viewpager的 adapter 创建 N(原来页数) + 2* M(前后卡扣缓存页数, 最优为 2 页) 页pager
 *  2 adapter和PageChangeListener中计算原来应播放页数 index 与 现在实际页数 position 的切换关系
 * 实现功能: 轮播切换的速度设置 setPagerScrollSpeed(); 页码指示器的高度多样化。
 * 使用步骤:
 * 1 给控件设置adapter 2 数据更新 startPlaying() stopPlaying()
 */

public class GallerySlider extends FrameLayout {

    private ViewPager mViewPager;//轮播容器
    private LinearLayout pageNumLayout;//圆点页码指示布局

    /**
     * 控制自动轮播
     */
    private ScheduledExecutorService scheduledExecutorService;//定时器对象
    private Handler mHandler = new Handler();//定时的 消息处理器、设置当前显示页
    private Thread playThread = new Thread() {//定时切换的子线程
        @Override
        public void run() {
            synchronized (mViewPager) {
                mHandler.post(new Runnable() {//定时切换的post消息
                    public void run() {
                        int current =mViewPager.getCurrentItem();
                        mViewPager.setCurrentItem(current+1);
                    }
                });
            }
        }
    };

    /**
     * 构造方法
     */
    public GallerySlider(Context context) {
        this(context, null);
    }

    public GallerySlider(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GallerySlider(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initChildView(context);
    }

    /**
     * 初始化创建本控件的子view：ViewPager+ 页码布局LinearLayout
     */
    private synchronized void initChildView(Context context) {
        mViewPager = new ViewPager(context){
            // 让ViewPager 的高度 可以 WRAP_CONTENT
            @Override
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                int height = 0;
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                    int h = child.getMeasuredHeight();
                    if (h > height) {
                        height = h;//采用最大的view的高度。
                    }
                }
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        };
        this.addView(mViewPager);

        pageNumLayout = new LinearLayout(context);
        pageNumLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL));
        pageNumLayout.setGravity(Gravity.CENTER_VERTICAL);
        this.addView(pageNumLayout);
        /**
         * 手动滑动执行顺序: Changed-1 Changed-2(Selected) Scrolled(arg1=0.0) Changed-0
         * 自动滑动执行顺序: Changed-2(Selected) Scrolled(arg1=0.0) Changed-0
         * */
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             *  页面滑动状态改变方法回调：
             *   SCROLL_STATE_DRAGGING 1: 手指“按在屏幕上并且开始拖动”
             *   SCROLL_STATE_SETTLING 2: 在“手指离开屏幕” (紧接着onPageSelected)
             *   SCROLL_STATE_IDLE     0: 滑动动画完全做完时,比onPageScrolled(float arg1=0.0) 要稍晚
             * */
            @Override
            public void onPageScrollStateChanged(int state) {

                if(state== ViewPager.SCROLL_STATE_IDLE){
                    if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 2) {
                        mViewPager.setCurrentItem(2, false);
                    }
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //计算页码无察觉切换的关系
                if (mViewPager.getCurrentItem() == 1&&(positionOffsetPixels==0||position==0)) {
                    mViewPager.setCurrentItem(mViewPager.getAdapter().getCount() - 3, false);
                }
                if (position == mViewPager.getAdapter().getCount() - 2) {
                    mViewPager.setCurrentItem(2, false);
                }
            }

            @Override
            public void onPageSelected(int pos) {

                //计算index 与 position 的关系根据现实的页码改变页码圆点图片的状态
                int IMAGE_COUNT=mViewPager.getAdapter().getCount()-4;
                int index= (pos+ 2*IMAGE_COUNT-2)%IMAGE_COUNT;
                for (int i = 0; i < pageNumLayout.getChildCount(); i++) {
                    if(mViewPager.getAdapter() !=null){
                        ((GalleryAdapter)mViewPager.getAdapter()).handlePageView( pageNumLayout.getChildAt(i),i== index);
                    }
                }
            }
        });

    }

    /**
     * 自定义Adapter 继承自 PagerAdapter
     */
    public void setGalleryAdapter(GalleryAdapter adapter) {
        mViewPager.setAdapter(adapter);
    }

    public void notifyDataSetChanged() {
        if(mViewPager.getAdapter()==null&&mViewPager.getAdapter().getCount()==0){
            return;
        }
        mViewPager.getAdapter().notifyDataSetChanged();
        // 设置缓存页面，当前页面的相邻N各页面都会被缓存,相当于提前加载执行
        mViewPager.setOffscreenPageLimit(mViewPager.getAdapter().getCount());
        // 清除布局中的子视图,下面使用代码动态添加与图片对应的圆点
        pageNumLayout.removeAllViews();
        //迭代创建页码图片并逐一添加到页码布局  圆点个数与图片数量相等
        for(int i=0;i<mViewPager.getAdapter().getCount() - 4;i++){
            View pageView = new View(pageNumLayout.getContext());
            pageNumLayout.addView(pageView);
        }
        mViewPager.setCurrentItem(0);
        mViewPager.setCurrentItem(2);//初始化显示第1张
    }

    /**
     * 开始轮播图切换 注意生命周期
     */
    public void startPlaying(int millisecond) {
        if (scheduledExecutorService == null) {
            //多少秒之后每隔多少秒执行一次
            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            scheduledExecutorService.scheduleAtFixedRate(playThread, millisecond, millisecond, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 停止轮播释放资源
     */
    public void stopPlaying() {
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
    }

    /**
     * 使用反射往ViewPager中设置新的scroller对象 覆盖默认的滑动切换时间
     * @param millisecond 切换时间
     */
    public void setPagerScrollSpeed(final int millisecond) {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            Scroller scroller = new Scroller(mViewPager.getContext()) {
                @Override
                public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                    super.startScroll(startX, startY, dx, dy, millisecond);
                }
            };
            mScroller.set(mViewPager, scroller);
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
    }

    public ViewPager getViewPager(){
        return mViewPager;
    }

    public LinearLayout getPageNumLayout(){
        return pageNumLayout;
    }

    /**
     * adapter
     */
    public static abstract class GalleryAdapter extends PagerAdapter {
        List galleryList;

        public GalleryAdapter(List galleryList) {
            this.galleryList = galleryList;
        }

        @Override
        public int getCount() {
            return galleryList.size() > 0 ? galleryList.size() + 4 : 0;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            int index= (position+ 2*galleryList.size()-2)%galleryList.size();
            View pager= getItem(container,index);

            container.addView(pager);
            return pager;
        }

        public abstract View getItem(ViewGroup container, int index);
        public abstract void handlePageView(View pageView, boolean focus);
    }


}