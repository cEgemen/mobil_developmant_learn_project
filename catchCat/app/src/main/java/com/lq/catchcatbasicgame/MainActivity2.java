package com.lq.catchcatbasicgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity2 extends AppCompatActivity {
    Runnable runnnable;
   Handler handler;
    CountDownTimer sayac;
    AlertDialog.Builder alert ;
   boolean isPause = false;
   boolean isReset = false;
    TextView timebord ;
    TextView skorboard;
    int time = 10;
    int skor = 0;
    String timeboardText = "TIME: ";
    String skorboardText = "SKOR: ";
    ImageView cat ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        handler = new Handler();
        alert =  new  AlertDialog.Builder(MainActivity2.this);
        timebord = findViewById(R.id.timeTextView);
        timebord.setText(timeboardText+time);
        skorboard = findViewById(R.id.skorTextView);
        skorboard.setText(skorboardText+skor);
        cat = findViewById(R.id.runCatImageView);
       sayac = new CountDownTimer((time*1000),1000){
            @Override
            public void onTick(long l) {
                 time--;
                 timebord.setText(timeboardText+time);
            }
            @Override
            public void onFinish() {
                handler.removeCallbacks(runnnable);
                alert.setMessage("Bastan Baslamak İster Misin ? ");
                alert.setTitle("Süre : "+time+ "                        Skor : "+skor);
                alert.setPositiveButton("EVET",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        start();
                    }
                });
                alert.setNegativeButton("HAYIR",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent =new Intent(MainActivity2.this,MainActivity.class);
                        startActivity(intent);
                    }
                });
                alert.show();
            }
        }.start();
        generateKordinat();
    }
   public void caught(View view)
   {
        skor++;
        skorboard.setText(skorboardText+skor);
   }

   public void pause(View view)
   {
 if(!isReset) {
     if (!isPause) {

         isPause = !isPause;
         sayac.cancel();
         handler.removeCallbacks(runnnable);
         return;
     }
     isPause = !isPause;
     handler.post(runnnable);
 }
   }
   public void reset(View view)
   {
     if(!isReset)
     {
         isReset  = !isReset;
         handler.removeCallbacks(runnnable);
         sayac.cancel();
         skorboard.setText(skorboardText+0);
         timebord.setText(timeboardText+0);
         alert.setMessage("Bastan Baslamak İster Misin ? ");
         alert.setTitle("Süre : "+time+ "                           Skor : "+skor);
         alert.setPositiveButton("EVET",new DialogInterface.OnClickListener(){

             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                 start();
             }
         });
         alert.setNegativeButton("HAYIR",new DialogInterface.OnClickListener(){

             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                 Intent intent =new Intent(MainActivity2.this,MainActivity.class);
                 startActivity(intent);
             }
         });
         alert.show();
     }

   }

   void generateKordinat()
   {
        runnnable = new Runnable() {
            @Override
            public void run() {
                float x = new Random().nextInt(770);
                float y = new Random().nextInt(1520)+120;
                cat.setTranslationX(x);
                cat.setTranslationY(y);
             handler.postDelayed(runnnable,500);
            }
        };
        handler.post(runnnable);
   }

    public void start()
    {
         time = 10;
         skor = 0;
         isPause = false;
         isReset = false;
         skorboard.setText(skorboardText+skor);
         timebord.setText(timeboardText+time);
         sayac.start();
         handler.post(runnnable);
    }

}