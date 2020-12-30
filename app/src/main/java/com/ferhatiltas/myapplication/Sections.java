package com.ferhatiltas.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.material.navigation.NavigationView;

public class Sections extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Context context = this;
    ProgressBar progressBarr;
    private AdView banner;
    private InterstitialAd interstitialAd;
    static Switch mySwitch;
    SharedPref sharedPref;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        progressBarr.setVisibility(View.INVISIBLE);
        sharedPref = new SharedPref(this);
        if (sharedPref.loadNightModeState() == true) {
            setTheme(R.style.darkTheme);
        } else setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sections);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = findViewById(R.id.drawer);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        View navigatin_drawer_image = navigationView.inflateHeaderView(R.layout.navigation_baslik);


        mySwitch = findViewById(R.id.idSwitch);
        if (sharedPref.loadNightModeState() == true) {
            mySwitch.setChecked(true);
        }
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sharedPref.setNightModeState(true);
                    restartApp();
                } else {
                    sharedPref.setNightModeState(false);
                    restartApp();
                }
            }
        });


        BannerAdvertisement();
        InterstitialAdAdvertisement();
        context = this;


        progressBarr = findViewById(R.id.pro_bar);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_item_night:
                if (mySwitch.isChecked() == true) {
                    mySwitch.setChecked(false);
                } else mySwitch.setChecked(true);

                break;
            case R.id.nav_item_share:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.ferhatiltas.bilgisunar");
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "İle Paylaş"));
                break;
            case R.id.nav_item_puan:
                String url1 = "https://play.google.com/store/apps/details?id=com.ferhatiltas.bilgisunar&fbclid=IwAR0MKq8jjuNkkWPUm-n2LR1MgiXrOX3E561iUO5n3EQSGg9Hw-2fw_MjRFI";
                Uri linkimiz1 = Uri.parse(url1); //butona vermek istediğimiz linki buraya yazıyoruz.
                Intent intentimiz1 = new Intent(Intent.ACTION_VIEW, linkimiz1);
                startActivity(intentimiz1);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void restartApp() {
        Intent intent = new Intent(getApplicationContext(), Sections.class);
        startActivity(intent);
        finish();
    }

    private void InterstitialAdAdvertisement() {
        MobileAds.initialize(this, "ca-app-pub-7031895079125878~9044897801");
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-7031895079125878/8244442717");// değiştir
        interstitialAd.loadAd(new AdRequest.Builder().build());

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {// Reklam yüklendiğinde çalışır
                Log.e("InterstitialAd", "onAdLoaded çalıştı");
            }

            @Override
            public void onAdFailedToLoad(int i) {// Hata olduğunda çalışır
                Log.e("InterstitialAd", "onAdFailedToLoad çalıştı");
            }

            @Override
            public void onAdOpened() {// Reklama tıklanıldığında reklam açıldığında çalışır
                Log.e("InterstitialAd", "onAdOpened çalıştı");
            }

            @Override
            public void onAdLeftApplication() {
                // REklama tıklanıldığında uygulamadan ayrılınca çalışır
                Log.e("InterstitialAd", "onAdLeftApplication çalıştı");
            }

            @Override
            public void onAdClosed() {
                // Reklama tıklayıp gittiğimiz sayfadana geri gelince
                Log.e("Banner", "onAdClosed çalıştı");
                interstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });

    }

    private void BannerAdvertisement() {
        MobileAds.initialize(this, "ca-app-pub-7031895079125878~9044897801");

        banner = findViewById(R.id.banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        banner.loadAd(adRequest);

        banner.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {// Reklam yüklendiğinde çalışır
                Log.e("Banner", "onAdLoaded çalıştı");
            }

            @Override
            public void onAdFailedToLoad(int i) {// Hata olduğunda çalışır
                Log.e("Banner", "onAdFailedToLoad çalıştı");
            }

            @Override
            public void onAdOpened() {// Reklama tıklanıldığında reklam açıldığında çalışır
                Log.e("Banner", "onAdOpened çalıştı");
            }

            @Override
            public void onAdLeftApplication() {
                // REklama tıklanıldığında uygulamadan ayrılınca çalışır
                Log.e("Banner", "onAdLeftApplication çalıştı");
            }

            @Override
            public void onAdClosed() {
                // Reklama tıklayıp gittiğimiz sayfadana geri gelince
                Log.e("Banner", "onAdClosed çalıştı");
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(Sections.this);
            builder.setMessage("Bilgilerden uzaklaşmak istediğinize emin misiniz ?");
            builder.setCancelable(true);
            builder.setNegativeButton("Evet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();

                }
            });
            builder.setPositiveButton("İptal", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();

                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }


    }

    public void section_onClick(View view) {

        Util.isClickable = false;

        Intent intent = new Intent(getApplicationContext(), InformationList.class);

        switch (view.getId()) {

            case R.id.idScience:
                interstitialAd.loadAd(new AdRequest.Builder().build());
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
                intent.putExtra("cardId", String.valueOf(R.id.idScience));

                break;
            case R.id.idHealth:
                interstitialAd.loadAd(new AdRequest.Builder().build());
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
                intent.putExtra("cardId", String.valueOf(R.id.idHealth));
                break;
            case R.id.idRelationship:
                interstitialAd.loadAd(new AdRequest.Builder().build());
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
                intent.putExtra("cardId", String.valueOf(R.id.idRelationship));
                break;
            case R.id.idIslam:
                interstitialAd.loadAd(new AdRequest.Builder().build());
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
                intent.putExtra("cardId", String.valueOf(R.id.idIslam));
                break;
            case R.id.idSport:
                interstitialAd.loadAd(new AdRequest.Builder().build());
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
                intent.putExtra("cardId", String.valueOf(R.id.idSport));
                break;
            case R.id.idThinkers:
                intent.putExtra("cardId", String.valueOf(R.id.idThinkers));
                break;
            case R.id.idCode:
                interstitialAd.loadAd(new AdRequest.Builder().build());
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
                intent.putExtra("cardId", String.valueOf(R.id.idCode));
                break;
            case R.id.idMusic:
                interstitialAd.loadAd(new AdRequest.Builder().build());
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
                intent.putExtra("cardId", String.valueOf(R.id.idMusic));
                break;
            case R.id.idPlanet:
                intent.putExtra("cardId", String.valueOf(R.id.idPlanet));
                break;
            case R.id.idMakeUp:
                interstitialAd.loadAd(new AdRequest.Builder().build());
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
                intent.putExtra("cardId", String.valueOf(R.id.idMakeUp));
                break;
            case R.id.idCareer:
                intent.putExtra("cardId", String.valueOf(R.id.idCareer));
                break;
            case R.id.idCulture:
                intent.putExtra("cardId", String.valueOf(R.id.idCulture));
                break;

        }

        startActivity(intent);
        //   progressBarr.setVisibility(View.VISIBLE);
    }


}