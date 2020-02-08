package com.example.hrhj;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hrhj.HomeFragment.OnListFragmentInteractionListener;
import com.example.hrhj.dummy.DummyContent.DummyItem;

import org.w3c.dom.Text;

import java.util.List;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public HomeRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.uploadImage.setImageResource(R.drawable.upload_image); // temp
        holder.uploadMood.setImageResource(R.drawable.upload_mood_happy); // temp
        holder.uploadText.setText(mValues.get(position).uploadText);
        holder.uploadDate.setText(mValues.get(position).uploadDate);

        // 이미지 클릭 시 텍스트 표시
        holder.uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.uploadText.getVisibility() == View.GONE) {
                    holder.uploadText.setVisibility(View.VISIBLE);
                } else {
                    holder.uploadText.setVisibility(View.GONE);
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
        private DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            uploadImage = view.findViewById(R.id.uploadImage);
            uploadText = view.findViewById(R.id.uploadText);
            uploadDate = view.findViewById(R.id.uploadDate);
            uploadMood = view.findViewById(R.id.uploadMood);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + uploadText.getText() + "'";
        }
    }
}
