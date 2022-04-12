package com.vasilecampeanu.scannercam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity 
{

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // TO DO: Aceasta functie trebuie apelata o singura data dupa ce aplicatia este intalata.
        // Recomandat ar fi ca utilizatorul sa poata alege daca vrea sa vada sau nu aplicatia la urmatoarea deschidere a aplicatiei.
        openWelcomePage();
    }
    
    // deschidem pagina de pornire
    public void openWelcomePage()
    {
        Intent intent = new Intent(this, WelcomePage.class);
        startActivity(intent);
    }
}