package com.example.a52426.infinitepage.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 具体的页
 * Created by 52426 on 2017/11/23.
 */
public class PageView extends PageLevelView {

    private static final String TAG = PageView.class.getSimpleName();

    private int backColor;
    private int height;
    private int width;
    private TextPaint textPaint;
    private Paint paint;

    private String text;
//    private int levelTag;

    public PageView(Context context) {
        this(context , null);
    }

    public PageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public PageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);

        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(50);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = View.MeasureSpec.getSize(widthMeasureSpec);
        height = View.MeasureSpec.getSize(heightMeasureSpec);
        Log.d(TAG , "width : " + width + "   height : " + height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(backColor);
        // 绘制背景
        canvas.drawRect(0 , 0 , width , height , paint);

        // 绘制文字
        canvas.drawText(text , width / 2 , height / 2 , textPaint);
    }

    public void setBackColor(int backColor){
        this.backColor = backColor;
    }

    public void setText(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }

}
