package com.lq.runcatgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.view.View
import com.lq.runcatgame.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {
    lateinit var  binding : ActivityGameBinding;
    var runnable : Runnable = Runnable {};
    lateinit var countDown : CountDownTimer;
    val handler : Handler= Handler(Looper.getMainLooper());
    val scoreText : String = "Score: ";
    val hScoreText:String = "H.Score: ";
    val timeText:String = "Time: ";
    var score:Int=0;
    var highScore:Int = 0;
    var time : Long = 15;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater);
        setContentView(binding.root);
        binding.timeTextView.text=timeText+""+time;
        binding.hScoreTextView.text=hScoreText+""+highScore;
        binding.scoreTextView.text=scoreText+""+scoreText;
        countDown = object  : CountDownTimer(15000,1000){
            override fun onTick(p0: Long) {
                time = p0;
                binding.timeTextView.text=timeText+""+time;
            }
            override fun onFinish() {
                // bitis
            }
        }.start();
    }

    fun start(view:View){
          runnable = object : Runnable{
              override fun run() {

                  // image move

                  handler.postDelayed(runnable,400);
              }

          }
         handler.post(runnable);
         binding.sButton.visibility=View.INVISIBLE;
    }
    fun pause(view:View)
    {
        countDown.cancel();
        handler.removeCallbacks(runnable);


        binding.sButton.visibility=View.VISIBLE;
    }


}