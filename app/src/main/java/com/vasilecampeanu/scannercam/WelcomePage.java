package com.vasilecampeanu.scannercam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class WelcomePage extends AppCompatActivity 
{
    private TabLayout  tabLayout;
    private ViewPager2 viewPager;
    private WelcomeVPAdapter welcomeVpAdapter;

    private String[] titles = new String[] { "WPAGE01", "WPAGE02", "WPAGE03" };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        addWelcomeFragemnts();
    }
    
    public void addWelcomeFragemnts()
    {
        tabLayout = findViewById(R.id.welcomeTabLayout);
        viewPager = findViewById(R.id.welcomeViewPager);

        welcomeVpAdapter = new WelcomeVPAdapter(this);
        viewPager.setAdapter(welcomeVpAdapter);
        
        new TabLayoutMediator(tabLayout, viewPager, ((tab, position) -> tab.setText(titles[position]))).attach();
    }

    public void onClickBtnCloseWelcomeActivity(View view) 
    {
        this.finish();
    }
}