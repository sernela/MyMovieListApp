package com.example.bingewatchversion3;

import static com.example.bingewatchversion3.Slider_Adapter.*;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


import com.bumptech.glide.Glide;

import java.util.List;


public class Slider_Adapter extends PagerAdapter {

    Context context;
    List<Slider_Model> listItems;

    public Slider_Adapter(Context context, List<Slider_Model> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.slider_layout, null);

        ImageView sliderImage = view.findViewById(R.id.slider_image);

        Glide.with(context).load(listItems.get(position).getImageUrl()).into(sliderImage);
        container.addView(view);
        return view;
    }
}
