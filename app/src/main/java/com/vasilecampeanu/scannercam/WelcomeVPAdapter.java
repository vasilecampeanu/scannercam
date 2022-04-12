package com.vasilecampeanu.scannercam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class WelcomeVPAdapter extends FragmentStateAdapter {

    private String[] titles = new String[] { "WPAGE01", "WPAGE02", "WPAGE03" };
    
    public WelcomeVPAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new WPAGE01();
            case 1:
                return new WPAGE02();
            case 2:
                return new WPAGE03();
        }
        
        return new WPAGE01();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
