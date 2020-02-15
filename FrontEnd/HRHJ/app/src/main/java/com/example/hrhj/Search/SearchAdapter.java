package com.example.hrhj.Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrhj.R;
import com.example.hrhj.dummy.DummyContent;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<DummyContent.DummyItem> itemList = new ArrayList<DummyContent.DummyItem>();

    public SearchAdapter(Context context, ArrayList<DummyContent.DummyItem> itemList)
    {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.item_fragment_search, parent, false);
        SearchViewHolder viewHolder = new SearchViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {

        holder.itemImage.setImageResource(R.drawable.upload_image);

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
}
