package com.example.howtoday;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ShowMemoActivity extends AppCompatActivity {

    Button deleteButton;
    TextView memoTitleTextView;
    TextView memoContentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_memo);

        deleteButton = findViewById(R.id.deleteBtn);
        memoTitleTextView = findViewById(R.id.showMemoTitleTv);
        memoContentTextView = findViewById(R.id.showMemoContentTv);




    }
}
