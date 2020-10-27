package com.demo.cook.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.demo.cook.R;
import com.demo.cook.base.local.Storage;
import com.demo.cook.ui.like.LikeFragment;
import com.demo.cook.ui.classes.ClassesFragment;
import com.demo.cook.ui.home.HomeFragment;
import com.demo.cook.ui.me.MeFragment;
import com.demo.cook.ui.shop.ShopFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);


        findViewById(R.id.btMainZh).setVisibility(Storage.getZh()?View.VISIBLE:View.GONE);

        findViewById(R.id.btMainZh).setOnClickListener(v -> {
            Storage.setZh(false);
            changeLanguage();

        });

        navView.setOnNavigationItemSelectedListener(item -> {

            item.setChecked(false);
            switch (item.getItemId()){
                case R.id.navigation_home : switchFragment(0); break;
                case R.id.navigation_classes: switchFragment(1); break;
                case R.id.navigation_shop: switchFragment(2); break;
                case R.id.navigation_like: switchFragment(3); break;
                case R.id.navigation_me: switchFragment(4); break;
            }
            return true;
        });

        navView.setSelectedItemId(R.id.navigation_home);
    }

    private void changeLanguage(){

        //根据读取到的数据，进行设置
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale( Locale.ITALY);
        resources.updateConfiguration(configuration,displayMetrics);

        Intent intent =new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }



    Fragment[] fragments = new Fragment[]{
            new HomeFragment(),new ClassesFragment(),new ShopFragment(),new LikeFragment(),new MeFragment()
    };


    private void switchFragment(int index){
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        if (!fragments[index].isAdded()) {
            transaction.add(R.id.nav_host_fragment, fragments[index]);
        }
        for (int position=0;position<fragments.length;position++){
            Fragment fragment = fragments[position];
            if(index==position){
                transaction.show(fragment);
            }else {
                transaction.hide(fragment);
            }
        }
        transaction.commit();
    }


}