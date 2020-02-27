package com.example.howtoday;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.howtoday.R;

import java.util.ArrayList;


public class AccountFragment extends Fragment {

    Spinner monthSpinner;
    TextView remainMoney;
    Button addButton;
    RecyclerView accountRecyclerView;
    AccountRecyclerAdapter accountRecyclerAdapter;
    ArrayList<AccountItem> data = new ArrayList<>();
    CustomDialog customDialog;
    String date, content, inoutcome;
    int remain=0;
    int money;

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<AccountItem> getData() {
        return data;
    }

    public void setData(ArrayList<AccountItem> data) {
        this.data = data;
    }

    public AccountFragment() {

    }

    public AccountItem addAccount(String date, String content, String inoutcome, String remain){
        AccountItem accountItem = new AccountItem();
        accountItem.setDate(date);
        accountItem.setContent(content);
        accountItem.setInoutcome(inoutcome);
        accountItem.setRemain(remain);
        return accountItem;
    }

    public int getRemain() {
        return remain;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_account,null);

        monthSpinner = v.findViewById(R.id.month_spinner);
        remainMoney = v.findViewById(R.id.now_money);
        addButton = v.findViewById(R.id.add_btn);
        accountRecyclerView = v.findViewById(R.id.accountRecyclerView);

        remainMoney.setText(""+remain);


        data.add(addAccount("날짜","내용","수입/지출","잔액"));

        accountRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        accountRecyclerAdapter = new AccountRecyclerAdapter(data);
        accountRecyclerView.setAdapter(accountRecyclerAdapter);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getContext(),new LinearLayoutManager(getContext()).getOrientation());

        accountRecyclerView.addItemDecoration(dividerItemDecoration);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog = new CustomDialog(getContext());
                customDialog.show();
                accountRecyclerAdapter.notifyDataSetChanged();
            }
        });

        return v;
    }
}
