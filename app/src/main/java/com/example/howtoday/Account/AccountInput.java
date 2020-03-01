package com.example.howtoday.Account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.howtoday.Account.AccountFragment;
import com.example.howtoday.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AccountInput extends AppCompatActivity {

    DatePicker datePicker;
    TextInputLayout textInputLayout1, textInputLayout2;
    TextInputEditText contentEditText, moneyEditText;
    RadioGroup radioGroup;
    RadioButton incomeButton, outcomeButton;
    Button okButton;
    String date, content;
    int money;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_input);

        datePicker = findViewById(R.id.datePicker);
        textInputLayout1 = findViewById(R.id.textInputLayout);
        textInputLayout2 = findViewById(R.id.textInputLayout2);
        contentEditText = findViewById(R.id.contentEditText);
        moneyEditText = findViewById(R.id.inoutcomeEditText);
        radioGroup = findViewById(R.id.radiogroup);
        incomeButton = findViewById(R.id.incomeButton);
        outcomeButton = findViewById(R.id.outcomeButton);
        okButton = findViewById(R.id.okButton);

        incomeButton.setChecked(true);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), AccountFragment.class);
                date = datePicker.getYear()+"/"+datePicker.getMonth()+"/"+datePicker.getDayOfMonth();
                content =  contentEditText.getText().toString();
                if(content.length() == 0){
                    contentEditText.setError("내용을 입력하세요.");
                }

                else if(moneyEditText.getText().toString().length() == 0){
                    moneyEditText.setError("수입/지출 비용을 입력하세요.");
                }

                else{
                    money = Integer.parseInt(moneyEditText.getText().toString());
                    if(radioGroup.getCheckedRadioButtonId() == R.id.outcomeButton){
                        money = money * -1;
                    }
                        setResult(101, intent.putExtra("date",date));
                        setResult(101, intent.putExtra("content",content));
                        setResult(101, intent.putExtra("money",money));
                        finish();
                }
            }
        });


    }
}
