package com.ferhatiltas.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class InformationList extends AppCompatActivity {
    RecyclerView mRecyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    String data = "";
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;
    boolean isClickable = false;
    static Switch mySwitch;
    SharedPref sharedPref;
    public void setData(String data) {
        this.data = data;
        Util.isClickable=true;
    }

    public String getData() {
        return data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        if (sharedPref.loadNightModeState() == true) {
            setTheme(R.style.darkTheme);
        }else setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_list);

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



        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.activity_open_internet);
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;

            Button btn_try_again=dialog.findViewById(R.id.btnAgain);

            btn_try_again.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recreate();
                }
            });
            dialog.show();
        }else {

        }

        progressBar = findViewById(R.id.pro_bar2);
        progressBar.setVisibility(View.VISIBLE);

        mRecyclerView = findViewById(R.id.recyclerview);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setClickable(false);


        firebaseDatabase = FirebaseDatabase.getInstance();
        Intent myIntent = getIntent();

        String cardId = myIntent.getStringExtra("cardId");

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        if (Integer.parseInt(cardId) == R.id.idScience) {
            setData("scienceData");
        } else if (Integer.parseInt(cardId) == R.id.idHealth) {
            setData("healthData");
        } else if (Integer.parseInt(cardId) == R.id.idRelationship) {
            setData("relationshipData");
        } else if (Integer.parseInt(cardId) == R.id.idIslam) {
            setData("islamData");
        } else if (Integer.parseInt(cardId) == R.id.idSport) {
            setData("sportData");
        } else if (Integer.parseInt(cardId) == R.id.idThinkers) {
            setData("ideaData");
        } else if (Integer.parseInt(cardId) == R.id.idCode) {
            setData("codeData");
        } else if (Integer.parseInt(cardId) == R.id.idMusic) {
            setData("musicData");
        } else if (Integer.parseInt(cardId) == R.id.idPlanet) {
            setData("planetData");
        } else if (Integer.parseInt(cardId) == R.id.idMakeUp) {
            setData("makeUpData");
        } else if (Integer.parseInt(cardId) == R.id.idCareer) {
            setData("careerData");
        } else if (Integer.parseInt(cardId) == R.id.idCulture) {

            setData("cultureData");
        }
        reference = firebaseDatabase.getReference(getData());

    }



    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<RowDetails, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<RowDetails, ViewHolder>(

                        RowDetails.class,
                        R.layout.row_info,
                        ViewHolder.class,
                        reference

                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, RowDetails member, int i) {
                        viewHolder.setDetails(getApplicationContext(), member.getTitle(), member.getImage());

                      progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

                         ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {


                            @Override
                            public void onItemclick(View view, int position) {

                               progressBar.setVisibility(View.VISIBLE);

                                TextView mTitleTv = view.findViewById(R.id.rTitleView);
                                ImageView imageView = view.findViewById(R.id.rImageView);

                                String mTitle = mTitleTv.getText().toString();
                                Drawable mDrawble = imageView.getDrawable();
                                if (mDrawble == null) {
                                    return;
                                }
                                Bitmap mBitmap = ((BitmapDrawable) mDrawble).getBitmap();

                                Intent intent = new Intent(view.getContext(), Details.class);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                byte[] bytes = stream.toByteArray();

                                intent.putExtra("image", bytes);
                                intent.putExtra("title", mTitle);
                                startActivity(intent);

                            }

                            @Override
                            public void onItemLongclick(View view, int position) {
                            }
                        });
                        return viewHolder;
                    }
                };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);


    }


}
