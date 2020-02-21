package com.example.hrhj.Add;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.hrhj.MainActivity;
import com.example.hrhj.R;

import java.io.File;


public class AddGalleryFragment extends Fragment {

    private static String picPath;
    private GridView gridView;
    private AddGalleryAdapter addGalleryAdapter;
    private Context context;
    private OnFragmentInteractionListener mListener;

    public static AddGalleryFragment newInstance(String path) {
        AddGalleryFragment addGalleryFragment = new AddGalleryFragment();
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("BitmapImage", bitmap);
//        addGalleryFragment.setArguments(bundle);
        Bundle bundle = new Bundle();
        bundle.putString(picPath, path);
        addGalleryFragment.setArguments(bundle);
        return addGalleryFragment;
    }

    public AddGalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
//        menu.findItem(android.R.id.home);
        menu.findItem(R.id.addText).setVisible(true);
        menu.findItem(R.id.done).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        switch(item.getItemId()) {
//            case android.R.id.home :
//                getActivity().onBackPressed();
//                ((MainActivity)context).bottomNavigation.setVisibility(View.VISIBLE);
//                return true;
            case R.id.addText:
//                transaction.add(R.id.frameLayout, new AddTextFragment().newInstance((Bitmap) getArguments().getParcelable("Bitmap"))).addToBackStack(null).commit();;
                transaction.add(R.id.frameLayout, new AddTextFragment()).addToBackStack(null).commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_add_gallery, container, false);

        File dir = new File(Environment.getExternalStorageDirectory(), "Pictures");
        picPath = dir.getPath();

        final ImageView imageView = view.findViewById(R.id.galleryPreview);
//        imageView.getLayoutParams().width = ((MainActivity)context).getHeight();
//        imageView.getLayoutParams().height = ((MainActivity)context).getHeight();
//        imageView.requestLayout();

        int height = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(height, height);
        imageView.setLayoutParams(params);
//        imageView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        addGalleryAdapter = new AddGalleryAdapter(this.getContext(), picPath);
        gridView = view.findViewById(R.id.gallery);
        gridView.setAdapter(addGalleryAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                imageView.setImageBitmap(addGalleryAdapter.getBitmap(position));
//                AddTextFragment.newInstance(addGalleryAdapter.getBitmap(position));
            }
        });

        return view;
    }

    public String getPicPath(int position) {
        return addGalleryAdapter.getItemPath(position);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
