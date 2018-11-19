package com.grup22.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grup22.ProfileActivity;
import com.grup22.QuizActivity;
import com.grup22.R;
import com.grup22.model.Data;

import java.util.List;

import info.hoang8f.widget.FButton;

/**
 * Created by hp-pc on 18.11.2018.
 */

public class mViewHolder  extends RecyclerView.ViewHolder {

    private static final String TAG = "mViewHolder";
    private final Activity activity;
    TextView question;
    public static Button option2, option3, option4;
    public static FButton option1;

    int ilkKazanc =0;

    private ButtonClickListener onClickBtnListener = new ButtonClickListener();

    public static int toplamDogruCevap = 0;

    public mViewHolder(Activity activity,View itemView) {
        super(itemView);
        this.activity = activity;
        question = (TextView) itemView.findViewById(R.id.mQuestion);
        option1 = (FButton) itemView.findViewById(R.id.option1);
        option2 = (Button) itemView.findViewById(R.id.option2);
        option3 = (Button) itemView.findViewById(R.id.option3);
        option4 = (Button) itemView.findViewById(R.id.option4);


    }
    public void bind(final Data data, int position){
      question.setText(data.getQuestion());
      option1.setText(data.getOptionsA());
      option2.setText(data.getOptionsB());
      option3.setText(data.getOptionsC());
      option4.setText(data.getOptionsD());

      option1.setTag(position);
      option2.setTag(position);
      option3.setTag(position);
      option4.setTag(position);

      /*option1.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              //Toast.makeText(activity, "Options 1", Toast.LENGTH_SHORT).show();

              String text = option1.getText().toString();

              Data trueData = new Data();

              if(text.equals(trueData.getTrueAnswer())){
                  Toast.makeText(activity, "Doğru cevap, tebrikler!", Toast.LENGTH_SHORT).show();

              }
              else{
                  Toast.makeText(activity, "Yanlış cevap", Toast.LENGTH_SHORT).show();
              }
          }
      });*/

      option1.setOnClickListener(onClickBtnListener);
      option2.setOnClickListener(onClickBtnListener);
      option3.setOnClickListener(onClickBtnListener);
      option4.setOnClickListener(onClickBtnListener);
    }


    public class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            String text = "";

            int buttonId=0;

            List<Data> trueDatas = QuizActivity.posts;

            if(v.getId()==R.id.option1)
            {
                int i = (Integer) v.getTag();
                text = trueDatas.get(i).getOptionsA();
                buttonId = v.getId();
            }
            else if (v.getId()==R.id.option2)
            {
                int i = (Integer) v.getTag();
                text = trueDatas.get(i).getOptionsB();
                buttonId = v.getId();
            }
            else if (v.getId()==R.id.option3)
            {
                int i = (Integer) v.getTag();
                text = trueDatas.get(i).getOptionsC();
                buttonId = v.getId();
            }
            else if (v.getId()==R.id.option4)
            {
                int i = (Integer) v.getTag();
                text = trueDatas.get(i).getOptionsD();
                buttonId = v.getId();
            }

            int i = (Integer) v.getTag();

            if(text.equals(trueDatas.get(i).getTrueAnswer())){
                //Toast.makeText(activity, "Doğru cevap, tebrikler!", Toast.LENGTH_SHORT).show();

                FButton button = v.findViewById(buttonId);
                button.setShadowColor(activity.getResources().getColor(R.color.grey));
                button.setCornerRadius(10);
                button.setButtonColor(activity.getResources().getColor(android.R.color.holo_green_dark));

                toplamDogruCevap++;
            }
            else{
                //Toast.makeText(activity, "Yanlış cevap", Toast.LENGTH_SHORT).show();

                FButton button = v.findViewById(buttonId);
                button.setShadowColor(activity.getResources().getColor(R.color.grey));
                button.setCornerRadius(10);
                button.setButtonColor(activity.getResources().getColor(android.R.color.holo_red_dark));
            }

            if(i + 1 == trueDatas.size())
            {
                if(toplamDogruCevap == i + 1)
                {
                    toplamDogruCevap = 0;
                    //Toast.makeText(activity, "Kazandınız", Toast.LENGTH_SHORT).show();

                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    final String uid = auth.getCurrentUser().getUid();

                    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
                    mData.child("topics").child(uid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ilkKazanc =Integer.parseInt( dataSnapshot.child("kazanc").getValue(String.class));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    correctDialog();
                }
                else
                {
                    toplamDogruCevap = 0;
                    //Toast.makeText(activity, "Kaybettiniz", Toast.LENGTH_SHORT).show();
                    negativeDialog();
                }
            }
            /*else
            {
                Toast.makeText(activity, "Kaybettiniz", Toast.LENGTH_SHORT).show();
            }*/
        }

    }
    public void correctDialog() {
        final Dialog dialogCorrect = new Dialog(activity);
        dialogCorrect.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogCorrect.getWindow() != null) {
            ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
            dialogCorrect.getWindow().setBackgroundDrawable(colorDrawable);
        }
        dialogCorrect.setContentView(R.layout.dialog_final);
        dialogCorrect.setCancelable(false);
        dialogCorrect.show();

        //Since the dialog is show to user just pause the timer in background



        TextView correctText = (TextView) dialogCorrect.findViewById(R.id.correctText);
        FButton buttonNext = (FButton) dialogCorrect.findViewById(R.id.dialogNext);



        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogCorrect.dismiss();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                final String uid = auth.getCurrentUser().getUid();

                DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
                mData.child("topics").child(uid).child("kazanc").setValue(String.valueOf(ilkKazanc + QuizActivity.deger));
                Intent intent = new Intent(activity, ProfileActivity.class);
                activity.startActivity(intent);
            }
        });
    }
    public void negativeDialog() {
        final Dialog dialogCorrect = new Dialog(activity);
        dialogCorrect.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogCorrect.getWindow() != null) {
            ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
            dialogCorrect.getWindow().setBackgroundDrawable(colorDrawable);
        }
        dialogCorrect.setContentView(R.layout.dialog_lose);
        dialogCorrect.setCancelable(false);
        dialogCorrect.show();

        //Since the dialog is show to user just pause the timer in background



        TextView correctText = (TextView) dialogCorrect.findViewById(R.id.correctText);
        FButton buttonNext = (FButton) dialogCorrect.findViewById(R.id.dialogNext);



        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogCorrect.dismiss();
                Intent intent = new Intent(activity, ProfileActivity.class);
                activity.startActivity(intent);
            }
        });
    }
}

