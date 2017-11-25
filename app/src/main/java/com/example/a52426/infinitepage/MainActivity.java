package com.example.a52426.infinitepage;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.a52426.infinitepage.view.LoopPageViewGroup;
import com.example.a52426.infinitepage.view.PageMove;
import com.example.a52426.infinitepage.view.PageView;

public class MainActivity extends AppCompatActivity {

    private LoopPageViewGroup lpvgContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lpvgContent = (LoopPageViewGroup) findViewById(R.id.lpvg_content);

        initPageView();
    }

    private void initPageView() {
        PageView preView = new PageView(this);
        preView.setText("1");
        preView.setBackColor(Color.RED);

        PageView currentView = new PageView(this);
        currentView.setText("2");
        currentView.setBackColor(Color.BLUE);

        PageView nextView = new PageView(this);
        nextView.setText("3");
        nextView.setBackColor(Color.GRAY);

        lpvgContent.setPageView(preView , currentView , nextView);
    }
}
