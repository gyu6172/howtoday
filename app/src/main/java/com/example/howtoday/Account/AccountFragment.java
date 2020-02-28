package com.example.howtoday.Account;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
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
    TextView remainMoneyTextView;
    Button addButton;
    RecyclerView accountRecyclerView;
    AccountRecyclerAdapter accountRecyclerAdapter;
    ArrayList<AccountItem> data = new ArrayList<>();
    String date, content, moneyString;
    String[] d;
    int usemoney, remainMoney;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_account,null);

        monthSpinner = v.findViewById(R.id.month_spinner);
        addButton = v.findViewById(R.id.add_btn);
        accountRecyclerView = v.findViewById(R.id.accountRecyclerView);
        remainMoneyTextView = v.findViewById(R.id.now_money);


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
                Intent intent = new Intent(getContext(), AccountInput.class);
                startActivityForResult(intent,1001);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1001){
            if(resultCode == 101){
                ArrayList<AccountItem> dataArray = new ArrayList<>();
                dataArray = this.data;
                date = data.getStringExtra("date");
                content = data.getStringExtra("content");
                usemoney = data.getIntExtra("money",0);
                d = date.split("/");
                d[1] = ""+(Integer.parseInt(d[1])+1);
                date = d[0]+"/"+d[1]+"/"+d[2];
                if(usemoney >= 0){
                    moneyString = "+"+usemoney;
                }
                else{
                    moneyString = ""+usemoney;
                }
                if(dataArray.size() > 1){
                    remainMoney = Integer.parseInt(dataArray.get(dataArray.size()-1).getRemain());
                }
                else{
                    remainMoney = 0;
                }
                remainMoney = remainMoney+usemoney;
                this.data.add(addAccount(date,content,moneyString,""+remainMoney));
                remainMoneyTextView.setText(""+remainMoney);
                accountRecyclerAdapter.notifyDataSetChanged();
            }
        }
    }
}
