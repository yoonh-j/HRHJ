package com.example.hrhj.Search;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.hrhj.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class SearchFragment extends Fragment {

    private Context context;
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

        EditText searchBar = view.findViewById(R.id.searchBar);
        searchBar.setImeOptions(EditorInfo.IME_ACTION_SEARCH); // 키보드 엔터키를 검색키로 변경
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // TODO: doSearch() 구현
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    Toast.makeText(context, "검색", Toast.LENGTH_LONG).show();
                    return true;
                }
                return false;
            }
        });

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.search_RecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);

        ArrayList<DummyContent.DummyItem> items = new ArrayList<>();

        int numberOfTestData = 4;
        for(int i = 0; i<numberOfTestData; i++ )
        {
            items.add(new DummyContent.DummyItem("1","1","1","1","1"));
        }

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new SearchAdapter(context,items));



        return view;
    }
}
