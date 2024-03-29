package com.example.infinitybloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Account extends AppCompatActivity {

    TextView t1, t2, t5, t6, t7;
    String name;
    static String phone;
    FirebaseUser currentUser;
    private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        t1 = findViewById(R.id.logo_text3);
        t2 = findViewById(R.id.logo_text4);
        t5 = findViewById(R.id.logo_text5);
        t6 = findViewById(R.id.logo_text6);
        t7 = findViewById(R.id.logo_text7);
        Intent i = getIntent();
        phone = i.getStringExtra("phone");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();


        String nm = currentUser.getEmail();
        String phn = nm.substring(0, 10);
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(phn);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    name = dataSnapshot.child("name").getValue().toString();
                    t1.setText(name);
                    t5.setText("Available to donate: " + dataSnapshot.child("active").getValue().toString());
                    t6.setText("District: " + dataSnapshot.child("district").getValue().toString());
                    t7.setText("Blood group: " + dataSnapshot.child("blood").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        //System.out.println(name + " okhane");
        t1.setText(name);

        t2.setText("+880" + phn);
    }

    public static String getPhone(){
        return phone;
    }
    public void EditButtonClick(View v){
        Intent i = new Intent(this, EditProfile.class);
        startActivity(i);
    }


    public void YreqButtonClick(View v){
        Intent i = new Intent(this, MyRequest.class);
        i.putExtra("phone", phone);
        startActivity(i);
    }

    public void SignOutButtonClick(View v){
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(this, Login.class);
        this.finish();
        startActivity(i);
    }
}