package com.example.howtoday.MainActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.howtoday.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    TextView nameTextView;
    EditText editText;
    String name;
    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdater mAdater;
    SharedPreferences sharedPreferences;
    int check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        nameTextView = findViewById(R.id.main_name);

        sharedPreferences = getSharedPreferences("pref",0);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        Intent intent = getIntent();
        check = intent.getIntExtra("check",0);
        if(check == 0){
            name = intent.getStringExtra("name");
            nameTextView.setText(name);
            editor.putString("name",name);
            editor.commit();
        }
        else if(check == 1){
            name = sharedPreferences.getString("name","jackxon");
            nameTextView.setText(name);
        }

        mAdater = new PagerAdater(getSupportFragmentManager());

        viewPager.setAdapter(mAdater);
        viewPager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        nameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText = new EditText(MainActivity.this);
                editText.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("이름 바꾸기");
                builder.setMessage("이름을 입력하세요.");
                builder.setView(editText);
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        nameTextView.setText(editText.getText().toString());
                        name = editText.getText().toString();
                        editor.putString("name",name);
                        editor.commit();
                        Toast.makeText(MainActivity.this, "적용되었습니다.",Toast.LENGTH_SHORT );
                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });



    }
}
