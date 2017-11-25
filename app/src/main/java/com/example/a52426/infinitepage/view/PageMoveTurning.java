package com.example.a52426.infinitepage.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;

/**
 * Created by 52426 on 2017/11/23.
 */

public class PageMoveTurning extends PageMove {

    private static final String TAG = PageMoveTurning.class.getSimpleName();
    private int sumOffset;
    /**
     * 父布局的宽度
     */
    private int parentWidth;

    private int actionFlag = MOVE_IDEL;

    @Override
    public void initPage(LoopPageViewGroup parent, PageLevelView preView, PageLevelView currentView, PageLevelView nextView){
        this.parent = parent;

        if(preView != null){
            this.prePageView = preView;
        }else{
            this.prePageView = parent.getPrePage();
        }

        if(currentView != null){
            this.currentPageView = currentView;
        }else{
            this.currentPageView = parent.getCurrentPage();
        }

        if(nextView != null){
            this.nextPageView = nextView;
        }else{
            this.nextPageView = parent.getNextPage();
        }
    }

    void setParentWidth(int parentWidth) {
        this.parentWidth = parentWidth;
    }

    @Override
    public void nextPage(float offset) {
        if(offset > 0){
            // 大于0的动作忽略
            return;
        }
        boolean checkFlag = checkPage();
        if(!checkFlag){
            Log.e(TAG , "check flag failed");
            return;
        }
        actionFlag = MOVE_NEXT;
        sumOffset += offset;
        int newLeft = currentPageView.getLeft() + (int)offset;

        currentPageView.layout(newLeft , currentPageView.getTop() , newLeft + currentPageView.getWidth() , currentPageView.getBottom());
    }

    @Override
    public void prePage(float offset) {
        Log.d(TAG , "pre page " + prePageView.getLeft());
        boolean checkFlag = checkPage();
        if(!checkFlag){
            Log.e(TAG , "check flag failed");
            return;
        }
        // 向前翻一页，需要控制的是上一页的动作
        actionFlag = MOVE_PRE;
        sumOffset += offset;
        int newLeft = prePageView.getLeft() + (int)offset;

        prePageView.layout(newLeft , prePageView.getTop() , newLeft + prePageView.getWidth() , prePageView.getBottom());
    }

    private boolean checkPage() {
        return !(this.currentPageView == null || this.prePageView == null || this.nextPageView == null);
    }

    @Override
    public void over(){
        if(actionFlag == MOVE_NEXT){
            int[] location = new int[2];
            currentPageView.getLocationInWindow(location);
            Log.d(TAG , "  " + sumOffset + "   " + (parentWidth / 2) + "   " + location[0] + "   " + location[1]);
            if(Math.abs(sumOffset) < (parentWidth / 2)){
                // 滚回之前的位置
                scrollAnimator(currentPageView , "translationX"   , 0 , -(location[0]) , 300 , false).start();
            }else{
                // 需要进行翻页操作
                int needScroll = parentWidth - Math.abs(sumOffset);
                scrollAnimator(currentPageView , "translationX" , 0 , -needScroll , 300 , true).start();
            }
        }else if(actionFlag == MOVE_PRE) {
            int preLeftX = Math.abs(prePageView.getLeft());

            if(preLeftX < (parentWidth / 2)) {
                // 需要翻到上一页
                scrollAnimator(prePageView , "translationX" , 0 , parentWidth - sumOffset , 300 , true).start();
            }else{
                // 滚回之前的位置
                scrollAnimator(prePageView , "translationX" , 0 , -sumOffset , 300 , false).start();
            }
        }

    }

    private void afterAnimationEnd(int actionFlag){
        Log.d(TAG , "afterAnimationEnd");
        // 全局的actionFlag已经被清理了
        if(actionFlag == MOVE_NEXT){
            nextPageChange();
        }else if(actionFlag == MOVE_PRE){
            prePageChange();
        }

        if(parent.getOnPageChangeListener() != null){
            parent.getOnPageChangeListener().onPageChange(actionFlag , prePageView , currentPageView , nextPageView);
        }

    }

    /**
     * 上一页的问题
     */
    private void prePageChange() {
        Log.d(TAG , "pre page change");

        PageLevelView temp1 , temp2 , temp3;
        temp1 = prePageView;
        temp2 = currentPageView;
        temp3 = nextPageView;

        prePageView = temp3;
        currentPageView = temp1;
        nextPageView = temp2;

        resetFlagAndLevel();
    }

    /**
     * 处理界面的转换问题
     */
    private void nextPageChange() {
        Log.d(TAG  , "next page change");
        // 转换名称
        PageLevelView temp1 , temp2;
        temp1 = prePageView;
        temp2 = currentPageView;

        currentPageView = nextPageView;
        nextPageView = prePageView;
        prePageView = temp2;

        resetFlagAndLevel();

    }

    private void resetFlagAndLevel(){
        currentPageView.setLevelTag(PageMove.LEVEL_CURR);
        nextPageView.setLevelTag(PageMove.LEVEL_NEXT);
        prePageView.setLevelTag(PageMove.LEVEL_PRE);

        nextPageView.layout(0 , 0 , nextPageView.getMeasuredWidth() , nextPageView.getMeasuredHeight());

        // 先把当前页放在最上层
        currentPageView.bringToFront();
        // 再把上一页放在最上层
        prePageView.bringToFront();

        // ==========像这样子============
        // prePageView
        //     |
        // currentPageView
        //     |
        // nextPageView
        // ======================
    }

    /**
     * 进行界面最后的滚动动画
     * @param view 需要操作的控件
     * @param cmd 命令，一般是移动
     * @param start 开始的位置
     * @param end 结束的位置
     * @param duration 持续的时间
     * @param willCallback 是否调用后续的函数
     * @return
     */
    private ValueAnimator scrollAnimator(View view , String cmd , int start , int end , int duration , final boolean willCallback){
        final View finalView = view;
        final int finalViewLeft = finalView.getLeft();

        final int[] location = new int[2];
        view.getLocationInWindow(location);

        ValueAnimator animator = ValueAnimator.ofFloat(start , end);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int newLeft = (int) (finalViewLeft + (float)animation.getAnimatedValue());
                finalView.layout(newLeft , 0 , newLeft + finalView.getMeasuredWidth() , finalView.getHeight());
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // 复位操作
                sumOffset = 0;
                int tempFlag = actionFlag;
                actionFlag = MOVE_IDEL;

                if(willCallback){
                    afterAnimationEnd(tempFlag);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return animator;
    }

}
