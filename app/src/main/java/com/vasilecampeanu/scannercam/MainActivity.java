package com.vasilecampeanu.scannercam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity 
{
    private TabLayout  tabLayout;
    private ViewPager2 viewPager;
    private MainVPAdapter mainVpAdapter;

    private String[] titlesMain = new String[] { "Home", "Docs", "Tools", "Settings"};
    
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Add main fragments
        addMainFragemnts();
        
        // TO DO: Aceasta functie trebuie apelata o singura data dupa ce aplicatia este intalata.
        // Recomandat ar fi ca utilizatorul sa poata alege daca vrea sa vada sau nu aplicatia la urmatoarea deschidere a aplicatiei.
         openWelcomePage();
    
        // Add fragments to tab layout
    }
    
    // Deschidem pagina de pornire
    public void openWelcomePage()
    {
        Intent intent = new Intent(this, WelcomePage.class);
        startActivity(intent);
    }

    // Add main fragments
    public void addMainFragemnts()
    {
        tabLayout = findViewById(R.id.mainTabLayout);
        viewPager = findViewById(R.id.mainPageViewer);

        mainVpAdapter = new MainVPAdapter(this);
        viewPager.setAdapter(mainVpAdapter);

        new TabLayoutMediator(tabLayout, viewPager, ((tab, position) -> tab.setText(titlesMain[position]))).attach();
    }
}