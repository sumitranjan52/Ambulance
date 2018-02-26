package com.ambulance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImageSliderActivity extends AppCompatActivity implements View.OnClickListener {

    private Button login, signUp;
    private ViewPager imageSlider;
    int[] sliders = {R.layout.first_slide};
    private LinearLayout dotsLayout;
    private ImageViewPagerAdapter imageViewPagerAdapter;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);

        /* Object of ImageViewPagerAdapter */
        imageViewPagerAdapter = new ImageViewPagerAdapter(this, sliders);

        /* Button goes here below */
        login = (Button) findViewById(R.id.btnLogin);
        signUp = (Button) findViewById(R.id.btnSignUp);
        login.setOnClickListener(this);
        signUp.setOnClickListener(this);

        /* ViewPager goes here below */
        imageSlider = (ViewPager) findViewById(R.id.imageSlider);
        imageSlider.setAdapter(imageViewPagerAdapter);


        /* LinearLayout for adding dots at run times goes here */
        dotsLayout = (LinearLayout) findViewById(R.id.dotLayout);
        createDots(0);

        imageSlider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                createDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case R.id.btnSignUp:
                startActivity(new Intent(this,SignupActivity.class));
                break;
        }
    }

    private void createDots(int currentPosition) {
        if (dotsLayout != null) {
            dotsLayout.removeAllViews();
        }
        dots = new ImageView[sliders.length];
        for (int i = 0; i < sliders.length; i++) {
            dots[i] = new ImageView(this);
            if (i == currentPosition) {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_selected));
            } else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dots_default));
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(4, 0, 4, 0);
            dotsLayout.addView(dots[i], layoutParams);
        }
    }
}
