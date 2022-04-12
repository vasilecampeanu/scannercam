package com.vasilecampeanu.scannercam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class MainVPAdapter extends FragmentStateAdapter {

    private String[] titlesMain = new String[] { "Home", "Docs", "Tools", "Settings"};

    public MainVPAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new Home();
            case 1:
                return new Docs();
            case 2:
                return new Tools();
            case 3:
                return new Settings();
        }

        return new WPAGE01();
    }

    @Override
    public int getItemCount() {
        return titlesMain.length;
    }
}
