package com.example.howtoday.Account;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.howtoday.R;
import com.google.gson.Gson;

import java.util.ArrayList;


public class AccountFragment extends Fragment {

    Spinner monthSpinner;
    TextView remainMoneyTextView;
    Button addButton;
    RecyclerView accountRecyclerView;
    ArrayList<AccountItem> data = new ArrayList<>();
    AccountRecyclerAdapter accountRecyclerAdapter = new AccountRecyclerAdapter(data);
    ArrayList<Integer> dateInt = new ArrayList();
    String date, content, moneyString;
    int usemoney, remainMoney;
    boolean inputAtBack = true;
    int count = 0;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public AccountFragment() {

    }

    @Override
    public void onStop() {
        super.onStop();

        editor.putInt("count", count);
        editor.putInt("remainmoney", remainMoney);
        editor.apply();

        Gson ggson = new Gson();
        for (int i = 0; i < count; i++) {
            String json = ggson.toJson(data.get(i));
            int n = dateInt.get(i);
            editor.putString("data" + i, json);
            editor.putInt("dateInt" + i, n);
            Log.e("onstop", json);
        }
        editor.apply();
    }

    public AccountItem addAccount(String date, String content, String inoutcome, String remain) {
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
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_account, null);

        monthSpinner = v.findViewById(R.id.month_spinner);
        addButton = v.findViewById(R.id.add_btn);
        accountRecyclerView = v.findViewById(R.id.accountRecyclerView);
        remainMoneyTextView = v.findViewById(R.id.now_money);

        sharedPreferences = getActivity().getSharedPreferences("mpr", 0);
        editor = sharedPreferences.edit();

        accountRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        accountRecyclerAdapter = new AccountRecyclerAdapter(data);
        accountRecyclerView.setAdapter(accountRecyclerAdapter);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getContext(), new LinearLayoutManager(getContext()).getOrientation());

        accountRecyclerView.addItemDecoration(dividerItemDecoration);


        count = sharedPreferences.getInt("count", 0);
        remainMoney = sharedPreferences.getInt("remainmoney", 0);

        if (count == 0) {
            data.add(addAccount("날짜", "내용", "소비/지출", "잔액"));
            dateInt.add(0);
            count++;
        } else {
            Gson ggson = new Gson();
            for (int i = 0; i < count; i++) {
                String json = sharedPreferences.getString("data" + i, "");
                int n = sharedPreferences.getInt("dateInt" + i, -1);
                AccountItem accountItem = new AccountItem();
                accountItem = ggson.fromJson(json, AccountItem.class);
                data.add(accountItem);
                dateInt.add(n);
                accountRecyclerAdapter.notifyDataSetChanged();
            }

        }


        Log.e("count", "" + count);
        Log.e("remainMoney", "" + remainMoney);

        remainMoneyTextView.setText("" + remainMoney);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AccountInput.class);
                startActivityForResult(intent, 1001);
            }
        });

        accountRecyclerAdapter.setOnItemClickListener(new AccountRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("소비/지출 내역 삭제");
                String content = data.get(position).getContent();
                builder.setMessage("\"" + content + "\"" + "내역을 삭제하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        remainMoney = remainMoney - Integer.parseInt(data.get(position).getInoutcome());
                        data.remove(position);
                        dateInt.remove(position);
                        count--;
                        reArrangeList();
                        accountRecyclerAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        remainMoneyTextView.setText("" + remainMoney);
                    }
                });
                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1001) {
            if (resultCode == 101) {
                date = intent.getStringExtra("date");
                content = intent.getStringExtra("content");
                usemoney = intent.getIntExtra("money", 0);
                String[] d;
                d = date.split("/");
                d[1] = "" + (Integer.parseInt(d[1]) + 1);
                date = d[0] + "/" + d[1] + "/" + d[2];
                int dateIntValue = Integer.parseInt(d[0]) * 10000 + Integer.parseInt(d[1]) * 100 + Integer.parseInt(d[2]);
                if (usemoney > 0) {
                    moneyString = "+" + usemoney;
                } else {
                    moneyString = "" + usemoney;
                }
                remainMoney = remainMoney + usemoney;
                Log.e("size", "" + data.size());
                if (data.size() > 1) {
                    for (int i = 1; i < data.size(); i++) {
                        if (dateIntValue < dateInt.get(i)) {
                            Log.e("location", "center");
                            data.add(i, addAccount(date, content, moneyString, "" + remainMoney));
                            dateInt.add(i, dateIntValue);
                            count++;
                            inputAtBack = false;
                            break;
                        }
                        inputAtBack = true;
                    }
                    if (inputAtBack) {
                        data.add(addAccount(date, content, moneyString, "" + remainMoney));
                        dateInt.add(dateIntValue);
                        count++;
                        Log.e("location", "back");
                    }
                } else {
                    data.add(addAccount(date, content, moneyString, "" + remainMoney));
                    dateInt.add(dateIntValue);
                    count++;
                    Log.e("size가 1보다", "작아");
                }
                remainMoneyTextView.setText("" + remainMoney);
                reArrangeList();
                accountRecyclerAdapter.notifyDataSetChanged();
                for (int a : dateInt) {
                    Log.e("asd", "" + a);
                }
                Toast.makeText(getContext(), "추가되었습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void reArrangeList() {
        for (int i = 1; i < data.size(); i++) {
            if (i == 1) {
                data.get(i).setRemain("" + Integer.parseInt(data.get(i).getInoutcome()));
            } else {
                int use = Integer.parseInt(data.get(i).getInoutcome());
                int remain = Integer.parseInt(data.get(i - 1).getRemain());
                int sum = use + remain;
                data.get(i).setRemain("" + sum);
            }
        }
    }
}
