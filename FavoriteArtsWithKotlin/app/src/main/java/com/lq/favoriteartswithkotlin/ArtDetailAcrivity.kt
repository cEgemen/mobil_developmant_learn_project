package com.lq.favoriteartswithkotlin

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteStatement
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lq.favoriteartswithkotlin.databinding.ActivityArtDetailAcrivityBinding
import java.io.ByteArrayOutputStream

class ArtDetailAcrivity : AppCompatActivity() {
     lateinit var  binding : ActivityArtDetailAcrivityBinding;
     lateinit var type : String;
     lateinit var  db :SQLiteDatabase;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtDetailAcrivityBinding.inflate((layoutInflater));
        db = this.openOrCreateDatabase("Arts", MODE_PRIVATE,null);
        setContentView(binding.root);
        type = intent.getStringExtra("type")!!;
        setView(type);
    }

    fun setView(type : String)
    {
          if(type == "new")
          {
             binding.detailTitleTextView.text = "ADD ART";
             binding.button.text = "SAVE";
              binding.button.setOnClickListener {
                  db.execSQL("CREATE TABLE IF NOT EXISTS arts (id INTEGER PRİMARY KEY , artName VARCHAR,artistName VARCHAR,year VARCHAR,img BLOB)")
                  val statement = db.compileStatement("INSERT INTO arts (artName,artistName,year,img) VALUES(?,?,?,?)");
                  statement.bindString(1,binding.artNamEditTextText.text.toString());
                  statement.bindString(2,binding.artistNamEditTextText.text.toString());
                  statement.bindString(3,binding.artYearEditTextText.text.toString());

                  val smallImg =  buildSmallImg(imgBit,300);
                  val outputArray = ByteArrayOutputStream();
                  smallImg.compress(Bitmap.CompressFormat.PNG,100,outputArray);
                  statement.bindBlob(4,outputArray.toByteArray());
                  statement.execute()

                  val intent = Intent(this@ArtDetailAcrivity,MainActivity::class.java);
                  startActivity(intent);
                  finish()
              }

          }
        else{
              binding.detailTitleTextView.text = "ART DETAIL";
              binding.button.text = "BACK";
              binding.artImageView.isEnabled =false;
              binding.artNamEditTextText.isEnabled=false;
              binding.artYearEditTextText.isEnabled=false;
              binding.artistNamEditTextText.isEnabled=false;
              binding.button.setOnClickListener {
                  val intent = Intent(this@ArtDetailAcrivity,MainActivity::class.java);
                  startActivity(intent);
                  finish()
              }
              val id : Int = intent.getIntExtra("id",-1);
              val cursor = db.rawQuery("SELECT * FROM arts WHERE id = ? ", arrayOf(id.toString()))

              val artNameIndex : Int = cursor.getColumnIndex("artName");
              val artistNameIndex : Int = cursor.getColumnIndex("artistName");
              val yearIndex : Int = cursor.getColumnIndex("year");
              val imgIndex : Int = cursor.getColumnIndex("img");

               while(cursor.moveToNext())
               {
                   binding.artNamEditTextText.hint = cursor.getString(artNameIndex);
                   binding.artistNamEditTextText.hint = cursor.getString(artistNameIndex);
                   binding.artYearEditTextText.hint = cursor.getString(yearIndex);
                   val byteArrImg = cursor.getBlob(imgIndex);
                   val bitmapImg = BitmapFactory.decodeByteArray(byteArrImg,0,byteArrImg.size);
                   binding.artImageView.setImageBitmap(bitmapImg)
               }
               cursor.close();


          }
    }

    fun buildSmallImg(img : Bitmap,maxSize:Int):Bitmap{
          var iWitdh = img.width;
          var iHeight = img.height;
          val ratio = iWitdh / iHeight;
          if(ratio > 0.5)
          {
              iWitdh = maxSize;
              iHeight = iWitdh / ratio
          }
        else{
              iHeight = maxSize;
              iWitdh = iHeight * ratio
        }
        return Bitmap.createScaledBitmap(img,iWitdh,iHeight,true);
    }

}