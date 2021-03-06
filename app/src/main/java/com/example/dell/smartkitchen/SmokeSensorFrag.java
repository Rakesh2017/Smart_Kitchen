package com.example.dell.smartkitchen;


import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class SmokeSensorFrag extends Fragment {

    TextView status1;
    TextView status2;
    TextView realtimedata1;
    TextView realtimedata2;
    TextView alarm1;
    TextView alarm2;
    TextView fan1;
    TextView fan2;
    TextView leak1;
    TextView leak2;
    TextView head;
    TextView condition1;
    TextView condition2;

    Button button1;
    Button button2;

    Animation animation1;
    Animation animation2;
    Animation animation3;

    DatabaseReference dparent = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dref = dparent.child("LpuHistoricalData");
    DatabaseReference dref1 = dref.child("Smoke");
  //  Query lastQuery = dref.child("Smoke").orderByKey().limitToLast(1);




    public SmokeSensorFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_smoke_sensor, container, false);
        animation1= AnimationUtils.loadAnimation(getContext(),R.anim.realrlanimate);
        animation2= AnimationUtils.loadAnimation(getContext(),R.anim.reallranimate);
        animation3= AnimationUtils.loadAnimation(getContext(),R.anim.blink);

        button1=(Button)view.findViewById(R.id.ssb1);
        button1.startAnimation(animation2);
        button2=(Button)view.findViewById(R.id.ssb2);
        button2.startAnimation(animation1);

        status1=(TextView)view.findViewById(R.id.ss1);
        status2=(TextView)view.findViewById(R.id.ss2);

        realtimedata1=(TextView)view.findViewById(R.id.ss3);
        realtimedata2=(TextView)view.findViewById(R.id.ss4);

        alarm1=(TextView)view.findViewById(R.id.ss5);
        alarm2=(TextView)view.findViewById(R.id.ss6);

        fan1=(TextView)view.findViewById(R.id.ss7);
        fan2=(TextView)view.findViewById(R.id.ss8);

        leak1=(TextView)view.findViewById(R.id.ss9);
        leak2=(TextView)view.findViewById(R.id.ss10);

        condition1=(TextView)view.findViewById(R.id.ss11);
        condition2=(TextView)view.findViewById(R.id.ss12);

        head=(TextView)view.findViewById(R.id.smokesensorheading);
       // head.startAnimation(animation3);

        status1.startAnimation(animation1);
        status2.startAnimation(animation1);
        realtimedata1.startAnimation(animation2);
        realtimedata2.startAnimation(animation2);
        alarm1.startAnimation(animation1);
        alarm2.startAnimation(animation1);
        fan1.startAnimation(animation2);
        fan2.startAnimation(animation2);
        leak1.startAnimation(animation1);
        leak2.startAnimation(animation1);
        condition1.startAnimation(animation2);
        condition2.startAnimation(animation1);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Historicaldata()).addToBackStack(null).commit();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new SmokeWork()).addToBackStack(null).commit();
            }
        });



        return view;
    }

   public void onStart(){
       super.onStart();

       dref1.orderByKey().limitToLast(1).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               int value = 250;
               for (com.google.firebase.database.DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                   try {
                       value = dataSnapshot1.child("value").getValue(Integer.class);

                   } catch (Exception e) {

                   }
               }
                   realtimedata2.setText("" + value);
                   if (value < 400) {
                       alarm2.setText("OFF");
                       fan2.setText("OFF");
                       leak2.setText("NO!");
                       condition2.setText("Normal");
                   } else if (value >= 400 && value <= 600) {
                       DispAlarm();
                       alarm2.setText("ON");
                       fan2.setText("ON");
                       leak2.setText("YES!!!");
                       condition2.setText("Severe!!");
                   } else if (value > 600) {
                       DispAlarm();
                       condition2.setText("Critical");
                   }
                     else if(value==0){
                       status2.setText("not working");
                       alarm2.setText("ON");
                       fan2.setText("ON");
                       leak2.setText("YES!!!");
                       condition2.setText("Severe!!");
                    }


               }

               @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

   }

    public void DispAlarm(){
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getContext(), notification);
        try{
            r.play();
            Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(1000);

        }
        catch (Exception e){

        }







        //notification page
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getContext());

//Create the intent that’ll fire when the user taps the notification//

        Intent intent = new Intent(getContext(), AlertSmoke.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);

        mBuilder.setContentIntent(pendingIntent);

        mBuilder.setSmallIcon(R.drawable.gasleakagenoti);
        mBuilder.setContentTitle("Gas Leakage Alert!!!");
        mBuilder.setContentText("Exaust fan and alarm is turn on...");

        NotificationManager mNotificationManager =

                (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(001, mBuilder.build());

    }



}
