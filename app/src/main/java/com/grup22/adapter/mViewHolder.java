package com.grup22.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.grup22.R;
import com.grup22.model.Data;

/**
 * Created by hp-pc on 18.11.2018.
 */

public class mViewHolder  extends RecyclerView.ViewHolder {

    private static final String TAG = "mViewHolder";
    private final Activity activity;
    TextView question;
   public static Button option1, option2, option3, option4;

    public mViewHolder(Activity activity,View itemView) {
        super(itemView);
        this.activity = activity;
        question = (TextView) itemView.findViewById(R.id.mQuestion);
        option1 = (Button) itemView.findViewById(R.id.option1);
        option2 = (Button) itemView.findViewById(R.id.option2);
        option3 = (Button) itemView.findViewById(R.id.option3);
        option4 = (Button) itemView.findViewById(R.id.option4);


    }
    public void bind(final Data data){
      question.setText(data.getQuestion());
      option1.setText(data.getOptionsA());
      option2.setText(data.getOptionsB());
      option3.setText(data.getOptionsC());
      option4.setText(data.getOptionsD());

    }
}

