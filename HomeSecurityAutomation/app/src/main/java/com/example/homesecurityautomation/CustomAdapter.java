package com.example.homesecurityautomation;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;


//This is an adapter class used to convert a list of photos into a format that can be displayed on the app for the user to easily view.

    public class CustomAdapter extends BaseAdapter{
        private Activity mContext;

        // Keep all Images in array
        public List<Photo> photoList;

        // Constructor
        public CustomAdapter(Pictures pics, List<Photo> photos) {
            this.mContext = pics;
            this.photoList = photos;
        }

        @Override
        public int getCount() {
            return photoList.size();
        }

        @Override
        public Object getItem(int position) {
            return photoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(mContext);
            //imageView.setImageResource(photoList.get(position).getPhotoURL());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
            return imageView;
        }

    }

    /*
    //constructor for adapter object
    public CustomAdapter(Context context, int resourceid, List<Photo> photos) {
        super(context, resourceid, photos);
    }

    //Gets the view that requires the adapters operations and returns a view that can be used to display the object.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if(row == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = vi.inflate(R.layout.photo_pic, null);
        }
        final Photo thisPhoto = getItem(position);
        final ImageView photo = row.findViewById(R.id.imageView);
        try{
            //Bitmap b = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), thisPhoto.getPhotoURL());
            //photo.setImageBitmap(b);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return row;
    }
    */

