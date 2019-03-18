package com.example.homesecurityautomation;
import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;



public class CustomAdapter extends ArrayAdapter<Photo> {

    public CustomAdapter(Context context, int resourceid, List<Photo> photos) {
        super(context, resourceid, photos);
    }

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
}
