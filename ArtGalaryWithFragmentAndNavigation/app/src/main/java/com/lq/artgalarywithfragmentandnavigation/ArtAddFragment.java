package com.lq.artgalarywithfragmentandnavigation;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.lq.artgalarywithfragmentandnavigation.databinding.ArtAddFragmentBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.datatype.Duration;
import javax.xml.transform.Result;

public class ArtAddFragment extends Fragment {
    ActivityResultLauncher<String> permissionLauncher;
    ActivityResultLauncher<Intent> galaryLauncher;

    ByteArrayOutputStream imageByte;

    Bitmap imageBitMap;
     ArtAddFragmentBinding binding;
     SQLiteDatabase db;
     SQLiteStatement statement;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ArtAddFragmentBinding.inflate(getLayoutInflater());
        registerLauncher(binding.getRoot().getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    private void setLayout(String type,Context context) throws IOException {
            if(type.equals("new"))
            {
                 binding.artFragmentButton.setText("SAVE");
                 binding.artFragmentButton.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {

                     }
                 });
                 binding.artImageView.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                            if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                            {
                                if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),Manifest.permission.READ_EXTERNAL_STORAGE))
                                {
                                    Snackbar.make(view,"YOU MUST ALLOW PERMISSION",Snackbar.LENGTH_INDEFINITE).setAction("CLICK FOR ALLOW PERMISSION", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                               permissionLauncher.launch( Manifest.permission.READ_EXTERNAL_STORAGE);
                                        }
                                    }).show();
                                }
                                else{
                                    permissionLauncher.launch( Manifest.permission.READ_EXTERNAL_STORAGE);
                                }
                            }
                            else{
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                galaryLauncher.launch(intent);
                            }

                     }
                 });
            }
            else{
                  binding.artFragmentButton.setText("BACK");
                  binding.artFragmentButton.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          NavDirections action = ArtAddFragmentDirections.actionArtAddFragmentToRecyclerFragment();
                          Navigation.findNavController(view).navigate(action);
                      }
                  });
                   Bundle bundle = getArguments();
                     if(bundle != null)
                     {
                         binding.artNameEditText.setEnabled(false);
                         binding.artNameEditText.setHint(ArtAddFragmentArgs.fromBundle(bundle).getArt().name);
                         byte []   byteArr = ArtAddFragmentArgs.fromBundle(bundle).getArt().image;
                        // binding.artImageView.setImageBitmap();
                         if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                             ImageDecoder.Source s = ImageDecoder.createSource(byteArr);
                                     ImageDecoder.decodeBitmap(s);
                         }
                     }
                     binding.artImageView.setEnabled(false);
                }
    }



    private void registerLauncher(Context context)
    {
          galaryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
              @Override
              public void onActivityResult(ActivityResult o) {
                        if(o.getResultCode() == RESULT_OK)
                        {
                            Intent intent = o.getData();
                            if(o.getData() != null)
                            {
                                Uri imageUri = intent.getData();
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P && imageUri != null) {
                                    ImageDecoder.Source source =  ImageDecoder.createSource(context.getContentResolver(),imageUri);
                                    try {
                                        imageBitMap = ImageDecoder.decodeBitmap(source);
                                        binding.artImageView.setImageBitmap(imageBitMap);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        }
              }
          });

          permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
              @Override
              public void onActivityResult(Boolean o) {
                   if(o)
                   {
                       Intent intent = new Intent(Intent.ACTION_PICK);
                       galaryLauncher.launch(intent);
                   }

              }
          });

    }

}
