package com.example.howtoday.Calendar;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.howtoday.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class CalendarFragment extends Fragment {

    CalendarView calendarView;
    String toaddSchedule;
    Button scheduleAddButton;
    RecyclerView recyclerView;
    TextView noScheduleTextView;
    ArrayList<ScheduleItem> dataArray = new ArrayList<>();
    ArrayList<String> scheduleArray = new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String nowDate;
    String[] d;
    Date today = new Date();
    SimpleDateFormat sdf;
    int count;
    int chk = 0;
    int focusedyear, focusedmonth, focuseddate;

    public ScheduleItem addSchedule(Drawable circle, String schedule) {
        ScheduleItem scheduleItem = new ScheduleItem();
        scheduleItem.setBlackCircle(circle);
        scheduleItem.setSchedule(schedule);
        return scheduleItem;
    }


    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_calendar, null);
        calendarView = v.findViewById(R.id.calender);
        scheduleAddButton = v.findViewById(R.id.scheduleAddBtn);
        recyclerView = v.findViewById(R.id.calendarRecycler);
        noScheduleTextView = v.findViewById(R.id.noscheduleTextView);
        sharedPreferences = getActivity().getSharedPreferences("pref", 0);
        editor = sharedPreferences.edit();

        recyclerView.setNestedScrollingEnabled(false);

        sdf = new SimpleDateFormat("yyyy/MM/dd");
        nowDate = sdf.format(today);
        d = nowDate.split("/");
        if (nowDate.charAt(5) == '0') {
            d[1] = "" + nowDate.charAt(6);
        }
        d[1] = ""+(Integer.parseInt(d[1])-1);
        Log.e("d", d[0] + "/" + d[1] + "/" + d[2]);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final ScheduleRecyclerAdapter scheduleRecyclerAdapter = new ScheduleRecyclerAdapter(dataArray);
        recyclerView.setAdapter(scheduleRecyclerAdapter);

        int tmp = sharedPreferences.getInt(d[0] + "/" + d[1] + "/" + d[2], 0);
        if (tmp == 0){
            noScheduleTextView.setText("( 일정 없음 )");
        }
        for (int i = 1; i <= tmp; i++) {
            String sch = sharedPreferences.getString(d[0] + "/" + d[1] + "/" + d[2] + "/" + i, "헿");
            Log.e("schedule",sch);
            dataArray.add(addSchedule(getResources().getDrawable(R.drawable.black_circle), sch));
        }
        scheduleRecyclerAdapter.notifyDataSetChanged();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dateOfMonth) {
                chk = 0;
                focusedyear = year;
                focusedmonth = month;
                focuseddate = dateOfMonth;
                Log.e("now", "" + focusedyear + "/" + focusedmonth + "/" + focuseddate + "\n   " + year + "/" + month + "/" + dateOfMonth);
                dataArray.clear();
                scheduleRecyclerAdapter.notifyDataSetChanged();
                count = sharedPreferences.getInt("" + year + "/" + month + "/" + dateOfMonth, 0);
                if (count == 0) {
                    noScheduleTextView.setText("( 일정 없음 )");
                } else {
                    noScheduleTextView.setText("");
                    for (int i = 1; i <= count; i++) {
                        String sch = sharedPreferences.getString("" + year + "/" + month + "/" + dateOfMonth + "/" + i, "헿");
                        dataArray.add(addSchedule(getResources().getDrawable(R.drawable.black_circle), sch));
                    }
                }
                scheduleRecyclerAdapter.notifyDataSetChanged();
            }
        });

        scheduleAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = new EditText(getContext());
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("일정 추가하기");
                builder.setMessage("일정을 입력하세요");
                builder.setView(editText);
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        chk = sharedPreferences.getInt("" + focusedyear + "/" + focusedmonth + "/" + focuseddate, 0);
                        toaddSchedule = editText.getText().toString();
                        if(toaddSchedule.length() == 0){
                            Toast.makeText(getContext(), "일정을 입력하세요", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            dataArray.add(addSchedule(getResources().getDrawable(R.drawable.black_circle), toaddSchedule));
                            scheduleRecyclerAdapter.notifyDataSetChanged();
                            noScheduleTextView.setText("");
                            chk++;
                            editor.putInt("" + focusedyear + "/" + focusedmonth + "/" + focuseddate, chk);
                            editor.putString("" + focusedyear + "/" + focusedmonth + "/" + focuseddate + "/" + chk, toaddSchedule);
                            editor.apply();
                            Log.e("sh", "" + focusedyear + "/" + focusedmonth + "/" + focuseddate + "/" + chk);
                            Toast.makeText(getContext(), "추가되었습니다.", Toast.LENGTH_SHORT).show();
                        }
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

        scheduleRecyclerAdapter.setOnItemClickListener(new ScheduleRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, final int position) {
                String schedule = dataArray.get(position).getSchedule();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("일정 삭제하기");
                builder.setMessage("\""+schedule+"\""+"일정을 삭제하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int check;
                        check = sharedPreferences.getInt("" + focusedyear + "/" + focusedmonth + "/" + focuseddate,0);

                        dataArray.remove(position);
                        scheduleRecyclerAdapter.notifyDataSetChanged();

                        for(int k=1;k<=check;k++){
                            String sch = sharedPreferences.getString("" + focusedyear + "/" + focusedmonth + "/" + focuseddate + "/" + k, "헿");
                            if(k>position+1){
                                editor.putString("" + focusedyear + "/" + focusedmonth + "/" + focuseddate + "/" + (k-1),sch);
                                editor.apply();
                            }
                        }
                        Log.e("dele","chk"+check);
                        check--;
                        editor.putInt("" + focusedyear + "/" + focusedmonth + "/" + focuseddate,check);
                        editor.apply();
                        if(check == 0){
                            noScheduleTextView.setText("( 일정 없음 )");
                        }
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

}
