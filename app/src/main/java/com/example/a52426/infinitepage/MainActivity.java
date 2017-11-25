package com.example.a52426.infinitepage;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.a52426.infinitepage.view.LoopPageViewGroup;
import com.example.a52426.infinitepage.view.PageLevelView;
import com.example.a52426.infinitepage.view.PageMove;
import com.example.a52426.infinitepage.view.PageView;

public class MainActivity extends AppCompatActivity {

    private LoopPageViewGroup lpvgContent;

    private int count = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lpvgContent = (LoopPageViewGroup) findViewById(R.id.lpvg_content);

        initPageView();
    }

    private void initPageView() {
        PageView preView = new PageView(this);
        preView.setText("" + (count -1));
        preView.setBackColor(Color.RED);

        PageView currentView = new PageView(this);
        currentView.setText("" + count);
        currentView.setBackColor(Color.BLUE);

        PageView nextView = new PageView(this);
        nextView.setText("" + (count + 1));
        nextView.setBackColor(Color.GRAY);

        lpvgContent.setPageView(preView , currentView , nextView);

        lpvgContent.setOnPageChangeListener(new LoopPageViewGroup.OnPageChangeListener() {
            @Override
            public void onPageChange(int moveFlag, PageLevelView preView, PageLevelView currentView, PageLevelView nextView) {
                PageView pv = (PageView) preView;
                PageView cv = (PageView) currentView;
                PageView nv = (PageView) nextView;

                if(moveFlag == PageMove.MOVE_NEXT){
                    ++ count;
                    nv.setText(count + "");
                }
                if(moveFlag == PageMove.MOVE_PRE){
                    -- count;
                    pv.setText(count + "");
                }
            }
        });

    }
}
