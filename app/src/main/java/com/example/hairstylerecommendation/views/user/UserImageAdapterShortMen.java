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

public class UserImageAdapterShortMen extends BaseAdapter {

    private Context mContextShortMen;
    private List<Integer> mShortMenImageList;

    public UserImageAdapterShortMen(Context context, List<Integer> shortMenImageList) {
        mContextShortMen = context;
        mShortMenImageList = shortMenImageList;
    }

    @Override
    public int getCount() {
        return mShortMenImageList.size();
    }

    @Override
    public Object getItem(int position) {
        return mShortMenImageList.get(position);
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
            LayoutInflater inflater = (LayoutInflater) mContextShortMen.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridViewItem = inflater.inflate(R.layout.griditemlayoutshortmen, null);

            // Adjust the size of the images by changing LayoutParams
            gridViewItem.setLayoutParams(new GridView.LayoutParams(270, 270)); // You can change the width and height as needed

            // Find the ImageView inside the inflated layout and set its scale type
            ImageView imageView = gridViewItem.findViewById(R.id.gridImageViewShortMen);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            gridViewItem = convertView;
        }

        ImageView imageView = gridViewItem.findViewById(R.id.gridImageViewShortMen);
        TextView textView = gridViewItem.findViewById(R.id.gridTextViewShortMen);

        // Set the image and text for the current position
        imageView.setImageResource(mShortMenImageList.get(position));
        imageView.setTag(mShortMenImageList.get(position)); // Store image resource ID in the tag

        // Set the text based on the image resource (you can customize this part)
        String imageName = getImageName(mShortMenImageList.get(position));
        textView.setText(imageName);

        return gridViewItem;
    }

    // Method to get the image name based on the image resource ID (customize as needed)
    String getImageName(int imageResourceId) {
        switch (imageResourceId) {
            case R.drawable.barbers:
                return "Barbers";
            case R.drawable.brushedbackcleancut:
                return "Brushed Back Clean Cut";
            case R.drawable.buzzcut:
                return "Buzz Cut";
            case R.drawable.cleancutshortquiff:
                return "Clean Cut Short Quiff";
            case R.drawable.curledbangs:
                return "Curled Bangs";
            case R.drawable.disconnectedundercut:
                return "Disconnected Undercut";
            case R.drawable.easycomboverlook:
                return "Easy Comb Over Look";
            case R.drawable.fauxhawk:
                return "Faux Hawk";
            case R.drawable.militarycut:
                return "Military Cut";
            case R.drawable.moderncrewtrim:
                return "Modern Crew Trim";

            default:
                return "Unknown";
        }
    }

    // Method to get the image description based on the image resource ID (customize as needed)
    public String getImageDescription(int imageResourceId) {
        switch (imageResourceId) {
            case R.drawable.barbers:
                return "Classic short hairstyle for men with a timeless appeal.";
            case R.drawable.brushedbackcleancut:
                return "Neat and stylish brushed back haircut for a clean look.";
            case R.drawable.buzzcut:
                return "Short and low-maintenance buzz cut for a no-fuss style.";
            case R.drawable.cleancutshortquiff:
                return "Short quiff for a clean and modern look with a touch of sophistication.";
            case R.drawable.curledbangs:
                return "Short hairstyle with curled bangs for a playful and textured appearance.";
            case R.drawable.disconnectedundercut:
                return "Edgy disconnected undercut for a bold and modern statement.";
            case R.drawable.easycomboverlook:
                return "Effortless and stylish comb-over for a polished and refined appearance.";
            case R.drawable.fauxhawk:
                return "Trendy faux hawk hairstyle for men who want a stylish and edgy look.";
            case R.drawable.militarycut:
                return "Short and disciplined military-inspired cut for a clean and sharp appearance.";
            case R.drawable.moderncrewtrim:
                return "Contemporary crew cut for a polished and versatile style.";

            default:
                return "Unknown";
        }
    }
}
