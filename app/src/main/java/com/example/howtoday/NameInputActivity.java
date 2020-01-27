package com.example.howtoday;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class NameInputActivity extends AppCompatActivity {

    Button okbtn;
    TextInputLayout textInputLayout;
    TextInputEditText nameInput;
    String name;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nameinput_layout);

        intent =  new Intent(NameInputActivity.this,MainActivity.class);

        okbtn = findViewById(R.id.OKbtn);
        nameInput =  findViewById(R.id.nameInput);
        textInputLayout = findViewById(R.id.textInputLayout);

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameInput.getText().toString();
                if(name.equals("")){
                    textInputLayout.setError("이름을 입력하세요.");
                }
                else{
                    intent.putExtra("name",name);
                    startActivity(intent);
                    Toast.makeText(NameInputActivity.this, "환영합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
