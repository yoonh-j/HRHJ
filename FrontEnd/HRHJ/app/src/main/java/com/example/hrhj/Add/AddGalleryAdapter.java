package com.example.hrhj.Add;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hrhj.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddGalleryAdapter extends BaseAdapter {

    private String picPath;
    private String[] picList;
    private Bitmap bitmap;
    private int width;
    private Context context;
    private DataSetObservable dataSetObservable = new DataSetObservable();

    public AddGalleryAdapter(Context context, String picPath, int width) {
        this.context = context;
        this.picPath = picPath;
        this.width = width;

        File file = new File(picPath);
        picList = file.list();
    }

    @Override
    public int getCount() {
        File file = new File(picPath);
        picList = file.list();
        return picList.length;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return position;
    }

    public String getItemPath(int position) {
        String path = picPath + File.separator + picList[position];
        return path;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        ImageView imageView;

        if (view == null) {
            imageView = new ImageView(context);
        } else {
            imageView = (ImageView) view;
        }

        bitmap = BitmapFactory.decodeFile(picPath + File.separator + picList[position]);

        int w = width / 4;
        Bitmap bm = ThumbnailUtils.extractThumbnail(bitmap, w, w);
        imageView.setImageBitmap(bm);
        imageView.setVisibility(ImageView.VISIBLE);
        imageView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, GridView.LayoutParams.MATCH_PARENT));
        imageView.setImageBitmap(bm);

        return imageView;
    }

    public Bitmap getBitmap(int position) {
        Bitmap bm = BitmapFactory.decodeFile(getItemPath(position));
        return bm;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        dataSetObservable.registerObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        dataSetObservable.unregisterObserver(observer);
    }

    @Override
    public void notifyDataSetChanged() {
        //super.notifyDataSetChanged();
        dataSetObservable.notifyChanged();
    }
}
