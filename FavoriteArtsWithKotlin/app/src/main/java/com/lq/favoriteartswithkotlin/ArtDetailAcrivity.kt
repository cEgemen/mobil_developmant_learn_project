package com.lq.favoriteartswithkotlin

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteStatement
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build.VERSION
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.lq.favoriteartswithkotlin.databinding.ActivityArtDetailAcrivityBinding
import java.io.ByteArrayOutputStream

class ArtDetailAcrivity : AppCompatActivity() {
     lateinit var  binding : ActivityArtDetailAcrivityBinding;
     lateinit var  permissionLaunch : ActivityResultLauncher<String> ;
      lateinit var  activityLaunch : ActivityResultLauncher<Intent> ;
     lateinit var type : String;
     lateinit var  db :SQLiteDatabase;
     var artImage : Bitmap? =null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtDetailAcrivityBinding.inflate((layoutInflater));
        db = this.openOrCreateDatabase("Arts", MODE_PRIVATE,null);
        setContentView(binding.root);
        regesterLauncher();
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
                  val smallImg =  buildSmallImg(artImage!!,300);
                  val outputArray = ByteArrayOutputStream();
                  smallImg.compress(Bitmap.CompressFormat.PNG,100,outputArray);
                  statement.bindBlob(4,outputArray.toByteArray());
                  statement.execute()

                  val intent = Intent(this@ArtDetailAcrivity,MainActivity::class.java);
                  startActivity(intent);
                  finish()
              }
              binding.artImageView.setImageResource(R.drawable.ic_launcher_foreground)
              binding.artImageView.setOnClickListener {
                       if(ContextCompat.checkSelfPermission(this@ArtDetailAcrivity,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                       {
                            if(ActivityCompat.shouldShowRequestPermissionRationale(this@ArtDetailAcrivity,Manifest.permission.READ_EXTERNAL_STORAGE))
                            {
                              Snackbar.make(it,"IF YOU SELECYT IMG YOU MUST ALLOW PERMISSION",Snackbar.LENGTH_INDEFINITE).setAction("ALLOW PERMISION",
                                  View.OnClickListener { view ->
                                                 permissionLaunch.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                                  }).show()
                            }
                           else{
                                permissionLaunch.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                            }

                       }
                      else{
                          val intent : Intent =  Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                           activityLaunch.launch(intent)
                       }
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

    fun regesterLauncher (){
         activityLaunch = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
             ActivityResultCallback {result ->
                             if(result.resultCode == RESULT_OK)
                             {
                                   val resultIntent = result.data;
                                   if(resultIntent != null)
                                   {
                                         val resultResultIntent = resultIntent.data;
                                         if(resultResultIntent != null)
                                         {
                                             artImage = if(VERSION.SDK_INT >= 28) {
                                                 val source = ImageDecoder.createSource(this@ArtDetailAcrivity.contentResolver,resultResultIntent);
                                                 ImageDecoder.decodeBitmap(source);
                                             } else{
                                                 MediaStore.Images.Media.getBitmap(this@ArtDetailAcrivity.contentResolver,resultResultIntent);
                                             }
                                             binding.artImageView.setImageBitmap(artImage);
                                         }
                                   }
                             }
             });

         permissionLaunch = registerForActivityResult(ActivityResultContracts.RequestPermission()){result->
                      if(result)
                      {
                          val intent : Intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                          activityLaunch.launch(intent);
                      }
             else{
                   Toast.makeText(this@ArtDetailAcrivity,"ERROR",Toast.LENGTH_LONG).show()
                }
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