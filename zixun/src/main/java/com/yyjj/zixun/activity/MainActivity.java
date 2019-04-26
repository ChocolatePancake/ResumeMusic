package com.yyjj.zixun.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yyjj.zixun.R;
import com.yyjj.zixun.fragment.ZixunFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zixun_activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.ll_content, ZixunFragment.newInstance()).commit();

    }
}
