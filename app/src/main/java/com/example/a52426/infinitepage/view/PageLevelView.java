package com.example.a52426.infinitepage.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 52426 on 2017/11/24.
 */

public abstract class PageLevelView extends View{

    private int levelTag;

    public PageLevelView(Context context) {
        super(context);
    }

    public PageLevelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PageLevelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLevelTag(int levelTag){
        this.levelTag = levelTag;
    }

    public int getLevelTag(){
        return levelTag;
    }

}
