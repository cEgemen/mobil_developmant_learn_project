package com.lq.runcatgame

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DataStore  {

   private constructor(){}

    companion object {
      private var store : DataStore? = null;

      fun getInitStore():DataStore{
              if(store == DataStore())
              {
                 return store!!;
              }
            store = DataStore();
          return store!!;
      }

    }

    fun getHightScore(context:AppCompatActivity) : Int
    {
        val shared : SharedPreferences  =  context.getSharedPreferences("com.lq.runcatgame",Context.MODE_PRIVATE);
        return shared.getInt("highScore",0);
    }

    fun checkHighScore(context:AppCompatActivity,score : Int)
    {
        val shared : SharedPreferences  =  context.getSharedPreferences("com.lq.runcatgame",Context.MODE_PRIVATE);
        val lastHighScore = shared.getInt("highScore",0);
        if(score > lastHighScore)
        {
              shared.edit().putInt("highScore",score).apply();
              Toast.makeText(context,"Your New High Score : "+score,Toast.LENGTH_LONG).show();
            return ;
        }
        Toast.makeText(context,"High Score : "+lastHighScore,Toast.LENGTH_LONG).show();
    }


}