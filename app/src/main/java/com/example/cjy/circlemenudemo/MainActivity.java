package com.example.cjy.circlemenudemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.cjy.circlemenudemo.view.CircleMenuLayout;
import com.example.cjy.circlemenudemo.view.OnItemClickListener;

public class MainActivity extends AppCompatActivity {
    private CircleMenuLayout mCircleMenuLayout;
    private String[] mItemTexts = new String[] {
            "安全中心", "特色服务", "投资理财",
            "转账汇款", "我的账户", "信用卡"
    };

    private int[] mItemImgs = new int[] {
        R.drawable.home_mbank_1_normal, R.drawable.home_mbank_2_normal,
            R.drawable.home_mbank_3_normal, R.drawable.home_mbank_4_normal,
            R.drawable.home_mbank_5_normal, R.drawable.home_mbank_6_normal
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCircleMenuLayout = findViewById(R.id.id_menulayout);
        mCircleMenuLayout.setMenuItemIconAndTexts(mItemImgs, mItemTexts);
        mCircleMenuLayout.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View v, int index) {
                Toast.makeText(MainActivity.this, mItemTexts[index], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void itemCenterClick(View v) {

            }
        });
    }
}
