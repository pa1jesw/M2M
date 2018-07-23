package com.pawanjeswani.mm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pawanjeswani.mm.R;

public class sliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;

    public sliderAdapter(Context context) {
        this.context = context;
    }

    //list of images
    public int[] imgs_list = {
            R.drawable.intropg1,
            R.drawable.intropg2,
            R.drawable.intropg3
    };
    public String[] title_list={
            "Discover New Friends",
            "Explore Nice Restaurants",
            "Don't Eat Alone"
    };

    public String[] desc_list={
            "Lorem Ipsum is simply dummy text of the \n\t\tprinting and typesetting industry.",
            "Lorem Ipsum is simply dummy text of the \n\t\tprinting and typesetting industry.",
            "Lorem Ipsum is simply dummy text of the \n\t\tprinting and typesetting industry."
    };



    @Override
    public int getCount() {
        return desc_list.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(ConstraintLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slides,container,false);
        ConstraintLayout cl = view.findViewById(R.id.slideCLayout);
        ImageView ivImg = view.findViewById(R.id.ivSlide);
        TextView tvTitle = view.findViewById(R.id.tvdummy);
        TextView tvdesc = view.findViewById(R.id.tvdesc);
        ivImg.setImageResource(imgs_list[position]);
        tvTitle.setText(title_list[position]);
        tvdesc.setText(desc_list[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
