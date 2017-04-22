package com.example.dell.smartkitchen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistorywaterFrag extends Fragment {


    Button button1;
    Button button2;
    Button button3;
    Button button4;

    Animation animation1;
    Animation animation2;




    DatabaseReference dparent = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dref = dparent.child("LpuHistoricalData");
    DatabaseReference dref1 = dref.child("water");
    ListView listview;
    ArrayList<String> list=new ArrayList<>();

    ArrayAdapter<String> adapter;

    public HistorywaterFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_historywater, container, false);

        listview = (ListView) view.findViewById(R.id.listviewhistorysmoke);
        button1 = (Button) view.findViewById(R.id.last2minuteswater);
        button2 = (Button) view.findViewById(R.id.last5minuteswater);
        button3 = (Button) view.findViewById(R.id.last1hourwater);
        button4 = (Button) view.findViewById(R.id.selectdatewater);
        animation1 = AnimationUtils.loadAnimation(getContext(), R.anim.reallranimate);
        animation2 = AnimationUtils.loadAnimation(getContext(), R.anim.realrlanimate);

        button1.startAnimation(animation1);
        button3.startAnimation(animation1);
        button2.startAnimation(animation2);
        button4.startAnimation(animation2);


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

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Select Date", Toast.LENGTH_SHORT).show();
                adapter.clear();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new CalendarData()).addToBackStack(null).commit();
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
                    String date = dataSnapshot1.child("date").getValue(String.class);
                    String date1 = date.substring(0, 5);
                    String time = dataSnapshot1.child("time").getValue(String.class);
                    int value = dataSnapshot1.child("value").getValue(Integer.class);
                    String status=" ";
                    if(value<100){
                        status="No Overflow";
                    }

                    if(value>=100){
                        status="Overflow!!!";
                    }

                    list.add("*  "+date1 + "     " + time + "       " + value +"     "+ status);
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
                    String date = dataSnapshot1.child("date").getValue(String.class);
                    String date1 = date.substring(0, 5);
                    String time = dataSnapshot1.child("time").getValue(String.class);
                    int value = dataSnapshot1.child("value").getValue(Integer.class);
                    String status=" ";
                    if(value<100){
                        status="No Overflow";
                    }

                    if(value>=100){
                        status="Overflow!!!";
                    }

                    list.add("*  "+date1 + "     " + time + "       " + value +"     "+ status);

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
                    String date = dataSnapshot1.child("date").getValue(String.class);
                    String date1 = date.substring(0, 5);
                    String time = dataSnapshot1.child("time").getValue(String.class);
                    int value = dataSnapshot1.child("value").getValue(Integer.class);
                    String status=" ";
                    if(value<100){
                        status="No Overflow";
                    }

                    if(value>=100){
                        status="Overflow!!!";
                    }

                    list.add("*  "+date1 + "     " + time + "       " + value +"     "+ status);
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
