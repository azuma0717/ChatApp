package com.gmail.yuki.chatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editMessege);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Messages");

    }

    public void sendButtonClicked(View view){

        final String messageValue = editText.getText().toString().trim();
        if(!TextUtils.isEmpty(messageValue)){

            final DatabaseReference newPost = mDatabase.push();
            newPost.child("content").setValue(messageValue);


        }


    }


}
