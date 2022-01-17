package com.madrex.customcalendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class CalendarAdapter extends ArrayAdapter {
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Date> days;
    private HashMap<Date,DataModel> dataMap;
    private Calendar calendarMonth, calendarToday;
    private Class<?> intentClass;
    public CalendarAdapter(@NonNull Context context, int resource, Calendar calendarMonth, ArrayList<Date> days,
                           HashMap<Date, DataModel> dataMap, Class<?> intentClass) {
        super(context,resource,days);
        this.context = context;
        this.days = days;
        this.dataMap = dataMap;
        this.calendarMonth = calendarMonth;
        this.intentClass = intentClass;
        this.calendarToday = Calendar.getInstance();
        calendarToday.setTime(new Date(System.currentTimeMillis()));
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = inflater.inflate(R.layout.calendar_day,parent,false);

        Calendar gridCalendar = Calendar.getInstance();
        Date gridDate = resetTime(days.get(position));
        gridCalendar.setTime(gridDate);
        int gridDay = gridCalendar.get(Calendar.DATE);
        int gridMonth = gridCalendar.get(Calendar.MONTH);
        int gridYear = gridCalendar.get(Calendar.YEAR);

        ((TextView)convertView.findViewById(R.id.day)).setText(String.valueOf(gridCalendar.get(Calendar.DATE)));
        if(gridMonth == calendarMonth.get(Calendar.MONTH)) {
            //Date same as calendar month shown with black color
            ((TextView)convertView.findViewById(R.id.day)).setTextColor(Color.BLACK);
        } else {
            //Date not in calendar month shown with gray color
            ((TextView)convertView.findViewById(R.id.day)).setTextColor(Color.parseColor("#aeaeae"));
        }

        if(gridDay == calendarToday.get(Calendar.DATE) && gridMonth == calendarToday.get(Calendar.MONTH)
                && gridYear == calendarToday.get(Calendar.YEAR)) {
            convertView.setBackgroundColor(Color.BLACK);
            ((TextView)convertView.findViewById(R.id.day)).setTextColor(Color.WHITE);

        } else if(gridDay != calendarToday.get(Calendar.DATE) && gridMonth == calendarMonth.get(Calendar.MONTH)
                && gridYear == calendarMonth.get(Calendar.YEAR)) {

        }

        convertView.setOnClickListener(v->{
            // below intent will work only if grid date has some information.
            if(dataMap !=null && dataMap.containsKey(gridDate)){
                Intent intent = new Intent(context,intentClass);
                ((Activity)context).startActivity(intent);
            }
        });

        return convertView;
    }

    private Date resetTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();

    }
}
