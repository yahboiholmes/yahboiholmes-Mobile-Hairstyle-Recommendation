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

public class UserImageAdapterShort extends BaseAdapter {

    private Context mContext;
    private List<Integer> mImageList;

    public UserImageAdapterShort(Context context, List<Integer> imageList) {
        mContext = context;
        mImageList = imageList;
    }

    @Override
    public int getCount() {
        return mImageList.size();
    }

    @Override
    public Object getItem(int position) {
        return mImageList.get(position);
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
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridViewItem = inflater.inflate(R.layout.griditemlayoutshort, null);

            // Adjust the size of the images by changing LayoutParams
            gridViewItem.setLayoutParams(new GridView.LayoutParams(270, 270)); // You can change the width and height as needed

            // Find the ImageView inside the inflated layout and set its scale type
            ImageView imageView = gridViewItem.findViewById(R.id.gridImageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            gridViewItem = convertView;
        }

        ImageView imageView = gridViewItem.findViewById(R.id.gridImageView);
        TextView textView = gridViewItem.findViewById(R.id.gridTextView);

        // Set the image and text for the current position
        imageView.setImageResource(mImageList.get(position));
        imageView.setTag(mImageList.get(position)); // Store image resource ID in the tag

        // Set the text based on the image resource (you can customize this part)
        String imageName = getImageName(mImageList.get(position));
        textView.setText(imageName);

        return gridViewItem;
    }

    // Method to get the image name based on the image resource ID (customize as needed)
    String getImageName(int imageResourceId) {
        switch (imageResourceId) {
            case R.drawable.pixieundercut:
                return "Pixie Undercut";
            case R.drawable.pixiefringe:
                return "Pixie Fringe";
            case R.drawable.pixiesidefringe:
                return "Pixie Side Fringe";
            case R.drawable.classicpixie:
                return "Classic Pixie";
            case R.drawable.pixiewithquiff:
                return "Pixie With Quiff";
            case R.drawable.sidepartedpixiecut:
                return "Side Part Pixie";
            case R.drawable.sleekpixiecut:
                return "Sleek Pixie";
            case R.drawable.bowlcutwihtbluntbangs:
                return "Bowl Cut with Blunt Bangs";
            case R.drawable.shortstraightbob:
                return "Straight Bob";
            case R.drawable.wispycurtain:
                return "Wispy Curtain";

            default:
                return "Unknown";
        }
    }

    // Method to get the image description based on the image resource ID (customize as needed)
    public String getImageDescription(int imageResourceId) {
        switch (imageResourceId) {
            case R.drawable.pixieundercut:
                return "Edgy and bold pixie undercut hairstyle.";
            case R.drawable.pixiefringe:
                return "Chic pixie hairstyle with a fringe.";
            case R.drawable.pixiesidefringe:
                return "Pixie cut with a stylish side fringe.";
            case R.drawable.classicpixie:
                return "Timeless classic pixie haircut.";
            case R.drawable.pixiewithquiff:
                return "Pixie cut with a fashionable quiff.";
            case R.drawable.sidepartedpixiecut:
                return "Elegant pixie cut with a side part.";
            case R.drawable.sleekpixiecut:
                return "Sleek and sophisticated pixie hairstyle.";
            case R.drawable.bowlcutwihtbluntbangs:
                return "Bold bowl cut with blunt bangs.";
            case R.drawable.shortstraightbob:
                return "Simple and stylish short straight bob.";
            case R.drawable.wispycurtain:
                return "Soft and wispy curtain bangs for a cute look.";

            default:
                return "Unknown";
        }
    }
}
