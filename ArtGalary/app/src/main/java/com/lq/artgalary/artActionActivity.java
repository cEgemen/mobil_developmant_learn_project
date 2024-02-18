package com.lq.artgalary;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.lq.artgalary.databinding.ActivityArtActionBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class artActionActivity extends AppCompatActivity {
    ActivityResultLauncher<Intent> galaryLauncher;
    ActivityResultLauncher<String> permisionLauncher;
    ActivityArtActionBinding binding;
    ByteArrayOutputStream   outputArray = new ByteArrayOutputStream();
    Bitmap selectedImage;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityArtActionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        registerLauncher();
        try {
            db = this.openOrCreateDatabase("Arts", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS arts (id INTEGER PRIMARY KEY,name VARCHAR,artist VARCHAR,year VARCHAR,image BLOB)");
        } catch (Exception e) {
            e.printStackTrace();
        }
       Intent intent = getIntent();
       setLayout(intent.getStringExtra("type"));
    }


    public void setLayout(String type) {
        if (type.equals("new")) {
            binding.artImageView.setImageResource(R.drawable.select_image);
            binding.saveButton.setText("SAVE");
            binding.saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                  try{
                      String sqlQuery = "INSERT INTO arts (name,artist,year,image) VALUES(?,?,?,?)";
                      String artName = binding.nameTextView.getText().toString();
                      String artistName = binding.artistTextView.getText().toString();
                      String artYear = binding.yearTextView.getText().toString();
                      selectedImage.compress(Bitmap.CompressFormat.PNG,80,outputArray);
                      byte [] bytArray = outputArray.toByteArray();
                      SQLiteStatement statement = db.compileStatement(sqlQuery);
                      statement.bindString(1,artName);
                      statement.bindString(2,artistName);
                      statement.bindString(3,artYear);
                      statement.bindBlob(4,bytArray);
                      statement.execute();
                      setIntent();
                  }
                  catch (Exception e)
                  {
                      System.out.println("errrrrrrrrrroooooooooorrrrrrrrrr");
                  }

                }
            });
        } else {
             Intent intent = getIntent();
             int idValue = intent.getIntExtra("id",0);
            try {
                  String query = "SELECT * FROM arts WHERE id ="+idValue;
                  Cursor cursor = db.rawQuery(query,null);
                  int nameIndex = cursor.getColumnIndex("name");
                  int artistIndex = cursor.getColumnIndex("artist");
                  int yearIndex = cursor.getColumnIndex("year");
                  int imageIndex = cursor.getColumnIndex("image");
                  while(cursor.moveToNext())
                  {
                      binding.nameTextView.setText(cursor.getString(nameIndex));
                      binding.nameTextView.setEnabled(false);
                      binding.artistTextView.setText(cursor.getString(artistIndex));
                      binding.artistTextView.setEnabled(false);
                      binding.yearTextView.setText(cursor.getString(yearIndex));
                      binding.yearTextView.setEnabled(false);
                      byte [] byteArray = cursor.getBlob(imageIndex);
                      Bitmap select = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
                      binding.artImageView.setImageBitmap(select);
                      binding.artImageView.setEnabled(false);
                  }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            binding.saveButton.setText("BACK");
            binding.saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setIntent();
                }
            });
        }

    }

    public void setIntent() {
        Intent intent = new Intent(artActionActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        artActionActivity.this.startActivity(intent);
    }


    public void selectImage(View view) {
        if (ContextCompat.checkSelfPermission(artActionActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Snackbar.make(view, "IF YOU WANT SELECT IMAGE YOU ALLOW PERMISSON",
                        Snackbar.LENGTH_LONG).setAction("IF YOU WANT ALLOW", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            permisionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                    }
                }).show();
            } else {
                permisionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galaryLauncher.launch(intent);
        }
    }


    public void registerLauncher() {
        galaryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    System.out.println("pick image");
                    Intent intent = result.getData();
                    Uri data = intent.getData();
                    if (data != null) {
                        System.out.println("image not null");
                        if (Build.VERSION.SDK_INT >= 28) {
                            ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), data);
                            try {
                                selectedImage = ImageDecoder.decodeBitmap(source);
                                binding.artImageView.setImageBitmap(selectedImage);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            try {
                                selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), data);
                                binding.artImageView.setImageBitmap(selectedImage);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }
        });

        permisionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                       if(result)
                       {
                           Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                           galaryLauncher.launch(intent);
                       }
                       else{
                           Toast.makeText(artActionActivity.this,"YOU NOT ALLOW PERMISSION",Toast.LENGTH_LONG).show();
                       }
            }
        });
    }

}