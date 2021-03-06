package com.example.dell.smartkitchen;


import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistorySmokeFrag extends Fragment {


    Button button1;
    Button button2;
    Button button3;


    Animation animation1;
    Animation animation2;




    DatabaseReference dparent = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dref = dparent.child("LpuHistoricalData");
    DatabaseReference dref1 = dref.child("Smoke");



    ListView listview;
    ArrayList<String> list = new ArrayList<>();


    ArrayAdapter<String> adapter;


    public HistorySmokeFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_smoke, container, false);

        listview = (ListView) view.findViewById(R.id.listviewhistorysmoke);
        button1 = (Button) view.findViewById(R.id.last2minutes);
        button2 = (Button) view.findViewById(R.id.last5minutes);
        button3 = (Button) view.findViewById(R.id.last1hour);

        animation1 = AnimationUtils.loadAnimation(getContext(), R.anim.reallranimate);
        animation2 = AnimationUtils.loadAnimation(getContext(), R.anim.realrlanimate);

        button1.startAnimation(animation1);
        button3.startAnimation(animation1);
        button2.startAnimation(animation2);



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                Toast.makeText(getContext(), "Last 2 minute data", Toast.LENGTH_SHORT).show();
                lasttwominutes();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                Toast.makeText(getContext(), "Last 5 minute data", Toast.LENGTH_SHORT).show();
                lastfiveminutes();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                Toast.makeText(getContext(), "Last 1 hour data", Toast.LENGTH_SHORT).show();
                lastonehour();
            }
        });


        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list);
        Collections.reverse(list);

        listview.setAdapter(adapter);
        return view;
    }

public void lasttwominutes(){

        dref1.limitToLast(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                for (com.google.firebase.database.DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    int value=250;
                    String date1="";
                    String time="";
                    try {
                        String date = dataSnapshot1.child("date").getValue(String.class);
                    date1 = date.substring(0, 5);
                        time = dataSnapshot1.child("time").getValue(String.class);

                        value = dataSnapshot1.child("value").getValue(Integer.class);
                    }
                    catch(Exception e){

                    }

                    String status=" ";
                    if(value<400){
                        status="No Leakage";
                    }

                    if(value>=400){
                        status="Leakage!!!";

                    }

                    list.add("*  " + date1 + "     " + time + "       " + value + "   " + status);
                    status=" ";
                }
                Collections.reverse(list);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


    public void lastfiveminutes(){

        dref1.limitToLast(30).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                for (com.google.firebase.database.DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    int value=250;
                    String date1="";
                    String time="";
                    try{
                    String date = dataSnapshot1.child("date").getValue(String.class);
                    date1 = date.substring(0, 5);
                    time = dataSnapshot1.child("time").getValue(String.class);

                        value = dataSnapshot1.child("value").getValue(Integer.class);
                    }
                    catch(Exception e){

                    }

                    String status=" ";
                    if(value<400){
                        status="No Leakage";
                    }

                    if(value>=400){
                        status="Leakage!!!";

                    }

                    list.add("*  " + date1 + "     " + time + "       " + value + "   " + status);
                    status=" ";

                }

                Collections.reverse(list);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


    public void lastonehour(){

        dref1.limitToLast(360).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                for (com.google.firebase.database.DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    int value=250;
                    String date1="";
                    String time="";
                    try{
                    String date = dataSnapshot1.child("date").getValue(String.class);
                    date1 = date.substring(0, 5);
                    time = dataSnapshot1.child("time").getValue(String.class);

                        value = dataSnapshot1.child("value").getValue(Integer.class);
                    }
                    catch(Exception e){

                    }


                    String status=" ";


                    if(value<400){
                        status="No Leakage";
                    }

                    if(value>=400){
                        status="Leakage!!!";

                    }

                    list.add("*  " + date1 + "     " + time + "       " + value + "   " + status);
                    status=" ";
                }

                Collections.reverse(list);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }




}




