package com.lq.myfavoritelocation;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.room.Database;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.lq.myfavoritelocation.RoomDatabase.DataBase;
import com.lq.myfavoritelocation.RoomDatabase.FavoriLocation;
import com.lq.myfavoritelocation.RoomDatabase.LocationDAO;
import com.lq.myfavoritelocation.databinding.ActivityMapsBinding;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {
    ActivityResultLauncher<String> permissionLauncher;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    LocationManager manager;
    Location location;
    LatLng lastLatLng;
    DataBase db;
    LocationDAO dao;
    CompositeDisposable disposables = new CompositeDisposable();
    FavoriLocation selectedLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setLayout();
        registerLauncher();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        db =  Room.databaseBuilder(getApplicationContext(), DataBase.class,"FavoriLocation").build();
        dao = db.locationDAO();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
        if(ContextCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION))
            {
                Snackbar.make(binding.getRoot(),"YOU WANT USE MAP YOU SHOULD ALLOW PERMISSION", Snackbar.LENGTH_LONG).setAction("CLICK FOR ALOW PERMISSION ",new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION);
                    }
                }).show(); ;
            }
            else{
                permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }
        else{
            manager =(LocationManager) getSystemService(Context.LOCATION_SERVICE);

           if(getIntent().getStringExtra("type").equals("oldLocation"))
           {
               FavoriLocation selectLocation =(FavoriLocation) getIntent().getSerializableExtra("selectedLocation");
               lastLatLng = new LatLng(selectLocation.latitude,selectLocation.longitude);
               mMap.addMarker(new MarkerOptions().position(lastLatLng).title(selectLocation.locationName));
               mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLatLng,15));
           }
           else
           {
               location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
               if(location != null)
               {
                   LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                   mMap.addMarker(new MarkerOptions().position(latLng).title("My Last Known Location"));
                   mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
               }
           }

            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {

                }
            });
        }
    }

    public void setLayout()
    {
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        if(type.equals("newLocation"))
        {
            binding.deleteButton.setVisibility(View.GONE);
        }
        else{
            selectedLocation =(FavoriLocation)intent.getSerializableExtra("selectedLocation");
            System.out.print("selectedLocation.name => "+selectedLocation.locationName);
            binding.locationNameTextView.setText(selectedLocation.locationName);
            binding.addButton.setVisibility(View.GONE);
            binding.locationNameTextView.setEnabled(false);
        }
    }

    public void add(View view)
    {
        this.disposables.add(dao.insert(new FavoriLocation(binding.locationNameTextView.getText().toString(), lastLatLng.longitude, lastLatLng.latitude)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::onCompleted));
    }

    public void delete(View view)
    {
         this.disposables.add(dao.delete(selectedLocation).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::onCompleted));
    }

    public void onCompleted()
    {
         Intent intent = new Intent(MapsActivity.this,MainActivity.class);
         startActivity(intent);
         finish();
    }

    public void registerLauncher()
    {
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result)
                {
                    if(ContextCompat.checkSelfPermission(MapsActivity.this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
                        manager =(LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if(location != null)
                        {

                            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(latLng).title("My Last Known Location"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

                        }
                        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                            @Override
                            public void onLocationChanged(@NonNull Location location) {

                            }
                        });
                    }
                }
                else{
                    Toast.makeText(MapsActivity.this,"YOU NOT ALLOW MAP PERMISSION", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        lastLatLng = latLng;
    }
}