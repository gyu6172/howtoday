package com.example.howtoday;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MemoFragment extends Fragment {

    Button addMemoBtn;
    RecyclerView recyclerView;
    Intent intent;
    ArrayList<Item> dataArray = new ArrayList<>();
    String title;
    String content;
    String now;
    ArrayList titleArray = new ArrayList();
    ArrayList contentArray = new ArrayList();

    public MemoFragment() {
        // Required empty public constructor
    }

    public Item addItem(String title, String date){
        Item item = new Item();
        item.setTopTv(title);
        item.setBottomTv(date);

        return item;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_memo, null);
        addMemoBtn = v.findViewById(R.id.memoAddBtn);
        recyclerView = v.findViewById(R.id.memoRecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(dataArray);
        recyclerView.setAdapter(recyclerAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                new LinearLayoutManager(getContext()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        intent = new Intent(getContext(), SetMemoActivity.class);

        addMemoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(intent, 1001);
            }
        });

        recyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent mintent = new Intent(getContext(),ShowMemoActivity.class);
                String title = titleArray.get(position).toString();
                String content = contentArray.get(position).toString();
                mintent.putExtra("title",title);
                mintent.putExtra("content",content);
                startActivityForResult(mintent,2001);
            }
        });

        return v;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1001){
            if(resultCode == 200){
                title = data.getStringExtra("title");
                now = data.getStringExtra("time");
                content = data.getStringExtra("content");
                dataArray.add(addItem(title,now));
                titleArray.add(title);
                contentArray.add(content);
            }
        }
        if(requestCode == 2001){

        }
    }
}
