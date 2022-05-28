package com.leducanh.phongtro;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.leducanh.phongtro.Options.Options;

public class Main extends AppCompatActivity  {
    private BottomNavigationView mNavigationView;
    private ViewPager2 mViewpager;
    TextView tvtoolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.main);
        Init ();
        setupViewpager ();
    }
    private void setupViewpager(){
        ViewpagerAdapter viewpagerAdapter =  new ViewpagerAdapter (getSupportFragmentManager (),getLifecycle ());
        mViewpager.setAdapter (viewpagerAdapter);
        mViewpager.registerOnPageChangeCallback (new ViewPager2.OnPageChangeCallback () {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled (position, positionOffset, positionOffsetPixels);
//                mNavigationView.setOnNavigationItemSelectedListener (new BottomNavigationView.OnNavigationItemSelectedListener () {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        switch (item.getItemId ()){
//                            case R.id.actiom_upload:
//                               tvtoolbar.setText ("Dãy 1");
//                                mViewpager.setCurrentItem (0);
//                                break;
//                            case R.id.action_profile:
//                              tvtoolbar.setText ("Dãy 2");
//                                mViewpager.setCurrentItem (1);
//                                break;
//                        }
//                        return true;
//                    }
//                });
            }


            @Override
            public void onPageSelected(int position) {
                super.onPageSelected (position);
                switch (position){
                    case 0:
                        tvtoolbar.setText ("Dãy 1");
//                        mNavigationView.getMenu ().findItem (R.id.actiom_upload).setChecked (true);
                        break;
                    case 1:
                      tvtoolbar.setText ("Dãy 2");
//                        mNavigationView.getMenu ().findItem (R.id.action_profile).setChecked (true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged (state);
            }
        });
    }
    private void Init(){
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        tvtoolbar = getSupportActionBar().getCustomView().findViewById(R.id.tv_toolbar);
        mViewpager = findViewById (R.id.viewpager);
//        mNavigationView = findViewById (R.id.bottom_nav);
    }
}
