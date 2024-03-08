package com.lq.runcatgame

import android.content.DialogInterface
import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.lq.runcatgame.databinding.ActivityGameBinding
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    lateinit var  binding : ActivityGameBinding;
    val shared : DataStore = DataStore.getInitStore();
    var runnable : Runnable = Runnable {};
    lateinit var countDown : CountDownTimer;
    val handler : Handler= Handler(Looper.getMainLooper());
    val scoreText : String = "Score: ";
    val hScoreText:String = "H.Score: ";
    val timeText:String = "Time: ";
    var score:Int=0;
    var highScore:Int = 0;
    var time : Long = 15;
    var isClick : Boolean = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater);
        setContentView(binding.root);
        binding.catImageView.visibility = View.INVISIBLE;
        defaultSettings();
    }

    fun start(view:View){
          binding.pButton.isEnabled=true;
          runnable = object : Runnable{
              override fun run() {
                  val heightStart = (30)* Resources.getSystem().displayMetrics.density;
                  val height= (450+30) * Resources.getSystem().displayMetrics.density;
                  val width = (345) * Resources.getSystem().displayMetrics.density;
                  val catX =Random.nextDouble(0.0,width.toDouble());
                  val catY = Random.nextDouble(heightStart.toDouble(),height.toDouble())
                  binding.catImageView.translationX = catX.toFloat();
                  binding.catImageView.translationY = catY.toFloat();
                  handler.postDelayed(runnable,500);
              }

          }
         handler.post(runnable);
         countDown.start();
        binding.catImageView.visibility=View.VISIBLE;
        binding.sButton.isEnabled = false;
    }
    fun pause(view:View)
    {
       if(isClick)
       {
          binding.pButton.text="Pause"
          isClick = !isClick;
          handler.post(runnable);
          countDown =countDownSyc(time,1000)
       }
        else{
            binding.pButton.text="Resume"
            isClick = !isClick;
            countDown.cancel();
            handler.removeCallbacks(runnable);
       }
    }

    fun catchCat (view : View){
            score +=1;
             binding.scoreTextView.text = scoreText+""+score;
    }

    fun countDownSyc(totalTime :Long,period:Long) : CountDownTimer
    {
         return object :  CountDownTimer(totalTime,period){
             override fun onTick(p0: Long) {
                 time = p0;
                 binding.timeTextView.text=timeText+""+time/1000;
             }
             override fun onFinish() {
                 handler.removeCallbacks(runnable);
                 countDown.cancel();
                 binding.catImageView.visibility = View.INVISIBLE;
                 AlertDialog.Builder(this@GameActivity)
                     .setTitle("GAME OVER")
                     .setMessage("YOUR SCORE : "+score)
                     .setPositiveButton("RESTART",object : DialogInterface.OnClickListener{
                         override fun onClick(p0: DialogInterface?, p1: Int) {
                             shared.checkHighScore(this@GameActivity,score);
                             p0!!.cancel();
                             defaultSettings();
                         }

                     })
                     .setNegativeButton("BACK HOME") { dialogInterface, i ->
                         shared.checkHighScore(this@GameActivity, score);
                         val intent: Intent = Intent(this@GameActivity, MainActivity::class.java);
                         this@GameActivity.startActivity(intent);
                     }
                     .show();
             }
         }
    }

    fun defaultSettings(){
         score=0;
         highScore = 0;
         time  = 15;
         isClick  = false;
        binding.timeTextView.text=timeText+""+time;
        binding.hScoreTextView.text=hScoreText+""+highScore;
        binding.scoreTextView.text=scoreText+""+score;
        binding.sButton.isEnabled = true;
        binding.pButton.text="Pause";
        binding.pButton.isEnabled = false;
        binding.catImageView.setImageResource(R.drawable.tom);
        countDown = countDownSyc(15000,1000)
    }

}