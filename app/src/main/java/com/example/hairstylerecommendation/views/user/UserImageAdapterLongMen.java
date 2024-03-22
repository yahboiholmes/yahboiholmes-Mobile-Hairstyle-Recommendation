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

public class UserImageAdapterLongMen extends BaseAdapter {

    private Context mContextLongMen;
    private List<Integer> mLongMenImageList;

    public UserImageAdapterLongMen(Context context, List<Integer> longMenImageList) {
        mContextLongMen = context;
        mLongMenImageList = longMenImageList;
    }

    @Override
    public int getCount() {
        return mLongMenImageList.size();
    }

    @Override
    public Object getItem(int position) {
        return mLongMenImageList.get(position);
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
            LayoutInflater inflater = (LayoutInflater) mContextLongMen.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridViewItem = inflater.inflate(R.layout.griditemlayoutlongmen, null);

            // Adjust the size of the images by changing LayoutParams
            gridViewItem.setLayoutParams(new GridView.LayoutParams(270, 270)); // You can change the width and height as needed

            // Find the ImageView inside the inflated layout and set its scale type
            ImageView imageView = gridViewItem.findViewById(R.id.gridImageViewLongMen);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            gridViewItem = convertView;
        }

        ImageView imageView = gridViewItem.findViewById(R.id.gridImageViewLongMen);
        TextView textView = gridViewItem.findViewById(R.id.gridTextViewLongMen);

        // Set the image and text for the current position
        imageView.setImageResource(mLongMenImageList.get(position));
        imageView.setTag(mLongMenImageList.get(position)); // Store image resource ID in the tag

        // Set the text based on the image resource (you can customize this part)
        String imageName = getImageName(mLongMenImageList.get(position));
        textView.setText(imageName);

        return gridViewItem;
    }

    // Method to get the image name based on the image resource ID (customize as needed)
    String getImageName(int imageResourceId) {
        switch (imageResourceId) {
            case R.drawable.bluntfringe:
                return "Blunt Fringe";
            case R.drawable.dyedhair:
                return "Dyed Hair";
            case R.drawable.halflong:
                return "Half Long";
            case R.drawable.halfupmanbun:
                return "Half Up Man Bun";
            case R.drawable.halfupmanponytail:
                return "Half Up Man PonyTail";
            case R.drawable.highmanponytail:
                return "High Man PonyTail";
            case R.drawable.layeredlonghair:
                return "Layered Long Hair";
            case R.drawable.longandstraight:
                return "Long and Straight";
            case R.drawable.longcurlyhairwithundercut:
                return "Long Curly with UnderCut";
            case R.drawable.longhair:
                return "Long Hair";

            default:
                return "Unknown";
        }
    }

    // Method to get the image description based on the image resource ID (customize as needed)
    public String getImageDescription(int imageResourceId) {
        switch (imageResourceId) {
            case R.drawable.bluntfringe:
                return "Modern and stylish blunt fringe haircut for men.";
            case R.drawable.dyedhair:
                return "Dyed hair in a trendy and bold style.";
            case R.drawable.halflong:
                return "Half long hairstyle for a versatile look.";
            case R.drawable.halfupmanbun:
                return "Man bun styled with a half-up approach.";
            case R.drawable.halfupmanponytail:
                return "Casual and cool half-up ponytail for men.";
            case R.drawable.highmanponytail:
                return "High ponytail for a bold and confident appearance.";
            case R.drawable.layeredlonghair:
                return "Layered long hair for added texture and movement.";
            case R.drawable.longandstraight:
                return "Classic long and straight hairstyle.";
            case R.drawable.longcurlyhairwithundercut:
                return "Long curly hair paired with a modern undercut.";
            case R.drawable.longhair:
                return "Timeless and flowing long hair for men.";

            default:
                return "Unknown";
        }
    }
}
