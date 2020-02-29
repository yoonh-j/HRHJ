package com.example.hrhj.Home;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hrhj.Home.HomeFragment.OnListFragmentInteractionListener;
import com.example.hrhj.MainActivity;
import com.example.hrhj.R;
import com.example.hrhj.domain.Post.Color;
import com.example.hrhj.domain.Post.Emotion;
import com.example.hrhj.domain.Post.Post;
import com.example.hrhj.httpConnect.HttpConnection;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {

    private  ArrayList<Post> mValues;
    private final OnListFragmentInteractionListener mListener;
    private HttpConnection httpConn = HttpConnection.getInstance();
    private Context context;

    public HomeRecyclerViewAdapter(ArrayList<Post> items, OnListFragmentInteractionListener listener,Context context) {
        mValues = items;
        mListener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fragment_home, parent, false);

        FrameLayout home_frameLayout = view.findViewById(R.id.home_frameLayout);
        ViewGroup.LayoutParams params = home_frameLayout.getLayoutParams();
        params.height = parent.getMeasuredWidth();
        home_frameLayout.setLayoutParams(params);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Glide.with(holder.mView.getContext())
                .load(HttpConnection.url+"/image/"+mValues.get(position).getImage())
                .error(R.drawable.upload_image)
                .into(holder.uploadImage);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        holder.uploadText.setText(holder.mItem.getText());
        holder.uploadDate.setText(sdf.format(holder.mItem.getDate()));
        holder.setMood(holder.mItem.getEmotion());
        holder.setTextColor(holder.mItem.getTextColor());



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
                holder.updateText.setText(holder.mItem.getText());
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
                holder.mItem.setText(holder.updateText.getText().toString());

                httpConn.updatePost(holder.mItem,updatePostListCallback);


            }
        });

        //포스트 삭제
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //HttpConnection httpConn = HttpConnection.getInstance();
                httpConn.deletePost(holder.mItem,deletePostListCallback);
                mValues.remove(holder.mItem);
                //TODO: CallBack함수로 이동 필요
                ((MainActivity)context).postList.remove(holder.mItem);
                notifyDataSetChanged();
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
        private ImageButton deleteButton;


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
            deleteButton = view.findViewById(R.id.deleteButton);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + uploadText.getText() + "'";
        }

        public void setMood(int mood)
        {
            switch (mood)
            {
                case Emotion.EMOTION_HAPPY:
                {
                    uploadMood.setImageResource(R.drawable.happy);
                    break;
                }
                case Emotion.EMOTION_GOOD:
                {
                    uploadMood.setImageResource(R.drawable.good);
                    break;
                }
                case Emotion.EMOTION_SOSO:
                {
                    uploadMood.setImageResource(R.drawable.soso);
                    break;
                }
                case Emotion.EMOTION_SAD:
                {
                    uploadMood.setImageResource(R.drawable.sad);
                    break;
                }
                case Emotion.EMOTION_BAD:
                {
                    uploadMood.setImageResource(R.drawable.bad);
                    break;
                }
            }
        }

        public void setTextColor(int color){
            switch (color){

                //TODO: Adapter생성시 Context 입력받아서 코드 간소화 가능(컬러 스트링부분)
                case Color.WHITE:
                {
                    uploadText.setTextColor(android.graphics.Color.parseColor("#FFFFFF"));
                    break;
                }
                case Color.RED:
                {
                    uploadText.setTextColor(android.graphics.Color.parseColor("#DDFF0000"));
                    break;
                }
                case Color.ORANGE:
                {
                    uploadText.setTextColor(android.graphics.Color.parseColor("#FF5722"));
                    break;
                }
                case Color.YELLOW:
                {
                    uploadText.setTextColor(android.graphics.Color.parseColor("#FFEB3B"));
                    break;
                }
                case Color.GREEN:
                {
                    uploadText.setTextColor(android.graphics.Color.parseColor("#4CAF50"));
                    break;
                }
                case Color.BLUE:
                {
                    uploadText.setTextColor(android.graphics.Color.parseColor("#3F51B5"));
                    break;
                }
                case Color.PURPLE:
                {
                    uploadText.setTextColor(android.graphics.Color.parseColor("#9C27B0"));
                    break;
                }

            }


        }
    }

    public final Callback deletePostListCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {

        }
    };

    public final Callback updatePostListCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {

        }
    };

}
