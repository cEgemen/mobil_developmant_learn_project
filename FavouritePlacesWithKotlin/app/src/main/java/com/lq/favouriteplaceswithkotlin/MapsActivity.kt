package com.lq.favouriteplaceswithkotlin

import android.Manifest
import android.app.LocaleManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.core.view.isVisible
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.room.Room
import androidx.room.Room.databaseBuilder

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.lq.favouriteplaceswithkotlin.database.Place
import com.lq.favouriteplaceswithkotlin.database.PlaceDao
import com.lq.favouriteplaceswithkotlin.database.RoomDB
import com.lq.favouriteplaceswithkotlin.databinding.ActivityMapsBinding
import com.lq.favouriteplaceswithkotlin.singlation.SelectPlace
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.selects.select
import java.security.Provider

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,GoogleMap.OnMapLongClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var  permissionLaunch : ActivityResultLauncher<String>;
    private lateinit var manager : LocationManager;
    private lateinit var  listener : LocationListener;
    private  var  lastLoc : Location? = null;
    private lateinit var db : RoomDB;
    private lateinit var dao: PlaceDao;
    var markedLt:Double = 0.0 ;
    var markedLd : Double= 0.0 ;
    private val composite : CompositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerLauncher()
        db = Room.databaseBuilder(
            applicationContext,
            RoomDB::class.java, "Places"
        ).build()
        dao = db.placeDao();
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapLongClickListener(this)
        val type = intent.getStringExtra("type");
        if(type == "new")
        {
             binding.deleteButton.visibility = View.GONE;
             binding.saveButton.setOnClickListener {


                 composite.add(
                     dao.insertPlace(Place(binding.placeNameEditTextView.text.toString(),markedLt,markedLd))
                         .subscribeOn(Schedulers.io())
                         .observeOn(AndroidSchedulers.mainThread())
                         .subscribe(this::handlerResponse)
                 )
             }
            manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager;
            listener = object:LocationListener{
                override fun onLocationChanged(p0: Location) {

                }

            }
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
                {

                    Snackbar.make(binding.placeNameEditTextView,"YOU WANT USE MAP ,YOU SHOULD ALLOW PERMISSION",Snackbar.LENGTH_INDEFINITE)
                        .setAction("CLICK FOR USE MAP"){
                            permissionLaunch.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                        }
                }
                else{
                    permissionLaunch.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                }
            }
            else{
                lastLoc = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(lastLoc != null)
                {
                    val ltld = LatLng(lastLoc!!.latitude,lastLoc!!.longitude);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ltld,15f))
                    mMap.addMarker(MarkerOptions().position(ltld).title("LAST LOCATION"))
                    mMap.isMyLocationEnabled =true;
                }
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,10f,listener);
            }
        }
        else{
            binding.saveButton.visibility = View.GONE;
            binding.deleteButton.setOnClickListener {
                   composite.add(
                        dao.deletePlace(SelectPlace.selectPlace!!)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::handlerResponse)
                   )
            }
            binding.placeNameEditTextView.isEnabled = false;
            val selectPlace = SelectPlace.selectPlace;
             binding.placeNameEditTextView.hint=selectPlace!!.placeName;
             val ltld = LatLng(selectPlace.placeLat,selectPlace.placeLot);
             mMap.addMarker(MarkerOptions().position(ltld).title(selectPlace.placeName))
             mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ltld,15f));
        }

    }

    override fun onMapLongClick(p0: LatLng) {
        mMap.clear();
        mMap.addMarker(MarkerOptions().position(p0))
        markedLt = p0.latitude;
        markedLd = p0.longitude;
    }

    fun registerLauncher(){
         permissionLaunch=registerForActivityResult(ActivityResultContracts.RequestPermission(),
             ActivityResultCallback { result ->
                   if(result)
                   {
                       if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                       {
                           lastLoc = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                           if(lastLoc != null)
                           {
                               val ltld = LatLng(lastLoc!!.latitude,lastLoc!!.longitude);
                               mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ltld,15f))
                               mMap.addMarker(MarkerOptions().position(ltld).title("LAST LOCATION"))
                               mMap.isMyLocationEnabled =true;
                           }
                           manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,10f,listener);
                       }
                   }
                 else{
                     Toast.makeText(this,"YOU NOT ALLOW PERMISSION",Toast.LENGTH_LONG)
                   }
             })

    }

    fun handlerResponse()
    {
         val intent = Intent(this,MainActivity::class.java);
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         startActivity(intent);
    }

    override fun onDestroy() {
        super.onDestroy()
        composite.clear()
    }
}