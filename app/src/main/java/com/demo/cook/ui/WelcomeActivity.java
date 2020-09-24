package com.demo.cook.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demo.cook.R;
import com.demo.cook.base.local.Storage;
import com.demo.cook.ui.user.login.LoginActivity;

public class WelcomeActivity extends AppCompatActivity {


    TextView tvWelcomeJump;
    CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        init();
        ImageView ivWelcomeLogo = findViewById(R.id.ivWelcomeLogo);

        Glide.with(this).asGif().load(R.drawable.welcome_logo).into(ivWelcomeLogo);


        tvWelcomeJump= findViewById(R.id.tvWelcomeJump);
        tvWelcomeJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.onFinish();
                timerCancel();//避免倒计时结束再跳一次
            }
        });
    }


    private void init() {
        countDownTimer=new CountDownTimer(5000,1000){
            @Override
            public void onTick(long l) {
                tvWelcomeJump.setText(getString(R.string.text_welcome_jump)+" "+l/1000);
            }
            @Override
            public void onFinish() {
                tvWelcomeJump.setEnabled(false);//避免倒计时结束再按按钮跳转一次
                timerCancel();
                gotoMain();
            }
        };
        countDownTimer.start();
    }

    /**
     * 当计时结束,跳转至主页面
     */
    private void gotoMain() {

        Intent intent = new Intent();
        if (Storage.getUserInfo()==null){
            intent.setClass(WelcomeActivity.this, LoginActivity.class);
        }else {
            intent.setClass(WelcomeActivity.this, MainActivity.class);
        }
        startActivity(intent);
        WelcomeActivity.this.finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        timerCancel();
    }
    private void timerCancel(){
        if(countDownTimer!=null){
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }



}