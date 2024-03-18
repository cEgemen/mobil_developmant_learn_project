package com.lq.favouriteartswithnavigationandfragment

import android.Manifest
import android.app.Activity.MODE_PRIVATE
import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.media.browse.MediaBrowser.MediaItem
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.scale
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.lq.favouriteartswithnavigationandfragment.databinding.FragmentDetailArtBinding
import java.io.ByteArrayOutputStream
import java.io.OutputStream

class detailArtFragment : Fragment() {
    private lateinit var  binding: FragmentDetailArtBinding;
    private lateinit var  permissionLaunch : ActivityResultLauncher<String>;
    private lateinit var  activiteLaunch : ActivityResultLauncher<Intent>;
    private lateinit var imgBitMap : Bitmap;
    private lateinit var  type :String ;
    private var art : ArtModel? = null;
    private lateinit var db : SQLiteDatabase;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = this.requireContext().openOrCreateDatabase("ARTS", MODE_PRIVATE,null);
        registerLauncher()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailArtBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments.let {
            type = detailArtFragmentArgs.fromBundle(it!!).type;
             println("type : "+type)
                art = detailArtFragmentArgs.fromBundle(it).art
                println("art : "+art.toString())

        }
           if(type == "new")
           {
              binding.button.text="SAVE";
               binding.imageView.setImageResource(R.drawable.ic_launcher_foreground)
               binding.imageView.setOnClickListener {
                   if(ContextCompat.checkSelfPermission(this.requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                   {
                        if(ActivityCompat.shouldShowRequestPermissionRationale(this.requireActivity(),Manifest.permission.READ_EXTERNAL_STORAGE))
                        {
                            Snackbar.make(it,"YOU WANT SELECT PHOTO , YOU MUST ALLOW PERMISSION",Snackbar.LENGTH_INDEFINITE)
                                .setAction("CLICK FOR ALLOW PERMISSION"){
                                    val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    activiteLaunch.launch(intent)
                                }
                                .show()
                        }
                       else{
                             permissionLaunch.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                        }
                   }
                   else{
                       val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                       activiteLaunch.launch(intent)
                   }
               }
               binding.button.setOnClickListener {
                   val name = binding.artNameEditableText.text.toString();
                   val artistName = binding.artistNameEditableText.text.toString();
                   val year = binding.artYearEditableText.text.toString();
                   db.execSQL("CREATE TABLE IF NOT EXISTS arts (id INTEGER PRIMARY KEY,artName VARCHAR,artistName VARCHAR,year VARCHAR,img BLOB)")
                   val statemant =  db.compileStatement("INSERT INTO arts (artName,artistName,year,img) VALUES(?,?,?,?)");
                   statemant.bindString(1,name);
                   statemant.bindString(2,artistName);
                   statemant.bindString(3,year);
                   imgBitMap = makeSmallImg(imgBitMap,300);
                   var outputArray = ByteArrayOutputStream();
                    imgBitMap.compress(Bitmap.CompressFormat.PNG,100,outputArray)
                   statemant.bindBlob(4,outputArray.toByteArray());
                   statemant.execute();
                   var action = detailArtFragmentDirections.actionDetailArtFragmentToFavouriArtsFragment();
                   Navigation.findNavController(view).navigate(action);
               }

           }
          else{
               binding.imageView.isEnabled = false
               binding.button.text = "DELETE"
               binding.button.setOnClickListener {
                   db.execSQL("DELETE FROM arts WHERE id = ? ", arrayOf(art!!.id.toString()))
                   var action = detailArtFragmentDirections.actionDetailArtFragmentToFavouriArtsFragment();
                   Navigation.findNavController(view).navigate(action);
               }
               binding.artNameEditableText.isEnabled=false;
               binding.artistNameEditableText.isEnabled=false;
               binding.artYearEditableText.isEnabled=false
               val cursor = db.rawQuery("SELECT * from arts where id = ?", arrayOf(art!!.id.toString()))
               val nameIndex = cursor.getColumnIndex("artName");
               val artistNameIndex = cursor.getColumnIndex("artistName");
               val yearIndex = cursor.getColumnIndex("year");
               val imgIndex = cursor.getColumnIndex("img");

               while(cursor.moveToNext())
               {
                   binding.artNameEditableText.hint = cursor.getString(nameIndex);
                   binding.artistNameEditableText.hint = cursor.getString(artistNameIndex);
                   binding.artYearEditableText.hint = cursor.getString(yearIndex);
                   val byteImg =  cursor.getBlob(imgIndex);
                   binding.imageView.setImageBitmap(BitmapFactory.decodeByteArray(byteImg,0,byteImg.size,null))
               }
               cursor.close();


           }


    }

    fun makeSmallImg(img : Bitmap , maxSize : Int) : Bitmap {
        var iWidth = img.width;
        var iHeight = img.height;
        val ratio = iWidth / iHeight;
        if(ratio >= 0.5)
        {
            iWidth = maxSize;
            iHeight = iWidth / ratio;
        }
        else{
            iHeight = maxSize;
            iWidth = iHeight * ratio;
        }
        return img.scale(iWidth,iHeight,true);
    }

    fun registerLauncher() {
          activiteLaunch = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),{
                if (it.resultCode == RESULT_OK)
                {
                    if(it.data != null)
                    {
                        if(it.data!!.data != null)
                        {
                             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                                 val source = ImageDecoder.createSource(this.requireContext().contentResolver,it.data!!.data!!)
                                  imgBitMap = ImageDecoder.decodeBitmap(source);
                                  binding.imageView.setImageBitmap(imgBitMap)
                            } else {
                                  imgBitMap = MediaStore.Images.Media.getBitmap(this.requireContext().contentResolver,it!!.data!!.data);
                                  binding.imageView.setImageBitmap(imgBitMap)
                            };
                        }
                    }
                }
          })

          permissionLaunch = registerForActivityResult(ActivityResultContracts.RequestPermission(),{
               if(it)
               {
                   val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                   activiteLaunch.launch(intent)
               }
              else{
                    Toast.makeText(this.requireContext(),"YOU REJECT PERMISSION",Toast.LENGTH_LONG).show()
               }
          })

    }
}