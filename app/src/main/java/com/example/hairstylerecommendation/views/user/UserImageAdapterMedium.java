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

public class UserImageAdapterMedium extends BaseAdapter {

    private Context mContextMedium;
    private List<Integer> mMediumImageList;

    public UserImageAdapterMedium(Context context, List<Integer> mediumImageList) {
        mContextMedium = context;
        mMediumImageList = mediumImageList;
    }

    @Override
    public int getCount() {
        return mMediumImageList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMediumImageList.get(position);
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
            LayoutInflater inflater = (LayoutInflater) mContextMedium.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridViewItem = inflater.inflate(R.layout.griditemlayoutmedium, null);

            // Adjust the size of the images by changing LayoutParams
            gridViewItem.setLayoutParams(new GridView.LayoutParams(270, 270)); // You can change the width and height as needed

            // Find the ImageView inside the inflated layout and set its scale type
            ImageView imageView = gridViewItem.findViewById(R.id.gridImageViewMedium);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            gridViewItem = convertView;
        }

        ImageView imageView = gridViewItem.findViewById(R.id.gridImageViewMedium);
        TextView textView = gridViewItem.findViewById(R.id.gridTextViewMedium);

        // Set the image and text for the current position
        imageView.setImageResource(mMediumImageList.get(position));
        imageView.setTag(mMediumImageList.get(position)); // Store image resource ID in the tag

        // Set the text based on the image resource (you can customize this part)
        String imageName = getImageName(mMediumImageList.get(position));
        textView.setText(imageName);

        return gridViewItem;
    }

    private String getImageName(int imageResourceId) {
        switch (imageResourceId) {
            case R.drawable.flipmediumbob:
                return "Flip Medium Bob:";
            case R.drawable.blowoutbob:
                return "Blow Out Bob:";
            case R.drawable.bluntmediumbob:
                return "Blunt Medium Bob:";
            case R.drawable.braidedbob:
                return "Braided Bob:";
            case R.drawable.dyedmediumbob:
                return "Dyed Medium Bob:";
            case R.drawable.messylayeredbob:
                return "Messy Layered Bob: ";
            case R.drawable.beachwaveswithbangs:
                return "Beach Waves with Bangs: ";
            case R.drawable.sidesweep:
                return "Side Sweep:";
            case R.drawable.sleekandshiny:
                return "Sleek and Shiny:";
            case R.drawable.topknot:
                return "Top Knot:";

            default:
                return "Unknown";
        }
    }

    // Method to get the image description based on the image resource ID (customize as needed)
    public String getImageDescription(int imageResourceId) {
        switch (imageResourceId) {
            case R.drawable.flipmediumbob:
                return "Elegant and trendy medium-length bob with a flip.";
            case R.drawable.blowoutbob:
                return "Chic and voluminous bob styled with a blowout.";
            case R.drawable.bluntmediumbob:
                return "Sleek and sharp blunt cut for a sophisticated look.";
            case R.drawable.braidedbob:
                return "Bob hairstyle with a stylish braided detail.";
            case R.drawable.dyedmediumbob:
                return "Medium bob enhanced with vibrant hair dye.";
            case R.drawable.messylayeredbob:
                return "Casual and tousled bob with layered texture.";
            case R.drawable.beachwaveswithbangs:
                return "Medium bob styled with beach waves and trendy bangs.";
            case R.drawable.sidesweep:
                return "Medium bob with a fashionable side-swept look.";
            case R.drawable.sleekandshiny:
                return "Ultra-sleek and shiny medium bob for a polished appearance.";
            case R.drawable.topknot:
                return "Medium bob styled with a stylish top knot.";

            default:
                return "Unknown";
        }
    }
}
