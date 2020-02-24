package com.example.howtoday;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SetMemoActivity extends AppCompatActivity {

    Button saveButton;
    TextInputLayout textInputLayout;
    TextInputEditText titleInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_memo);

        saveButton = findViewById(R.id.saveButton);
        textInputLayout = findViewById(R.id.textInputLayout);
        titleInput = findViewById(R.id.titleInput);


    }
}
