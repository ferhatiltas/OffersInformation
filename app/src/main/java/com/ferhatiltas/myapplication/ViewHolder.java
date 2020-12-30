package com.ferhatiltas.myapplication;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public  class ViewHolder extends RecyclerView.ViewHolder {
    View view;

    public ViewHolder.ClickListener mClickListener;

    public interface ClickListener {
        void onItemclick(View view, int position);

        void onItemLongclick(View view, int position);
    }

    public void setOnClickListener(ViewHolder.ClickListener clickListener) {
        mClickListener = clickListener;

    }

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
//        itemView.setClickable(false);

        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //  InformationList informationList=new InformationList();
//                informationList.progressBar.setVisibility(View.VISIBLE);
                if (Util.isClickable) {
                    System.out.println("if");
                    mClickListener.onItemclick(view, getAdapterPosition());
                } else {
                    System.out.println("else");
                }



            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongclick(view, getAdapterPosition());
                return false;
            }
        });
    }

    public void setDetails(Context context, String title, String image) {
        TextView mTitleTv = view.findViewById(R.id.rTitleView);
        ImageView mImageTv = view.findViewById(R.id.rImageView);

        mTitleTv.setText(title);

        Picasso.get().load(image).into(mImageTv);

        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        itemView.startAnimation(animation);
    }


}
