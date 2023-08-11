package com.example.practice;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.practice.databinding.ActivityFragmentContainerBinding;
import com.example.practice.fragments.SingleImage;

public class FragmentContainer extends AppCompatActivity {

    private SingleImage singleImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFragmentContainerBinding fragmentContainerBinding = ActivityFragmentContainerBinding.inflate(getLayoutInflater());
        View view = fragmentContainerBinding.getRoot();
        setContentView(view);
        singleImage = new SingleImage();
        activateFragmentContainer();
    }

    private void activateFragmentContainer() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, singleImage)
                .commit();
    }
}