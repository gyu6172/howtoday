package com.example.howtoday;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ShowMemoActivity extends AppCompatActivity {

    Button deleteButton;
    TextView memoTitleTextView;
    TextView memoContentTextView;
    Intent intent;
    String title;
    String content;
    int posOfDeleteMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_memo);

        deleteButton = findViewById(R.id.deleteBtn);
        memoTitleTextView = findViewById(R.id.showMemoTitleTv);
        memoContentTextView = findViewById(R.id.showMemoContentTv);

        intent = getIntent();
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        posOfDeleteMemo = intent.getIntExtra("position",-1);

        memoTitleTextView.setText(title);
        memoContentTextView.setText(content);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowMemoActivity.this);
                builder.setTitle("메모 삭제하기");
                builder.setMessage("메모를 삭제하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setResult(201,intent.putExtra("deletePos",posOfDeleteMemo));
                        Toast.makeText(ShowMemoActivity.this, "삭제되었습니다", Toast.LENGTH_SHORT).show();
                        finish();
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





    }
}
