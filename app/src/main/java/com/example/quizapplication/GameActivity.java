package com.example.quizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private TextView QuizQuestion, QuizPoints, QuizQuestionsNo, QuizTimer;
    private Button optBt1, optBt2, optBt3;
    private Button NextBtn;

    int allQuestionsAnswered;
    int questionCounter = 0;
    int Point = 0;

    ColorStateList optBtPicked;
    boolean answered;
    CountDownTimer cdtimer;

    private QuestionsContainer onGoingQuestion;


    private List<QuestionsContainer> questionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        questionsList = new ArrayList<>();
        QuizQuestion = findViewById(R.id.Questions2beAnswered);
        QuizPoints = findViewById(R.id.gamePoints);
        QuizQuestionsNo = findViewById(R.id.questionNo);
        QuizTimer = findViewById(R.id.gameTimer);

        optBt1 = findViewById(R.id.answerA);
        optBt2 = findViewById(R.id.answerB);
        optBt3 = findViewById(R.id.answerC);
        NextBtn = findViewById(R.id.nextBtn);

        optBtPicked = optBt1.getTextColors();



        inputQuestions();
        allQuestionsAnswered = questionsList.size();
        showNextQuestions();

        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answered == false){
                    if(optBt1.callOnClick()){
                    verifyAnswer();
                    cdtimer.cancel();
                    
                    }

                    else {
                        Toast.makeText(GameActivity.this, "Choose an answer", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    showNextQuestions();
                }
            }

            private void verifyAnswer() {
                answered = true;
                Button btPicked = findViewById(NextBtn.getId());
                if(btPicked.equals(onGoingQuestion.getRightAnswerCounter())){
                    Point++;
                    QuizPoints.setText("Point: "+ Point);
                }
                optBt1.setTextColor(Color.RED);
                optBt2.setTextColor(Color.RED);
                optBt3.setTextColor(Color.RED);

                switch (onGoingQuestion.getRightAnswerCounter()){
                    case 1:
                        optBt1.setTextColor(Color.GREEN);
                        break;
                    case 2:
                        optBt2.setTextColor(Color.GREEN);
                        break;
                    case 3:
                        optBt3.setTextColor(Color.GREEN);
                        break;
                }
                if (questionCounter < allQuestionsAnswered){
                    NextBtn.setText("NEXT");
                }else{
                    NextBtn.setText("Complete");
                }

            }
        });



    }

    private void showNextQuestions() {


        optBt1.setTextColor(optBtPicked);
        optBt2.setTextColor(optBtPicked);
        optBt3.setTextColor(optBtPicked);

        if(questionCounter < allQuestionsAnswered){
            timer();
            onGoingQuestion = questionsList.get(questionCounter);
            QuizQuestion.setText(onGoingQuestion.getQuestion());
            optBt1.setText(onGoingQuestion.getAnswerA());
            optBt2.setText(onGoingQuestion.getAnswerB());
            optBt3.setText(onGoingQuestion.getAnswerC());

            questionCounter++;
            NextBtn.setText("SUBMIT");
            QuizQuestionsNo.setText("Question: "+questionCounter+"/"+allQuestionsAnswered);
            answered = false;

        }else{
            finish();
        }
    }

    private void timer() {
        cdtimer = new CountDownTimer(20000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                NumberFormat f = new DecimalFormat("00");
                long min =(millisUntilFinished/60000)%60;
                long sec =(millisUntilFinished/1000)%60;
                QuizTimer.setText(f.format(min)+":"+f.format(sec));
            }

            @Override
            public void onFinish() {
                showNextQuestions();

            }
        }.start();

    }

    private void inputQuestions(){
        questionsList.add(new QuestionsContainer("Who is the most streamed adult film celebrity ?", "Teyana Trump", "Abella Danger", "Lana Rhoades", 2));
        questionsList.add(new QuestionsContainer("Which country won the fifa 2022 World Cup ?", "Argentina", "Portugal", "Brazil", 1));
        questionsList.add(new QuestionsContainer("What year was the google first used ?", "1994", "1998", "1996", 3));
        questionsList.add(new QuestionsContainer("When was google founded ?", "1974", "1998", "1961", 2));
        questionsList.add(new QuestionsContainer("Which player has the most expensive insurance ?", "C.Ronaldo", "Neymar.jr", "L.Messi", 3));
    }

}
