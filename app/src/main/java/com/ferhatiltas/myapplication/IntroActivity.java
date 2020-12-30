package com.ferhatiltas.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {
    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIndicator;
    Button btnNext,btn_skip,btnGetStarted;
    int position = 0;
    Animation btnAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        if (RestorePrefData()) {
            Intent intent = new Intent(getApplicationContext(), Sections.class);
            startActivity(intent);
            finish();
        }

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


//        getSupportActionBar().hide();
        btnNext = findViewById(R.id.btn_netxtt);
        btnNext = findViewById(R.id.btn_netxtt);
        btn_skip = findViewById(R.id.btn_skip);
        btnGetStarted = findViewById(R.id.btn_get_started);
        btnAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);


        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("MERHABA", "Bilgi Evi birbirinden farklı bilgileri\n" +
                "içeren bölümlerden oluşur.\n" +
                "Her alana özgü bilgiler içerir.\n" +
                "Hadi içeride neler olduğuna beraber bakalım.", R.drawable.giris_bilgi));
        mList.add(new ScreenItem("BİLİM", "Birbirinden farklı bilimsel bilgileri\n" +
                "sizler için Bilgi Evi'nde derledik.\n" +
                "Bilim butonu ile pek çok alanda \n" +
                "bilimsel bilgiye erişebilirsiniz.", R.drawable.giris_bilim));
        mList.add(new ScreenItem("İLİŞKİ", "İlişkinizin iyi yürümesini mi \n istiyorsunuz ? İlişki" +
                " bölümünde ilişkiye dair her şey mevcut.\n " +
                "Okumaktan keyif alacağınız \n bilgilerle İlişki " +
                "bölümü sizleri \n bekliyor.\n", R.drawable.giris_iliski));
        mList.add(new ScreenItem("MÜZİK", "Hala herhangi bir enstrümanı çalmaya başlamadınız mı ?\n" +
                "Bilgi Evi ile istediğiniz enstrüman hakkında rahatlıkla\n" +
                "bilgi sahibi olabilirsiniz.", R.drawable.giris_music));
        mList.add(new ScreenItem("SPOR", "Spor dalları hakkında bilgi sahibi olmak istiyorsanız" +
                "doğru \n yerdesiniz. Spor dallarını sizler için derledik." +
                "Spora dair herşey \n için Spor bilgilerini okuyunuz.", R.drawable.giris_spor));

        screenPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        screenPager.setAdapter(introViewPagerAdapter);
        tabIndicator=findViewById(R.id.tab_indicator);
        tabIndicator.setupWithViewPager(screenPager);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = screenPager.getCurrentItem();
                if (position < mList.size()) {
                    position++;
                    screenPager.setCurrentItem(position);
                }

                if (position == mList.size() - 1) {
                    loadLastScreen();

                }
            }
        });

        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size() - 1) {
                    loadLastScreen();

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Sections.class);
                startActivity(intent);
                SavePrefData();
                finish();
            }
        });
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Sections.class);
                startActivity(intent);
                SavePrefData();
                finish();
            }
        });
    }

    private boolean RestorePrefData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend", false);
        return isIntroActivityOpnendBefore;

    }

    private void SavePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend", true);
        editor.commit();
    }

    private void loadLastScreen() {
        btnNext.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        btn_skip.setVisibility(View.INVISIBLE);
        btnGetStarted.setAnimation(btnAnimation);
    }


}