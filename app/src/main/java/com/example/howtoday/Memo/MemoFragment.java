package com.example.howtoday.Memo;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.howtoday.R;
import com.google.gson.Gson;

import java.util.ArrayList;


public class MemoFragment extends Fragment {

    Button addMemoBtn;
    RecyclerView recyclerView;
    Intent intent;
    ArrayList<Item> dataArray = new ArrayList<>();
    String title;
    String content;
    String now;
    ArrayList<String> titleArray = new ArrayList();
    ArrayList<String> contentArray = new ArrayList();
    int deletePosition;
    RecyclerAdapter recyclerAdapter = new RecyclerAdapter(dataArray);
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int count=0;

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
    public void onStop() {
        super.onStop();

        editor.putInt("count",count);
        editor.apply();

        Gson ggson = new Gson();
        for(int i=0;i<count;i++){
            String json = ggson.toJson(dataArray.get(i));
            String title = titleArray.get(i);
            String content = contentArray.get(i);
            editor.putString("titleArray"+i,title);
            editor.putString("contentArray"+i,content);
            editor.putString("dataArray"+i,json);
            Log.e("onstop",json);
        }
        editor.apply();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_memo, null);
        addMemoBtn = v.findViewById(R.id.memoAddBtn);
        recyclerView = v.findViewById(R.id.memoRecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerAdapter);

        sharedPreferences = getActivity().getSharedPreferences("pref",0);
        editor = sharedPreferences.edit();

        Gson gson = new Gson();
        count = sharedPreferences.getInt("count",0);
        for(int i=0;i<count;i++){
            String json =sharedPreferences.getString("dataArray"+i,"");
            String title = sharedPreferences.getString("titleArray"+i,"");
            String content = sharedPreferences.getString("contentArray"+i,"");
            Item item = new Item();
            item = gson.fromJson(json,Item.class);
            dataArray.add(item);
            titleArray.add(title);
            contentArray.add(content);
            recyclerAdapter.notifyDataSetChanged();
            Log.e("oncreate",item.toString());
        }


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
                mintent.putExtra("position",position);
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
            if(resultCode == 101){
                title = data.getStringExtra("title");
                now = data.getStringExtra("time");
                content = data.getStringExtra("content");
                dataArray.add(addItem(title,now));
                titleArray.add(title);
                contentArray.add(content);
                recyclerAdapter.notifyDataSetChanged();
                count++;

            }
        }
        if(requestCode == 2001){
            if (resultCode == 201) {
                deletePosition = data.getIntExtra("deletePos", -1);
                Log.e("삭제", "" + deletePosition);
                dataArray.remove(deletePosition);
                titleArray.remove(deletePosition);
                contentArray.remove(deletePosition);
                recyclerAdapter.notifyDataSetChanged();
                count--;
            }
        }
    }
}
