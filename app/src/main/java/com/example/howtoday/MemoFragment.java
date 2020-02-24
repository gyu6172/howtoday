package com.example.howtoday;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MemoFragment extends Fragment {

    Button addMemoBtn;
    RecyclerView recyclerView;
    Intent intent;


    public MemoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_memo,null);
        addMemoBtn = v.findViewById(R.id.memoAddBtn);
        recyclerView = v.findViewById(R.id.memoRecycler);

        intent = new Intent(getContext(), SetMemoActivity.class);

        addMemoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        return v;
    }

}
