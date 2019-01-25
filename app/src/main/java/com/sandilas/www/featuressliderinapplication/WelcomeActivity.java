package com.sandilas.www.featuressliderinapplication;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mPager;
    private int[] layouts={R.layout.first_slide,R.layout.second_slide,R.layout.third_slide,R.layout.fourth_slide};
    private MpagerAdapter mpagerAdapter;
    private LinearLayout dots_Layout;
    private ImageView[] dots;

    private Button bnNext;
    private Button bnSkip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(new PreferenceManager(WelcomeActivity.this).checkPreference()){
            loadHome();
        }
        if(Build.VERSION.SDK_INT>=19)
        {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_welcome);



        mPager=(ViewPager)findViewById(R.id.viewPager);
        mpagerAdapter=new MpagerAdapter(layouts,WelcomeActivity.this);
        mPager.setAdapter(mpagerAdapter);
        dots_Layout =(LinearLayout)findViewById(R.id.dotsLayout);
        bnNext=(Button)findViewById(R.id.bnNext);
        bnSkip=(Button)findViewById(R.id.bnSkip);
        bnNext.setOnClickListener(this);
        bnSkip.setOnClickListener(this);





        createDots(0);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
              createDots(i);
              if(i==layouts.length-1){
                  bnNext.setText("Start");
                  bnSkip.setVisibility(View.INVISIBLE);
              }else{
                  bnNext.setText("Next");
                  bnSkip.setVisibility(View.VISIBLE);
              }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }


    private void createDots(int current_position){
        if(dots_Layout!=null){
            dots_Layout.removeAllViews();

            dots=new ImageView[layouts.length];

            for (int i=0;i<layouts.length;i++)
            {
                dots[i]=new ImageView(this);
                if(i==current_position){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_dots));
                }
                else {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.default_dots));
                }

                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(4,0,4,0);
                dots_Layout.addView(dots[i],params);
            }
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
        case R.id.bnNext:
            loadNextSlide();
            break;
            case R.id.bnSkip:
                loadHome();
                new PreferenceManager(WelcomeActivity.this).writePreferences();
                break;

        }
    }

    private void loadHome(){
        startActivity(new Intent(getApplication(),MainActivity.class));
        finish();
    }

    private void loadNextSlide() {
      int next_slide= mPager.getCurrentItem()+1;
      if(next_slide<layouts.length){
          mPager.setCurrentItem(next_slide);
      }else{
          loadHome();
          new PreferenceManager(WelcomeActivity.this).writePreferences();
      }

    }
}
