package com.example.hrhj.Home;

import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hrhj.Home.HomeFragment.OnListFragmentInteractionListener;
import com.example.hrhj.R;
import com.example.hrhj.domain.Post.Post;
import com.example.hrhj.httpConnect.HttpConnection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {

    private  ArrayList<Post> mValues;
    private final OnListFragmentInteractionListener mListener;

    public HomeRecyclerViewAdapter(ArrayList<Post> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fragment_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //TODO: fileName -> 포스트 이미지
        //String fileName = "img_2020_02_24_11_18_59.jpeg";
        Glide.with(holder.mView.getContext())
                .load(HttpConnection.url+"/image/"+mValues.get(position).getImage())
                .error(R.drawable.upload_image)
                .into(holder.uploadImage);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //holder.uploadImage.setImageResource(R.drawable.upload_image); // temp
        holder.uploadMood.setImageResource(R.drawable.upload_mood_happy); // temp
        holder.uploadText.setText(mValues.get(position).getText());
        holder.uploadDate.setText(sdf.format(mValues.get(position).getDate()));

        //TODO: 텍스트 대신 레이아웃이 올라오게 변경

        // 이미지 클릭 시 텍스트 표시
        holder.uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.postLayout.getVisibility() == View.GONE) {
                    holder.postLayout.setVisibility(View.VISIBLE);
                } else {
                    holder.postLayout.setVisibility(View.GONE);
                }
            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });

        //업데이트 버튼
        holder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.postLayout.setVisibility(View.GONE);
                holder.updateLayout.setVisibility(View.VISIBLE);
            }
        });

        // 포스트 수정

        //포스트 변경 확인 버튼

        holder.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.updateLayout.setVisibility(View.GONE);
                holder.postLayout.setVisibility(View.VISIBLE);

                holder.uploadText.setText(holder.updateText.getText());

            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private ImageView uploadImage;
        private TextView uploadText;
        private final TextView uploadDate;
        private ImageView uploadMood;
        private Post mItem;
        private FrameLayout postLayout;
        private Button updateButton;
        private FrameLayout updateLayout;
        private Button confirmButton;
        private EditText updateText;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            uploadImage = view.findViewById(R.id.uploadImage);
            uploadText = view.findViewById(R.id.uploadText);
            uploadDate = view.findViewById(R.id.uploadDate);
            uploadMood = view.findViewById(R.id.uploadMood);
            postLayout = view.findViewById(R.id.postLayout);
            updateButton = view.findViewById(R.id.postUpdateButton);
            updateLayout = view.findViewById(R.id.postUpdateLayout);
            confirmButton = view.findViewById(R.id.updateConfirmButton);
            updateText = view.findViewById(R.id.updateText);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + uploadText.getText() + "'";
        }
    }
}
