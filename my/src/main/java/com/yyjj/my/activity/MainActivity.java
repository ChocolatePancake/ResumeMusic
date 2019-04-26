package com.yyjj.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yyjj.my.R;
import com.yyjj.my.fragment.PersonalFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.ll_content, PersonalFragment.newInstance()).commit();

    }
}
