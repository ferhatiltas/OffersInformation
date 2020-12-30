package com.ferhatiltas.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Details extends AppCompatActivity {
    TextView mtitle;
    ImageView mimage;
    private AdView bannerDetail,bannerDetail2;
   static Switch mySwitch;
    SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        if (sharedPref.loadNightModeState() == true) {
            setTheme(R.style.darkTheme);
        }else setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Sections as=new Sections();
        mySwitch=as.mySwitch;
        if (sharedPref.loadNightModeState() == true) {
            mySwitch.setChecked(true);
        }
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sharedPref.setNightModeState(true);
                    finish();
                }else {
                    sharedPref.setNightModeState(false);
                    finish();
                }
            }
        });

        BannerAdss();

        mtitle = findViewById(R.id.rTitleView2);
        mimage = findViewById(R.id.rImageView2);

        byte[] bytes = getIntent().getByteArrayExtra("image");
        String title = getIntent().getStringExtra("title");

        Bitmap btm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        mtitle.setText(title);
        mimage.setImageBitmap(btm);

    }

    private void BannerAdss() {
        MobileAds.initialize(this, "ca-app-pub-7031895079125878~9044897801");

        bannerDetail2 = findViewById(R.id.bannerDetails2);
        AdRequest adRequestt = new AdRequest.Builder().build();
        bannerDetail2.loadAd(adRequestt);

        bannerDetail = findViewById(R.id.bannerDetails);
        AdRequest adRequest = new AdRequest.Builder().build();
        bannerDetail.loadAd(adRequest);

        bannerDetail.setAdListener(new AdListener() {
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
                Log.e("Banner","onAdClosed çalıştı");
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}