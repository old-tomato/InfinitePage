package com.example.a52426.infinitepage.view;

/**
 * Created by 52426 on 2017/11/23.
 */

public abstract class PageMove {

    protected PageLevelView currentPageView;
    protected PageLevelView prePageView;
    protected PageLevelView nextPageView;
    protected LoopPageViewGroup parent;

    protected int parentWidth;

    /**
     * 空闲
     */
    public static final int MOVE_IDEL = 0;
    /**
     * 上一页
     */
    public static final int MOVE_PRE = 1;
    /**
     * 下一页
     */
    public static final int MOVE_NEXT = 2;

    /**
     * 上一页标志
     */
    public static final int LEVEL_PRE = 1;
    /**
     * 当前页标志
     */
    public static final int LEVEL_CURR = 2;
    /**
     * 下一页标志
     */
    public static final int LEVEL_NEXT = 3;

    /**
     * 进行下一页的翻页，控件的获取默认从容器中获得
     * @param parent
     */
    abstract void initPage(LoopPageViewGroup parent, PageLevelView preView, PageLevelView currentView, PageLevelView nextView);

    /**
     * 进行下一页的翻页
     * @param offset 偏移量
     */
    abstract void nextPage(float offset);

    /**
     * 进行上一页的翻页
     * @param offset 偏移量
     */
    abstract void prePage(float offset);

    /**
     * 手指松开的那一刻调用
     */
    abstract void over();

    /**
     * 设置父布局的宽度
     * @param parentWidth
     */
    abstract void setParentWidth(int parentWidth);
}
