package com.example.howtoday.Account;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.ArrayList;


public class AccountFragment extends Fragment {

    Spinner monthSpinner;
    TextView remainMoneyTextView;
    Button addButton;
    RecyclerView accountRecyclerView;
    AccountRecyclerAdapter accountRecyclerAdapter;
    ArrayList<AccountItem> data = new ArrayList<>();
    ArrayList<Integer> dateInt = new ArrayList();
    String date, content, moneyString;
    int usemoney, remainMoney;
    int count=0;
    boolean inputAtBack = true;

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
        dateInt.add(0);

        accountRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        accountRecyclerAdapter = new AccountRecyclerAdapter(data);
        accountRecyclerView.setAdapter(accountRecyclerAdapter);

        if(data.size() == 1){
            remainMoney = 0;
        }
        else{
            remainMoney = Integer.parseInt(data.get(data.size()-1).getRemain());
        }

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getContext(),new LinearLayoutManager(getContext()).getOrientation());

        accountRecyclerView.addItemDecoration(dividerItemDecoration);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AccountInput.class);
                intent.putExtra("remain",remainMoney);
                startActivityForResult(intent,1001);
            }
        });

        accountRecyclerAdapter.setOnItemClickListener(new AccountRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("소비/지출 내역 삭제");
                String content = data.get(position).getContent();
                builder.setMessage("\""+content+"\""+"내역을 삭제하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        data.remove(position);
                        dateInt.remove(position);
                        reArrangeList();
                        accountRecyclerAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1001){
            if(resultCode == 101){
                date = data.getStringExtra("date");
                content = data.getStringExtra("content");
                usemoney = data.getIntExtra("money",0);
                String[] d;
                d = date.split("/");
                d[1] = ""+(Integer.parseInt(d[1])+1);
                date = d[0]+"/"+d[1]+"/"+d[2];
                int dateIntValue = Integer.parseInt(d[0])*10000+Integer.parseInt(d[1])*100+Integer.parseInt(d[2]);
                if(usemoney >= 0){
                    moneyString = "+"+usemoney;
                }
                else{
                    moneyString = ""+usemoney;
                }
                remainMoney = remainMoney+usemoney;
                if(this.data.size() > 1){
                    for(int i=1;i<=this.data.size()-1;i++){
                        if(dateIntValue<dateInt.get(i)){
                            Log.e("location","center");
                            this.data.add(i, addAccount(date,content,moneyString,""+remainMoney));
                            dateInt.add(i, dateIntValue);
                            inputAtBack = false;
                            break;
                        }
                    }
                    if(inputAtBack){
                        this.data.add(addAccount(date,content,moneyString,""+remainMoney));
                        dateInt.add(dateIntValue);
                        inputAtBack = false;
                    }
                }
                else{
                    this.data.add(addAccount(date,content,moneyString,""+remainMoney));
                    dateInt.add(dateIntValue);
                }
                remainMoneyTextView.setText(""+remainMoney);
                reArrangeList();
                accountRecyclerAdapter.notifyDataSetChanged();
                for(int a:dateInt){
                    Log.e("asd",""+a);
                }
            }
        }
    }

    public void reArrangeList(){
        this.data.clear();
        data.add(addAccount("날짜","내용","수입/지출","잔액"));
        dateInt.add(0);
        int i;
        String date, content, str;
        String d[];
        int useM;
        int remain=0;
        for(i=1;i<=this.data.size()-1;i++){
            date = this.data.get(i).getDate();
            d = date.split("/");
            d[1] = ""+(Integer.parseInt(d[1])+1);
            date = d[0]+"/"+d[1]+"/"+d[2];
            int dateIntValue = Integer.parseInt(d[0])*10000+Integer.parseInt(d[1])*100+Integer.parseInt(d[2]);
            content = this.data.get(i).getContent();
            str = this.data.get(i).getInoutcome();
            useM = Integer.parseInt(this.data.get(i).getInoutcome());
            remain += useM;
            data.add(addAccount(date,content,str,""+remain));
            dateInt.add(dateIntValue);
        }
        accountRecyclerAdapter.notifyDataSetChanged();
        for(AccountItem item:data){
            Log.e("asd",""+item.toString());
        }
    }
}
