package com.example.hrhj.Add;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.hrhj.Home.HomeFragment;
import com.example.hrhj.MainActivity;
import com.example.hrhj.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

    public static AddTextFragment newInstance(Bitmap bm, int tabNum) {
        AddTextFragment addTextFragment = new AddTextFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("Bitmap", bm);
        bundle.putInt("FragmentNumber", tabNum);
        addTextFragment.setArguments(bundle);
        return addTextFragment;
    }
    // TODO: fragment to fragment bitmap send/receive
    public AddTextFragment() {

    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.next).setVisible(false);
        menu.findItem(R.id.done).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        switch(item.getItemId()) {
//            case android.R.id.home :
//                onBackPressed();
//                return true;
            case R.id.done:
                if(getArguments().getInt("FragmentNumber") == 3) {
                    savePicture();
                }
                transaction.replace(R.id.frameLayout, new HomeFragment()).commit();
                ((MainActivity)getContext()).setBottomNavigationVisibility(true);
                ((MainActivity)getContext()).bottomNavigation.setSelectedItemId(R.id.homeMenu);

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

        context = getContext();
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_add_text, container, false);

        // 게시물 작성일 설정
        final TextView addDate = view.findViewById(R.id.addDate);
        addDate.setText(dateFormat.format(today.getTime()));
        addDate.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

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

        // 선택한 이미지를 배경으로 글 작성
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);

        FrameLayout frameLayout = view.findViewById(R.id.addText_frameLayout);
        frameLayout.setLayoutParams(params);

        final EditText editText = view.findViewById(R.id.addText_editText);

        ImageView imageView = view.findViewById(R.id.addText_imageView);
        imageView.setImageBitmap((Bitmap) getArguments().getParcelable("Bitmap"));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setVisibility(View.VISIBLE);
            }
        });

        int padding = 20;
        float scale = getResources().getDisplayMetrics().density;
        int padding_dp = (int) (padding * scale + 0.5f);
        editText.setPadding(padding_dp, padding_dp, padding_dp, padding_dp);
        editText.setTextColor(getResources().getColor(R.color.colorPrimary));

        final ImageView addMood = view.findViewById(R.id.addMood);

        final ImageButton happy = view.findViewById(R.id.happy);
        happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddTextFragment.this.context, "happy", Toast.LENGTH_SHORT).show();
                addMood.setImageResource(R.drawable.happy);
            }
        });
        ImageButton good = view.findViewById(R.id.good);
        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddTextFragment.this.context, "good", Toast.LENGTH_SHORT).show();
                addMood.setImageResource(R.drawable.good);
            }
        });
        ImageButton soso = view.findViewById(R.id.soso);
        soso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddTextFragment.this.context, "soso", Toast.LENGTH_SHORT).show();
                addMood.setImageResource(R.drawable.soso);
            }
        });
        ImageButton sad = view.findViewById(R.id.sad);
        sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddTextFragment.this.context, "sad", Toast.LENGTH_SHORT).show();
                addMood.setImageResource(R.drawable.sad);
            }
        });
        ImageButton bad = view.findViewById(R.id.bad);
        bad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddTextFragment.this.context, "bad", Toast.LENGTH_SHORT).show();
                addMood.setImageResource(R.drawable.bad);
            }
        });

        Button white = view.findViewById(R.id.textColor_white);
        white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        Button red = view.findViewById(R.id.textColor_red);
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setTextColor(getResources().getColor(R.color.red));
            }
        });
        Button orange = view.findViewById(R.id.textColor_orange);
        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setTextColor(getResources().getColor(R.color.orange));
            }
        });
        Button yellow = view.findViewById(R.id.textColor_yellow);
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setTextColor(getResources().getColor(R.color.yellow));
            }
        });
        Button green = view.findViewById(R.id.textColor_green);
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setTextColor(getResources().getColor(R.color.green));
            }
        });
        Button blue = view.findViewById(R.id.textColor_blue);
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setTextColor(getResources().getColor(R.color.blue));
            }
        });
        Button purple = view.findViewById(R.id.textColor_purple);
        purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setTextColor(getResources().getColor(R.color.purple));
            }
        });
        return view;
    }

    private void onBackPressed() {
        super.getActivity().onBackPressed();
        ((MainActivity)context).bottomNavigation.setVisibility(View.GONE);
    }

    private void savePicture() {
        Bitmap picBitmap = getArguments().getParcelable("Bitmap");

        // bitmap to byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        picBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] picData = stream.toByteArray();

        new SaveImageTask().execute(picData);
    }

    public class SaveImageTask extends AsyncTask<byte[], Void, Void> {
        @Override
        protected Void doInBackground(byte[]... bytes) {
            FileOutputStream outputStream = null;

            try {
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/하루한장");
                if (!path.exists()) {
                    path.mkdirs();
                }

                String fileName = String.format(Locale.KOREA, "%d.jpg", System.currentTimeMillis());
                File outputFile = new File(path, fileName);

                outputStream = new FileOutputStream(outputFile);
                outputStream.write(bytes[0]);
                outputStream.flush();
                outputStream.close();

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaScanIntent.setData(Uri.fromFile(outputFile));
                context.sendBroadcast(mediaScanIntent);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
