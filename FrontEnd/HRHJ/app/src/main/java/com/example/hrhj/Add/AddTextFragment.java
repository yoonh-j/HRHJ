package com.example.hrhj.Add;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.hrhj.Home.HomeFragment;
import com.example.hrhj.MainActivity;
import com.example.hrhj.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTextFragment extends Fragment {

    private AddGalleryAdapter addGalleryAdapter;
    private final Calendar today = Calendar.getInstance();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.", Locale.KOREA);
    private AddBasicFragment.OnFragmentInteractionListener mListener;
    private Context context;
    private static String picPath;

    public static AddTextFragment newInstance(Bitmap bm) {
        AddTextFragment addTextFragment = new AddTextFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("Bitmap", bm);
        addTextFragment.setArguments(bundle);
        return addTextFragment;
    }
    // TODO: fragment to fragment bitmap send/receive
    public AddTextFragment() {

    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
//        menu.findItem(android.R.id.home);
        menu.findItem(R.id.addText).setVisible(false);
        menu.findItem(R.id.done).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        switch(item.getItemId()) {
//            case android.R.id.home :
//                getActivity().onBackPressed();
//                ((MainActivity)context).bottomNavigation.setVisibility(View.GONE);
//                return true;
            case R.id.done:
//                transaction.add(R.id.frameLayout, new AddTextFragment().newInstance((Bitmap) getArguments().getParcelable("Bitmap"))).addToBackStack(null).commit();
                transaction.replace(R.id.frameLayout, new HomeFragment()).addToBackStack(null).commit();
                ((MainActivity)context).bottomNavigation.setVisibility(View.VISIBLE);
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

//        if(getArguments() != null) {
//        bitmap = getArguments().getParcelable("BitmapImage");
//        }

        context = getContext();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_add_text, container, false);

        // 게시물 작성일 설정
        final TextView addDate = view.findViewById(R.id.addDate);
        addDate.setText(dateFormat.format(today.getTime()));

        final DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                today.set(Calendar.YEAR, year);
                today.set(Calendar.MONTH, month);
                today.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                addDate.setText(dateFormat.format(today.getTime()));
            }
        };
        addDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, listener,
                        today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        // 선택한 이미지 배경으로 글 작성
//        ImageView imageView = view.findViewById(R.id.addText_ImageView);
//        int height = getActivity().getWindowManager().getDefaultDisplay().getWidth();
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(height, height);
//        imageView.setLayoutParams(params);
//        imageView.setImageBitmap((Bitmap) getArguments().getParcelable("Bitmap"));

        final EditText editText = view.findViewById(R.id.add_editText);
        int height = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(height, height);
        editText.setLayoutParams(params);
        int padding = 20;
        float scale = getResources().getDisplayMetrics().density;
        int padding_dp = (int) (padding * scale + 0.5f);
        editText.setPadding(padding_dp, padding_dp, padding_dp, padding_dp);
        editText.setBackgroundResource(R.color.colorTextBackground);
        editText.setTextColor(getResources().getColor(R.color.colorPrimary));

        ImageButton happy = view.findViewById(R.id.happy);
        happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddTextFragment.this.context, "happy", Toast.LENGTH_SHORT).show();
            }
        });
        ImageButton good = view.findViewById(R.id.good);
        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddTextFragment.this.context, "good", Toast.LENGTH_SHORT).show();
            }
        });
        ImageButton soso = view.findViewById(R.id.soso);
        soso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddTextFragment.this.context, "soso", Toast.LENGTH_SHORT).show();
            }
        });
        ImageButton sad = view.findViewById(R.id.sad);
        sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddTextFragment.this.context, "sad", Toast.LENGTH_SHORT).show();
            }
        });
        ImageButton bad = view.findViewById(R.id.bad);
        bad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddTextFragment.this.context, "bad", Toast.LENGTH_SHORT).show();
            }
        });

        Button white = view.findViewById(R.id.white);
        white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        Button red = view.findViewById(R.id.red);
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setTextColor(getResources().getColor(R.color.red));
            }
        });
        Button orange = view.findViewById(R.id.orange);
        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setTextColor(getResources().getColor(R.color.orange));
            }
        });
        Button yellow = view.findViewById(R.id.yellow);
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setTextColor(getResources().getColor(R.color.yellow));
            }
        });
        Button green = view.findViewById(R.id.green);
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setTextColor(getResources().getColor(R.color.green));
            }
        });
        Button blue = view.findViewById(R.id.blue);
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setTextColor(getResources().getColor(R.color.blue));
            }
        });
        Button purple = view.findViewById(R.id.purple);
        purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setTextColor(getResources().getColor(R.color.purple));
            }
        });
        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
