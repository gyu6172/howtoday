package com.example.howtoday;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class CustomDialog extends Dialog {

    DatePicker datePicker;
    TextInputLayout textInputLayout1, textInputLayout2;
    TextInputEditText contentEditText, inoutcomeEditText;
    RadioGroup radioGroup;
    RadioButton incomeButton, outcomeButton;
    Button okButton, cancelButton;
    String date, content, inoutcome;
    int money, remain;
    AccountFragment accountFragment = new AccountFragment();
    ArrayList<AccountItem> mData = new ArrayList<>();

    public CustomDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.account_custom_dialog);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        datePicker = findViewById(R.id.datePicker);
        textInputLayout1 = findViewById(R.id.textInputLayout);
        textInputLayout2 = findViewById(R.id.textInputLayout2);
        contentEditText = findViewById(R.id.contentEditText);
        inoutcomeEditText = findViewById(R.id.inoutcomeEditText);
        radioGroup = findViewById(R.id.radiogroup);
        incomeButton = findViewById(R.id.incomeButton);
        outcomeButton = findViewById(R.id.outcomeButton);
        okButton = findViewById(R.id.okButton);
        cancelButton = findViewById(R.id.cancelButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date = ""+datePicker.getYear()+"/"+datePicker.getMonth()+"/"+datePicker.getDayOfMonth();
                content = contentEditText.getText().toString();
                money = Integer.parseInt(inoutcomeEditText.getText().toString());
                if(radioGroup.getCheckedRadioButtonId() == R.id.incomeButton){
                    inoutcome = "+"+inoutcomeEditText.getText().toString();
                }
                else if(radioGroup.getCheckedRadioButtonId() == R.id.outcomeButton){
                    inoutcome = "-"+inoutcomeEditText.getText().toString();
                }
                remain = accountFragment.getRemain();
                if(inoutcome.charAt(0) == '+'){
                    remain += money;
                }
                else if(inoutcome.charAt(0) == '-'){
                    remain -= money;
                }
                Log.e("alert",""+date+"and"+content+"and"+inoutcome+"and"+remain);
                mData = accountFragment.getData();
                mData.add(accountFragment.addAccount(date,content,inoutcome,""+remain));
                accountFragment.setData(mData);
                Toast.makeText(getContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }
}
