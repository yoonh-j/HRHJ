package com.example.hrhj.Add;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.hrhj.MainActivity;
import com.example.hrhj.R;

import java.io.File;


public class AddGalleryFragment extends Fragment {

    private static String picPath;
    private GridView gridView;
    private AddGalleryAdapter addGalleryAdapter;
    private Bitmap bitmap;
    private Context context;
    private OnFragmentInteractionListener mListener;

    public static AddGalleryFragment newInstance(Bitmap bitmap) {
        AddGalleryFragment addGalleryFragment = new AddGalleryFragment();
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("BitmapImage", bitmap);
////        addGalleryFragment.setArguments(bundle);
//        Bundle bundle = new Bundle();
//        bundle.putString(picPath, path);
//        addGalleryFragment.setArguments(bundle);
        return addGalleryFragment;
    }

    public AddGalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
//        menu.findItem(android.R.id.home);
        menu.findItem(R.id.next).setVisible(true);
        menu.findItem(R.id.done).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        switch(item.getItemId()) {
//            case android.R.id.home :
//                getActivity().onBackPressed();
//                return true;
            case R.id.next:
                transaction.add(R.id.frameLayout, AddTextFragment.newInstance(bitmap)).addToBackStack(null).commit();
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

        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);

        final ImageView imageView = view.findViewById(R.id.galleryPreview);
        imageView.setLayoutParams(params);

        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/Camera");
        picPath = dir.getPath();

        addGalleryAdapter = new AddGalleryAdapter(this.getContext(), picPath, width);
        gridView = view.findViewById(R.id.gallery);
        gridView.setAdapter(addGalleryAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bitmap = addGalleryAdapter.getBitmap(position);
                imageView.setImageBitmap(bitmap);
            }
        });

        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
