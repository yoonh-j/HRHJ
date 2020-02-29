package com.example.hrhj.Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hrhj.MainActivity;
import com.example.hrhj.R;
import com.example.hrhj.domain.Post.Post;
import com.example.hrhj.httpConnect.HttpConnection;


import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<Post> itemList = new ArrayList<Post>();

    public SearchAdapter(Context context, ArrayList<Post> itemList)
    {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.item_fragment_search, parent, false);
        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams)view.getLayoutParams();
        params.height = parent.getMeasuredWidth() / 3;
        view.setLayoutParams(params);

        SearchViewHolder viewHolder = new SearchViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchViewHolder holder, final int position) {

        Glide.with(holder.itemView.getContext())
                .load(HttpConnection.url+"/image/"+itemList.get(position).getImage())
                .error(R.drawable.upload_image)
                .into(holder.itemImage);

        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)context).scrollToItem(itemList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder{

        public ImageView itemImage;

        public SearchViewHolder(View itemView) {
            super(itemView);
            itemImage = (ImageView) itemView.findViewById(R.id.search_itemImage);
        }
    }

    public void setItemList(ArrayList<Post> itemList)
    {
        this.itemList = itemList;
    }
}
