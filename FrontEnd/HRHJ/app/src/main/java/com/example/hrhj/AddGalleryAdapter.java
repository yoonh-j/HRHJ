package com.example.hrhj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AddGalleryAdapter extends ArrayAdapter {

    public static class PicInfo {
        int picId;
        String picPath;
    }

    private ArrayList<PicInfo> picList;
    private LayoutInflater inflater;

    public AddGalleryAdapter(Context context, ArrayList<PicInfo> picList) {
        super(context, R.layout.fragment_add);
        this.picList = picList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return picList.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if(view == null) {
            view = inflater.inflate(R.layout.item_fragment_add_gallery, parent, false);
        }
        ImageView imageView = view.findViewById(R.id.galleryItem);

        PicInfo pic = picList.get(position);
        //imageView.setImageBitmap(ima);
        return view;
    }
}
