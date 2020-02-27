package com.example.howtoday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SetMemoActivity extends AppCompatActivity {

    Button saveButton;
    TextInputLayout textInputLayout;
    TextInputEditText titleInput;
    String title;
    String content;
    Intent intent;
    String now;
    EditText contentsEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_memo);

        saveButton = findViewById(R.id.saveButton);
        textInputLayout = findViewById(R.id.textInputLayout);
        titleInput = findViewById(R.id.titleInput);
        contentsEditText = findViewById(R.id.memoContent);

        intent = new Intent(SetMemoActivity.this, MemoFragment.class);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date today = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/E  HH:mm");
                now = sdf.format(today);
                title = titleInput.getText().toString();
                content = contentsEditText.getText().toString();
                if(title.length() != 0) {
                    setResult(101, intent.putExtra("title", title));
                    setResult(101, intent.putExtra("time",now));
                    setResult(101, intent.putExtra("content",content));
                    Toast.makeText(SetMemoActivity.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    setResult(101, intent.putExtra("title", "제목없음"));
                    setResult(101,intent.putExtra("time",now));
                    setResult(101, intent.putExtra("content",content));
                    Toast.makeText(SetMemoActivity.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


    }
}
