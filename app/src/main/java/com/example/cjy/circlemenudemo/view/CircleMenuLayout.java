package com.example.cjy.circlemenudemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cjy.circlemenudemo.R;

/**
 * Created by CJY on 2017/11/27.
 */

public class CircleMenuLayout extends ViewGroup {
    //圆形直径
    private int mRadius;
    //该容器内child item的默认尺寸
    private static final float RADIO_DEFAULT_CHILD_DIMENSION = 1 / 4f;
    //该容器的内边距，无视padding属性，如需边距请用该变量
    private static final float RADIO_PADDING_LAYOUT = 1 / 12f;
    //该容器的内边距，无视padding属性，如需边距请用该变量
    private float mPadding;
    //布局时的开始角度
    private double mStartAngle = 0;
    //菜单项文本
    private String[] mItemTexts;
    //菜单项图标
    private int[] mItemImgs;
    //菜单的个数
    private int mMenuItemCount;
    //菜单布局资源Id
    private int mMenuItemLayoutId = R.layout.circle_menu_item;
    //MenuItem的点击事件接口
    private OnItemClickListener mOnMenuItemClickListener;


    public CircleMenuLayout(Context context) {
        this(context, null);
    }

    public CircleMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
//        //无视padding
//        setPadding(0, 0, 0, 0);
    }

    public CircleMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //无视padding
        setPadding(0, 0, 0, 0);
    }

    public void setMenuItemIconAndTexts(int[] images, String[] texts) {
        if(images == null && texts == null) {
            throw new IllegalArgumentException("菜单项文本和图标至少设置其一");
        }

        mItemImgs = images;
        mItemTexts = texts;
        //初始化mMenuCount
        mMenuItemCount = images == null ? mItemTexts.length : mItemImgs.length;
        if(images != null && texts != null) {
            mMenuItemCount = Math.min(images.length, texts.length);
        }

        buildMenuItems();
    }

    private void buildMenuItems() {
        //根据用户设置的参数，初始化menu item
        for(int i = 0; i < mMenuItemCount; i++) {
            View itemView = inflateMenuView(i);
            //初始化菜单项
            initMenuItem(itemView, i);
            //添加View到容器中
            addView(itemView);
        }
    }

    private View inflateMenuView(final int childIndex) {
        LayoutInflater mInflater = LayoutInflater.from(getContext());
        View itemView = mInflater.inflate(mMenuItemLayoutId, this, false);
        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnMenuItemClickListener != null) {
                    mOnMenuItemClickListener.onClick(view, childIndex);
                }
            }
        });

        return itemView;
    }

    /**
     * 设置itemview中image和text
     *
     * @param itemView
     * @param childIndex
     */
    private void initMenuItem(View itemView, int childIndex) {
        ImageView iv = itemView.findViewById(R.id.id_circle_menu_item_image);
        TextView tv = itemView.findViewById(R.id.id_circle_menu_item_text);
        iv.setVisibility(VISIBLE);
        iv.setImageResource(mItemImgs[childIndex]);
        tv.setVisibility(VISIBLE);
        tv.setText(mItemTexts[childIndex]);
    }

    //设置MenuItem的布局文件，必须在setMenuItemIconAndTexts之前调用
    public void setMenuItemLayoutId(int mMenuItemLayoutId) {
        this.mMenuItemLayoutId = mMenuItemLayoutId;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnMenuItemClickListener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mesureMyself(widthMeasureSpec, heightMeasureSpec);
        measureChildViews();
    }

    private void measureChildViews() {
        //获得半径
        mRadius = Math.max(getMeasuredWidth(), getMeasuredHeight());
        //menu item数量
        final int count = getChildCount();
        //menu item尺寸
        int childSize = (int) (mRadius * RADIO_DEFAULT_CHILD_DIMENSION);
        //menu item测量模式
        int childMode = MeasureSpec.EXACTLY;
        //迭代测量
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if(child.getVisibility() == GONE) {
                continue;
            }
            //计算menu item的尺寸，以及设置好的模式，去对item进行测量
            int makeMeasureSpec = -1;
            makeMeasureSpec = MeasureSpec.makeMeasureSpec(childSize, childMode);
            child.measure(makeMeasureSpec, makeMeasureSpec);
        }

        mPadding = RADIO_PADDING_LAYOUT * mRadius;
    }

    private void mesureMyself(int widthMeasureSpec, int heightMeasureSpec) {
        int resWidth = 0;
        int resHeight = 0;
        //根据传入的参数， 分别获取测量模式和测量值
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);


        /**
         * 如果宽或者高的测量模式非精确值
         */
        if (widthMode != MeasureSpec.EXACTLY
                || heightMode != MeasureSpec.EXACTLY)
        {
            // 主要设置为背景图的宽度
            resWidth = getSuggestedMinimumWidth();
            // 如果未设置背景图片，则设置为屏幕宽高的默认值
            resWidth = resWidth == 0 ? getDefaultWidth() : resWidth;
            // 主要设置为背景图的高度
            resHeight = getSuggestedMinimumHeight();
            // 如果未设置背景图片，则设置为屏幕宽高的默认值
            resHeight = resHeight == 0 ? getDefaultWidth() : resHeight;
        } else
        {
            // 如果都设置为精确值，则直接取小值；
            resWidth = resHeight = Math.min(width, height);
        }

        setMeasuredDimension(resWidth, resHeight);
    }

    /**
     * 获得默认该layout的尺寸
     *
     * @return
     */
    private int getDefaultWidth()
    {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        final int childCount = getChildCount();
        int left, top;
        //menu item尺寸
        int itemWidth = (int) (mRadius * RADIO_DEFAULT_CHILD_DIMENSION);
        //根据menu item的个数,计算item的布局用的角度
        float angleDelay = 360 / (childCount - 1);
        //遍历所有菜单项，设置它们的位置
        for (int j = 0; j < childCount; j++) {
            final View child = getChildAt(j);
            if (child.getVisibility() == GONE) {
                continue;
            }
            //菜单项起始角度
            mStartAngle %= 360;
            //计算，中心点到menu item中心的距离
            float distanceFromCenter = mRadius / 2f - itemWidth / 2 - mPadding;
            //distanceFromCenter cosa即menu item中心点的left坐标
            left = (int) (mRadius / 2 + Math.round(distanceFromCenter * Math.cos(Math
                    .toRadians(mStartAngle))
                    - 1 / 2f * itemWidth));
            //distanceFromCenter sina即menu item的纵坐标
            top = (int) (mRadius / 2 + Math.round(distanceFromCenter * Math.sin(Math
                    .toRadians(mStartAngle)) - 1 / 2f * itemWidth));
            //布局child view
            child.layout(left, top, left + itemWidth, top + itemWidth);
            //叠加尺寸
            mStartAngle += angleDelay;
        }

        // 找到中心的view，如果存在设置onclick事件
        View cView = findViewById(R.id.id_circle_menu_item_center);
        if (cView != null) {
            cView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mOnMenuItemClickListener != null) {
                        mOnMenuItemClickListener.itemCenterClick(v);
                    }
                }
            });
            // 设置center item位置
            int cl = mRadius / 2 - cView.getMeasuredWidth() / 2;
            int cr = cl + cView.getMeasuredWidth();
            cView.layout(cl, cl, cr, cr);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
