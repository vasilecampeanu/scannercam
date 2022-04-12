package com.vasilecampeanu.scannercam;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity 
{
    private TabLayout  tabLayout;
    private ViewPager2 viewPager;
    private MainVPAdapter mainVpAdapter;

    private String[] titlesMain = new String[] { "Home", "Docs", "Tools", "Settings"};

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 7;
    
    ImageView homeImageView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ask permsions
        checkAndroidVersion();
        
        // Add main fragments
        addMainFragemnts();
        
        // TO DO: Aceasta functie trebuie apelata o singura data dupa ce aplicatia este intalata.
        // Recomandat ar fi ca utilizatorul sa poata alege daca vrea sa vada sau nu aplicatia la urmatoarea deschidere a aplicatiei.
         openWelcomePage();
         
    }

    public void onClickBtnOpenCamera(View view)
    {
        ImagePicker.with(this)
            .saveDir(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "ImagePicker"))
            .cameraOnly()
            .crop()	    			                //Crop image(Optional), Check Customization for more option
            .compress(1024)			                //Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
            .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) 
    {
        super.onActivityResult(requestCode, resultCode, data);
        homeImageView = (ImageView)findViewById(R.id.homeImageView);
        Uri uri = data.getData();
        homeImageView.setImageURI(uri);
    }
    
    private void checkAndroidVersion()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkAndRequestPermissions();
        }
        else
        {
            // code for lollipop and pre-lollipop devices
        }
    }

    private boolean checkAndRequestPermissions()
    {
        int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int wtite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (wtite != PackageManager.PERMISSION_GRANTED)
        {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (camera != PackageManager.PERMISSION_GRANTED)
        {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }

        if (read != PackageManager.PERMISSION_GRANTED)
        {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (!listPermissionsNeeded.isEmpty())
        {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("in fragment on request", "Permission callback called-------");
        switch (requestCode)
        {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
            {

                Map<String, Integer> perms = new HashMap<>();

                // Initialize the map with both permissions
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);

                // Fill with actual results from user
                if (grantResults.length > 0)
                {
                    for (int i = 0; i < permissions.length; i++) perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                    {
                        Log.d("in fragment on request", "CAMERA & WRITE_EXTERNAL_STORAGE READ_EXTERNAL_STORAGE permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    }
                    else
                    {
                        Log.d("in fragment on request", "Some permissions are not granted ask again ");

                        // Permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
                        // ShouldShowRequestPermissionRationale will return true
                        // Show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.

                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showDialogOK("Camera and Storage Permission required for this app",
                                new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        switch (which)
                                        {
                                            case DialogInterface.BUTTON_POSITIVE:
                                                checkAndRequestPermissions();
                                                break;
                                            case DialogInterface.BUTTON_NEGATIVE:
                                                // Proceed with logic by disabling the related features or quit the app.
                                                break;
                                        }
                                    }
                                });
                        }

                        // Permission is denied (and never ask again is checked)
                        // ShouldShowRequestPermissionRationale will return false
                        else
                        {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        }
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener)
    {
        new AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", okListener)
            .create()
            .show();
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