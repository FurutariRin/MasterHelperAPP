package com.example.m1.adapter.adapterUtilities;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class adapterUtilities {

    public static void genCover(ImageView imageView, String url, Context context) {

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context)
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(4000000)
                                .fitCenter()
                        //.error(R.mipmap.eeeee)//可以忽略
//                        .placeholder(R.mipmap)R//可以忽略
                )
                .load(url)
                .thumbnail(0.8f)
                .apply(RequestOptions.bitmapTransform((new RoundedCornersTransformation(50,0))))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }
}
