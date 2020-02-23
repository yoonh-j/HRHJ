package com.example.hrhj.Add;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;

import com.example.hrhj.R;

public class AddBasicFragment extends Fragment {

    private Bitmap bitmap;
    private int id;
    private OnFragmentInteractionListener mListener;

    public static AddBasicFragment newInstance() {
        return new AddBasicFragment();
    }
    public AddBasicFragment() {
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
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        final View view = inflater.inflate(R.layout.fragment_add_basic, container, false);

        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        int height = width / 3;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);

//        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
//        radioGroup.setLayoutParams(params);

        LinearLayout linearLayout1 = view.findViewById(R.id.addBasic_linearLayout1);
        linearLayout1.setLayoutParams(params);
        LinearLayout linearLayout2 = view.findViewById(R.id.addBasic_linearLayout2);
        linearLayout2.setLayoutParams(params);
        LinearLayout linearLayout3 = view.findViewById(R.id.addBasic_linearLayout3);
        linearLayout3.setLayoutParams(params);

        final RadioButton red = view.findViewById(R.id.basicColor_red);
        final RadioButton orange = view.findViewById(R.id.basicColor_orange);
        final RadioButton yellow = view.findViewById(R.id.basicColor_yellow);
        final RadioButton green = view.findViewById(R.id.basicColor_green);
        final RadioButton blue = view.findViewById(R.id.basicColor_blue);
        final RadioButton purple = view.findViewById(R.id.basicColor_purple);
        final RadioButton white = view.findViewById(R.id.basicColor_white);
        final RadioButton gray = view.findViewById(R.id.basicColor_gray);
        final RadioButton black = view.findViewById(R.id.basicColor_black);

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orange.setChecked(false);   yellow.setChecked(false);   green.setChecked(false);    blue.setChecked(false);
                purple.setChecked(false);   white.setChecked(false);    gray.setChecked(false);     black.setChecked(false);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.red);
            }
        });
        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                red.setChecked(false);      yellow.setChecked(false);   green.setChecked(false);    blue.setChecked(false);
                purple.setChecked(false);   white.setChecked(false);    gray.setChecked(false);     black.setChecked(false);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.orange);
            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                red.setChecked(false);      orange.setChecked(false);   green.setChecked(false);    blue.setChecked(false);
                purple.setChecked(false);   white.setChecked(false);    gray.setChecked(false);     black.setChecked(false);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.yellow);
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                red.setChecked(false);      orange.setChecked(false);   yellow.setChecked(false);   blue.setChecked(false);
                purple.setChecked(false);   white.setChecked(false);    gray.setChecked(false);     black.setChecked(false);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.green);
            }
        });
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                red.setChecked(false);      orange.setChecked(false);   yellow.setChecked(false);   green.setChecked(false);
                purple.setChecked(false);   white.setChecked(false);    gray.setChecked(false);     black.setChecked(false);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blue);
            }
        });
        purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                red.setChecked(false);    orange.setChecked(false);   yellow.setChecked(false);   green.setChecked(false);
                blue.setChecked(false);   white.setChecked(false);    gray.setChecked(false);     black.setChecked(false);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.purple);
            }
        });
        white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                red.setChecked(false);    orange.setChecked(false);    yellow.setChecked(false);   green.setChecked(false);
                blue.setChecked(false);   purple.setChecked(false);    gray.setChecked(false);     black.setChecked(false);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.white);
            }
        });
        gray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                red.setChecked(false);    orange.setChecked(false);    yellow.setChecked(false);    green.setChecked(false);
                blue.setChecked(false);   purple.setChecked(false);    white.setChecked(false);     black.setChecked(false);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gray);
            }
        });
        black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                red.setChecked(false);    orange.setChecked(false);    yellow.setChecked(false);    green.setChecked(false);
                blue.setChecked(false);   purple.setChecked(false);    white.setChecked(false);    gray.setChecked(false);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.black);
            }
        });
//        if(red.isChecked()) {
//            radioGroup2.clearCheck();
//            radioGroup3.clearCheck();
//            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.red);
//        }
//        if(orange.isChecked()) {
//            radioGroup2.clearCheck();
//            radioGroup3.clearCheck();
//            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.orange);
//        }
//        if(green.isChecked()) {
//            radioGroup1.clearCheck();
//            radioGroup3.clearCheck();
//            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.green);
//        }
//        if(black.isChecked()) {
//            radioGroup1.clearCheck();
//            radioGroup2.clearCheck();
//            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.black);
//        }


//        final RadioButton red = view.findViewById(R.id.basicColor_red);
//        if(red.isChecked()) {
//            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.red);
//        }
//        orange.setOnCheckedChangeListener(new RadioButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked) {
//                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.orange);
//                }
//            }
//        });
//        if(orange.isChecked()) {
//            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.orange);
//        } else {
//            orange.setChecked(false);
//        }
//        if(yellow.isChecked()) {
//            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.yellow);
//        }
//        if(green.isChecked()) {
//            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.green);
//        }
//        if(blue.isChecked()) {
//            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blue);
//        }
//        if(purple.isChecked()) {
//            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.purple);
//        }
//        if(white.isChecked()) {
//            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.white);
//        }
//        if(gray.isChecked()) {
//            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gray);
//        }
//        if(black.isChecked()) {
//            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.black);
//        }

//
//        final View red = view.findViewById(R.id.basicColor_red);
//        final View redSelected = view.findViewById(R.id.basicColorSelected_red);
//        red.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(bitmap == null) {
//
//                }
//                if(redSelected.getVisibility() == View.GONE) {
//                    redSelected.setVisibility(View.VISIBLE);
//                } else
//                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.red);
//
//            }
//        });

        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
