package com.example.dell.smartkitchen;


import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfile extends Fragment {

    Button button;

    TextView profilename;
    TextView profilecontact;
    TextView profilemail;

    DatabaseReference dparent = FirebaseDatabase.getInstance().getReference();
    DatabaseReference refname = dparent.child("username");
    DatabaseReference refcontact = dparent.child("usercontact");
    DatabaseReference refmail = dparent.child("usermail");
    private ProgressDialog mProgress;

    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    ImageView imageView;




    public UserProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_user_profile, container, false);
        button=(Button)view.findViewById(R.id.updatebtn);
        profilename=(TextView)view.findViewById(R.id.profilename);
        profilecontact=(TextView)view.findViewById(R.id.profilecontact);
        profilemail=(TextView)view.findViewById(R.id.profilemail);
        imageView=(ImageView)view.findViewById(R.id.imageloadfromfirebase);
        mProgress=new ProgressDialog(getContext());
        mProgress.setMessage("loading...");
        mProgress.show();
       storageReference.child("ProfilePhotos/userimage").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
           @Override
           public void onSuccess(Uri uri) {
               Log.e("Tuts+", "uri: " + uri.toString());
               Picasso.with(getContext()).load(uri.toString()).into(imageView);
               mProgress.dismiss();
           }
       });





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment someFragment = new Fragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new UpdateUserdata()); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });

        return view;
    }

    public void onStart(){
        super.onStart();

        refname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                profilename.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        refcontact.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String contact = dataSnapshot.getValue(String.class);
                profilecontact.setText(contact);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        refmail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String contact = dataSnapshot.getValue(String.class);
                profilemail.setText(contact);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
