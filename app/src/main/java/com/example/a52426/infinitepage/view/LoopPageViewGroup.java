package com.example.a52426.infinitepage.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * 控制两个页面，同时对外提供一些方法调整界面的展示
 * Created by 52426 on 2017/11/23.
 */
public class LoopPageViewGroup extends ViewGroup implements View.OnTouchListener {

    private static final String TAG = LoopPageViewGroup.class.getSimpleName();

    private Context context;
    private PageView preView;
    private PageView currentView;
    private PageView nextView;
    private int height;
    private int width;
    private float downX;
    private PageMove pageMove;
    private int actionLock;

    public LoopPageViewGroup(Context context) {
        this(context , null);
    }

    public LoopPageViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public LoopPageViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        pageMove = new PageMoveTurning();
        setWillNotDraw(false);
        initChild();
        initListener();
    }

    private void initListener() {
        setOnTouchListener(this);
    }

    private void initChild() {
        preView = new PageView(context);
        preView.setText("1");
        preView.setLevelTag(PageMove.LEVEL_PRE);
        preView.setBackColor(Color.RED);

        currentView = new PageView(context);
        currentView.setText("2");
        currentView.setLevelTag(PageMove.LEVEL_CURR);
        currentView.setBackColor(Color.BLUE);

        nextView = new PageView(context);
        nextView.setText("3");
        nextView.setLevelTag(PageMove.LEVEL_NEXT);
        nextView.setBackColor(Color.GRAY);

        // 从下往上添加
        addView(nextView);
        addView(currentView);
        addView(preView);

        pageMove.initPage(this , null , null , null);
    }

    public PageView getPrePage(){
        return preView;
    }

    public PageView getCurrentPage(){
        return currentView;
    }

    public PageView getNextPage(){
        return nextView;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 只接受固定大小
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        for(int x = 0 ; x < getChildCount() ; ++ x){
            measureChild(getChildAt(x) , widthMeasureSpec , heightMeasureSpec);
        }
        setMeasuredDimension(widthMeasureSpec , heightMeasureSpec);
        pageMove.setParentWidth(width);
        Log.d(TAG , "width == > " + getMeasuredWidth());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 对子控件进行布局
        for(int x =0  ; x < getChildCount() ; ++ x){
            View v = getChildAt(x);
            if(v instanceof PageView){
                PageView pv = (PageView) v;
                if(pv.getLevelTag() == PageMove.LEVEL_PRE){
                    // 上一页
                    pv.layout(0 - preView.getWidth() , 0 , 0 , b);
                }else if(pv.getLevelTag() == PageMove.LEVEL_CURR){
                    // 当前页
                    pv.layout(0 , 0 , r , b);
                }else if(pv.getLevelTag() == PageMove.LEVEL_NEXT){
                    // 下一页
                    nextView.layout(0 , 0 , r , b);
                }
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction()== MotionEvent.ACTION_DOWN){
            downX = event.getX();
            return true;
        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            float moveX = event.getX();
            float offset = moveX - downX;

            if(actionLock == PageMove.MOVE_IDEL){
                if(offset < 0){
                    actionLock = PageMove.MOVE_NEXT;
                }else{
                    actionLock = PageMove.MOVE_PRE;
                }
            }

            if(actionLock == PageMove.MOVE_NEXT){
                pageMove.nextPage(offset);
            }else{
                pageMove.prePage(offset);
            }

            downX = moveX;

            return true;
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            Log.d(TAG , "over");
            actionLock = PageMove.MOVE_IDEL;
            pageMove.over();
            return true;
        }
        return false;
    }
}
