package com.grup22;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grup22.adapter.RecyclerAdapter;
import com.grup22.adapter.mViewHolder;
import com.grup22.model.Data;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    public QuizActivity() {
    }

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    public static final String EXTRA_DATA_KEY = "info_cam";
    List<Data> posts;

    DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mRef;
    mViewHolder viewHolder;
    Data trueData;
    String mInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mInfo = getIntent().getStringExtra(EXTRA_DATA_KEY);
        if(mInfo == null) {
            throw new IllegalArgumentException("bos");
        }

        posts = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerAdapter = new RecyclerAdapter(this, posts);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        mRef = mRoot.child("smile");
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Data post = dataSnapshot.getValue(Data.class);
                posts.add(post);
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               trueData = dataSnapshot.getValue(Data.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


if(viewHolder.option1 != null){        viewHolder.option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button option1 =(Button)v;
                String text = option1.getText().toString();
                if(text.equals(trueData.getTrueAnswer())){
                    Toast.makeText(QuizActivity.this, "Doğru cevap, tebrikler!", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(QuizActivity.this, "Yanlış cevap", Toast.LENGTH_SHORT).show();
                }

            }
        }); }
        if(viewHolder.option2 != null) {
        viewHolder.option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button option2 =(Button)v;
                String text = option2.getText().toString();
                if(text.equals(trueData.getTrueAnswer())){
                    Toast.makeText(QuizActivity.this, "Doğru cevap, tebrikler!", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(QuizActivity.this, "Yanlış cevap", Toast.LENGTH_SHORT).show();
                }

            }
        }); }
        if(viewHolder.option3 != null) {
        viewHolder.option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button option3 =(Button)v;
                String text = option3.getText().toString();
                if(text.equals(trueData.getTrueAnswer())){
                    Toast.makeText(QuizActivity.this, "Doğru cevap, tebrikler!", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(QuizActivity.this, "Yanlış cevap", Toast.LENGTH_SHORT).show();
                }

            }
        }); }
        if(viewHolder.option4!= null) {
        viewHolder.option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button option4 =(Button)v;
                String text = option4.getText().toString();
                if(text.equals(trueData.getTrueAnswer())){
                    Toast.makeText(QuizActivity.this, "Doğru cevap, tebrikler!", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(QuizActivity.this, "Yanlış cevap", Toast.LENGTH_SHORT).show();
                }

            }
        }); }
    }
}
