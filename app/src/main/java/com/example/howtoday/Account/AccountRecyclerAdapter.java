package com.example.howtoday.Account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.howtoday.R;

import java.util.ArrayList;

public class AccountRecyclerAdapter extends RecyclerView.Adapter<AccountRecyclerAdapter.ViewHolder> {

    private ArrayList<AccountItem> mData = null;

    public AccountRecyclerAdapter(ArrayList<AccountItem> mData) {
        this.mData = mData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView dateTextView;
        TextView contentTextView;
        TextView inoutcomeTextView;
        TextView remainTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTextView = itemView.findViewById(R.id.dateTextView);
            contentTextView = itemView.findViewById(R.id.taskTextView);
            inoutcomeTextView = itemView.findViewById(R.id.incomeoutcomeTextView);
            remainTextView = itemView.findViewById(R.id.remainmoneyTextView);
        }
    }

    @NonNull
    @Override
    public AccountRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.account_recycler_item, parent, false);

        return new AccountRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountRecyclerAdapter.ViewHolder holder, int position) {
        AccountItem accountItem = mData.get(position);
        holder.dateTextView.setText(accountItem.getDate());
        holder.contentTextView.setText(accountItem.getContent());
        holder.inoutcomeTextView.setText(accountItem.getInoutcome());
        holder.remainTextView.setText(accountItem.getRemain());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
