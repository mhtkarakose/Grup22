package com.grup22.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.grup22.QuizActivity;
import com.grup22.R;
import com.grup22.model.Data;

import java.util.List;

/**
 * Created by hp-pc on 18.11.2018.
 */

public class mViewHolder  extends RecyclerView.ViewHolder {

    private static final String TAG = "mViewHolder";
    private final Activity activity;
    TextView question;
    public static Button option1, option2, option3, option4;

    private ButtonClickListener onClickBtnListener = new ButtonClickListener();

    public static int toplamDogruCevap = 0;

    public mViewHolder(Activity activity,View itemView) {
        super(itemView);
        this.activity = activity;
        question = (TextView) itemView.findViewById(R.id.mQuestion);
        option1 = (Button) itemView.findViewById(R.id.option1);
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

            List<Data> trueDatas = QuizActivity.posts;

            if(v.getId()==R.id.option1)
            {
                int i = (Integer) v.getTag();
                text = trueDatas.get(i).getOptionsA();
            }
            else if (v.getId()==R.id.option2)
            {
                int i = (Integer) v.getTag();
                text = trueDatas.get(i).getOptionsB();
            }
            else if (v.getId()==R.id.option3)
            {
                int i = (Integer) v.getTag();
                text = trueDatas.get(i).getOptionsC();
            }
            else if (v.getId()==R.id.option4)
            {
                int i = (Integer) v.getTag();
                text = trueDatas.get(i).getOptionsD();
            }

            int i = (Integer) v.getTag();

            if(text.equals(trueDatas.get(i).getTrueAnswer())){
                Toast.makeText(activity, "Doğru cevap, tebrikler!", Toast.LENGTH_SHORT).show();

                toplamDogruCevap++;

                if(i + 1 == trueDatas.size())
                {
                    if(toplamDogruCevap == i + 1)
                    {
                        Toast.makeText(activity, "Kazandınız", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(activity, "Kaybettiniz", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else{
                Toast.makeText(activity, "Yanlış cevap", Toast.LENGTH_SHORT).show();
            }
        }

    }
}

