package com.leancloud.circle.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.leancloud.circle.R;
import com.leancloud.circle.fragment.CircleFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circle_activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.ll_content, CircleFragment.newInstance()).commit();
    }
}
