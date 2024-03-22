package com.example.hairstylerecommendation.views.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hairstylerecommendation.R;

import java.util.List;

public class UserImageAdapterLong extends BaseAdapter {

    private Context mContextLong;
    private List<Integer> mLongImageList;

    public UserImageAdapterLong(Context context, List<Integer> longImageList) {
        mContextLong = context;
        mLongImageList = longImageList;
    }

    @Override
    public int getCount() {
        return mLongImageList.size();
    }

    @Override
    public Object getItem(int position) {
        return mLongImageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridViewItem;

        if (convertView == null) {
            // Inflate the grid item layout if it's the first time
            LayoutInflater inflater = (LayoutInflater) mContextLong.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridViewItem = inflater.inflate(R.layout.griditemlayoutlong, null);

            // Adjust the size of the images by changing LayoutParams
            gridViewItem.setLayoutParams(new GridView.LayoutParams(270, 270)); // You can change the width and height as needed

            // Find the ImageView inside the inflated layout and set its scale type
            ImageView imageView = gridViewItem.findViewById(R.id.gridImageViewlong);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            gridViewItem = convertView;
        }

        ImageView imageView = gridViewItem.findViewById(R.id.gridImageViewlong);
        TextView textView = gridViewItem.findViewById(R.id.gridTextViewlong);

        // Set the image and text for the current position
        imageView.setImageResource(mLongImageList.get(position));
        imageView.setTag(mLongImageList.get(position)); // Store image resource ID in the tag

        // Set the text based on the image resource (you can customize this part)
        String imageName = getImageName(mLongImageList.get(position));
        textView.setText(imageName);

        return gridViewItem;
    }

    // Method to get the image name based on the image resource ID (customize as needed)
    public String getImageName(int imageResourceId) {
        switch (imageResourceId) {
            case R.drawable.asteriahair:
                return "Asteria Hair";
            case R.drawable.bigtopknot:
                return "Big Top Knot";
            case R.drawable.doublebraidedponytail:
                return "Double Braided PonyTail";
            case R.drawable.halfcrownbraid:
                return "Half Crown Braid";
            case R.drawable.longandlayered:
                return "Long and Layered";
            case R.drawable.longandwavy:
                return "Long and Wavy";
            case R.drawable.longcurlyhair:
                return "Long Curly Hair";
            case R.drawable.longhairwithbangs:
                return "Long Hair With Bangs";
            case R.drawable.lowmessyponytail:
                return "Low Messy PonyTail";
            case R.drawable.messyfishtail:
                return "Messy Fish Tail";

            default:
                return "Unknown";
        }
    }

    // Method to get the image description based on the image resource ID (customize as needed)
    public String getImageDescription(int imageResourceId) {
        switch (imageResourceId) {
            case R.drawable.asteriahair:
                return "Beautiful long and flowing hairstyle.";
            case R.drawable.bigtopknot:
                return "Stylish and voluminous top knot hairstyle.";
            case R.drawable.doublebraidedponytail:
                return "Chic ponytail with double braids.";
            case R.drawable.halfcrownbraid:
                return "Elegant half crown braid for long hair.";
            case R.drawable.longandlayered:
                return "Classic long and layered haircut.";
            case R.drawable.longandwavy:
                return "Gorgeous long and wavy hairstyle.";
            case R.drawable.longcurlyhair:
                return "Beautiful curls for long hair.";
            case R.drawable.longhairwithbangs:
                return "Long hair styled with bangs.";
            case R.drawable.lowmessyponytail:
                return "Casual and chic low messy ponytail.";
            case R.drawable.messyfishtail:
                return "Messy fishtail braid for a trendy look.";

            default:
                return "Unknown";
        }
    }
}
