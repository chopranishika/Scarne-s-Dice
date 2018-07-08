package com.example.nishika.scarnesdice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    int userCurrent=0,userTotal=0,cpuCurrent=0,cpuTotal=0,r;
    Button roll,hold,reset;
    Boolean isUserTurn= true;
    ImageView diceImage;
    TextView userCurrentTextView,userTotalTextView,cpuCurrentTextView,cpuTotalTextView;
    int images[]={R.drawable.dice1,R.drawable.dice2,R.drawable.dice3,R.drawable.dice4,R.drawable.dice5,R.drawable.dice6};
    Random random;
    String uc="User Current:",ut="User Total:",cc="CPU Current:",ct="CPU Total:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        roll = (Button) findViewById(R.id.roll);
        hold = (Button) findViewById(R.id.hold);
        reset = (Button) findViewById(R.id.reset);
        userCurrentTextView = (TextView) findViewById(R.id.usercurrent);
        userTotalTextView = (TextView) findViewById(R.id.usertotal);
        cpuCurrentTextView = (TextView) findViewById(R.id.cpucurrent);
        cpuTotalTextView = (TextView) findViewById(R.id.cputotal);
        diceImage = (ImageView) findViewById(R.id.dice);
        roll.setOnClickListener(this);
        hold.setOnClickListener(this);
        reset.setOnClickListener(this);
        random= new Random();
        if(isUserTurn)
        {
            Toast.makeText(getApplicationContext(),"Your Turn",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"CPU's Turn",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.roll:
            {
                roll();
                break;
            }
            case R.id.hold:
            {
                hold();
                break;
            }
            case R.id.reset:
            {
                reset();
                break;
            }
        }
    }

    public void roll(){
        r=random.nextInt(6);
        diceImage.setImageResource(images[r]);
        r++;
        if(isUserTurn)
        {
            if(r==1)
            {
                userCurrent=0;
                hold();
            }
            else
            {
                userCurrent=userCurrent+r;
                updateUI();
            }
        }
        else
        {
            if (r==1)
            {
                cpuCurrent=0;
                hold();
            }
            else
            {
                cpuCurrent=cpuCurrent+r;
                updateUI();
            }
        }
    }

    public void hold(){
        if(cpuTotal>=100||userTotal>=100)
        {
            Toast.makeText(MainActivity.this,(cpuTotal>=100?"Computer wins":"user wins"), Toast.LENGTH_SHORT).show();
            reset();
        }
         if(isUserTurn)
         {
             userTotal=userCurrent+userTotal;
             userCurrent=0;
             isUserTurn=false;
             updateUI();
             computerTurn();
         }
        else
         {
             cpuTotal=cpuCurrent+cpuTotal;
             isUserTurn=true;
             cpuCurrent=0;
             computerTurn();
         }
    }

    public void reset(){
        cpuTotal=0;
        cpuCurrent=0;
        userCurrent=0;
        userTotal=0;
        updateUI();
    }

    public void updateUI(){
        userCurrentTextView.setText(uc+userCurrent);
        userTotalTextView.setText(ut+userTotal);
        cpuCurrentTextView.setText(cc+cpuCurrent);
        cpuTotalTextView.setText(ct+cpuTotal);
    }

    public void computerTurn(){
                if(!isUserTurn)
                {

                    if(cpuCurrent<20) {
                        roll();
                        new android.os.Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                computerTurn();
                            }
                        },1000);
                    }
                    else{
                        hold();}
                    }


        updateUI();
    }


}
