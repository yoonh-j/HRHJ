package com.example.hrhj.Search;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hrhj.MainActivity;
import com.example.hrhj.R;
import com.example.hrhj.domain.Post.Post;
import com.example.hrhj.domain.SearchDTO;
import com.example.hrhj.httpConnect.HttpConnection;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class SearchFragment extends Fragment {

    private Context context;
    private ArrayList<Post> searchedPostList = new ArrayList<Post>();
    private SearchAdapter adapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 액션바 숨기기
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.setShowHideAnimationEnabled(false);
        actionBar.hide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();

        final ViewGroup view = (ViewGroup)inflater.inflate(R.layout.fragment_search, container, false);


        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.search_RecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);

        ArrayList<Post> items = ((MainActivity)getActivity()).postList;
        adapter = new SearchAdapter(context,items);


        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        final EditText searchBar = view.findViewById(R.id.searchBar);
        final InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        final HttpConnection httpConnection = HttpConnection.getInstance();


        searchBar.requestFocus();
        searchBar.setImeOptions(EditorInfo.IME_ACTION_SEARCH); // 키보드 엔터키를 검색키로 변경
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // TODO: doSearch() 구현
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    SearchDTO searchDTO = new SearchDTO();
                    searchDTO.setUid(MainActivity.USER_ID);
                    searchDTO.setSearchText(searchBar.getText().toString());

                    httpConnection.searchPost(searchDTO,searchPostCallBack);

                    return true;
                }
                return false;
            }
        });



        return view;
    }

    public final Callback searchPostCallBack = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {

            final byte[] responseBytes = response.body().bytes();
            ObjectMapper objectMapper = new ObjectMapper();
            searchedPostList = objectMapper.readValue(responseBytes,new TypeReference<List<Post>>(){});


            new Thread()
            {
                public void run()
                {
                    Message msg = handler.obtainMessage();
                    handler.sendMessage(msg);
                }
            }.start();

        }
    };


    final Handler handler = new Handler(){

        public void handleMessage(Message msg){
            adapter.setItemList(searchedPostList);
            adapter.notifyDataSetChanged();
        }

    };


}
