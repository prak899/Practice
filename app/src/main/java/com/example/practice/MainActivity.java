package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.practice.databinding.ActivityMainBinding;
import com.example.practice.fragments.MultipleImages;
import com.example.practice.fragments.SingleImage;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
    private FragmentManager fragmentManager;
    private MultipleImages multipleImages;
    private SingleImage singleImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);
        init();


        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, multipleImages)
                .commit();

        /*activityMainBinding.next.setOnClickListener(v->{
            switchFragments();
            Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
        });*/

    }

    private void init() {
        fragmentManager = getSupportFragmentManager();
        multipleImages = new MultipleImages();
        singleImage = new SingleImage();

    }

    private void switchFragments() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (fragmentManager.findFragmentByTag("MultipleImages") != null) {
            transaction.setCustomAnimations(
                    R.anim.enter_from_right, R.anim.exit_to_left,
                    R.anim.enter_from_left, R.anim.exit_to_right
            );
            transaction.replace(R.id.fragment_container, multipleImages, "SingleImage");
        } else {
            transaction.setCustomAnimations(
                    R.anim.enter_from_left, R.anim.exit_to_right,
                    R.anim.enter_from_right, R.anim.exit_to_left
            );
            transaction.replace(R.id.fragment_container, singleImage, "MultipleImages");
        }

        transaction.addToBackStack(null); // Add the transaction to the back stack
        transaction.commit();
    }
}