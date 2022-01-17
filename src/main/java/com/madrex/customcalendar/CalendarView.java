package com.madrex.customcalendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
public class CalendarView extends LinearLayout {

    static final int DAYS_COUNT = 42;
    public ImageView btnPrev, btnNext;
    private LinearLayoutCompat header;
    private TextView calendarMonth;
    private GridView gridView;
    private Calendar calendar;

    public CalendarView(Context context) {
        super(context);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initControl(context,attrs);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initialiseComponent(){
        header = findViewById(R.id.calendar_header);
        btnPrev = findViewById(R.id.prev_month);
        btnNext = findViewById(R.id.next_month);
        calendarMonth = findViewById(R.id.calendar_month);
        gridView = findViewById(R.id.calendar_grid);
        calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
    }
    private void initControl(Context context, AttributeSet attributeSet){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.calendar_view,this);
        initialiseComponent();
    }

    public void decMonth(){
        calendar.add(Calendar.MONTH,-1);
    }

    public void incMonth(){
        calendar.add(Calendar.MONTH,1);
    }

    public void updateCalendar(HashMap<Date,DataModel> dataMap, Class<?> intentClass){
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) this.calendar.clone();
        calendar.set(Calendar.DAY_OF_MONTH,1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        while (cells.size()< DAYS_COUNT) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH,1);
        }
        gridView.setAdapter(new CalendarAdapter(getContext(),0,this.calendar,cells,dataMap,intentClass));

        calendarMonth.setText(new SimpleDateFormat("MMM, yyyy").format(this.calendar.getTime()));
    }
}
