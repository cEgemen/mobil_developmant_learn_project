package com.lq.informationcities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lq.informationcities.databinding.ActivityDetayBinding;

public class DetayActivity extends AppCompatActivity {
    ActivityDetayBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        CityModel model = SelectModel.init().getModel();
        binding.cityImage.setImageResource(model.getImage());
        binding.cityInformationTextView.setText(model.getInfo());
        binding.cityNameTextView.setText(model.getName());
    }
}