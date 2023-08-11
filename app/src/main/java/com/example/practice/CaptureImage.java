package com.example.practice;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.example.practice.databinding.ActivityCaptureMoveImageBinding;
import com.example.practice.fragments.SingleImage;
import com.example.practice.model.MultipleResorces;
import com.example.practice.viewmodel.MainViewModel;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CaptureImage extends AppCompatActivity {
    public static String TAG = "CaptureImage";
    private MainViewModel viewModel;
    private ActivityCaptureMoveImageBinding binding;
    private ExecutorService cameraExecutor;
    private ImageCapture imageCapture;
    private File outputDirectory;
    private SingleImage singleImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCaptureMoveImageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        initView();
        binding.viewImage.setOnClickListener(v->
                startActivity(new Intent(this, FragmentContainer.class))
        );

//        getLiveData();
    }
    private void initView() {
        cameraExecutor = Executors.newSingleThreadExecutor();
        outputDirectory = getOutputDirectory();
        Log.d(TAG, "initView: "+ getOutputDirectory());

        PreviewView previewView = findViewById(R.id.previewView);

        startCamera(previewView);
        findViewById(R.id.captureButton).setOnClickListener(view -> takePhoto());
    }

    void getLiveData() {
        // Observe LiveData to update the UI
        viewModel.getData().observe(this, data -> {
            if (null != data){
                for (MultipleResorces.Datum datum: data){
                    Log.d(TAG, "onResponse: class data" +
                            "\n" + datum.id +
                            "\n" + datum.first_name +
                            "\n" + datum.last_name +
                            "\n" + datum.email +
                            "\n" + datum.avatar);
                }
            }
        });
        viewModel.fetchUsersDetails();
    }

    private void goLive() {
        viewModel.getData().observe(this, data -> {
            if (data != null) {
                for (MultipleResorces.Datum datum : data) {
                    Log.d(TAG, "onResponse: class data" +
                            "\n" + datum.id +
                            "\n" + datum.first_name +
                            "\n" + datum.last_name +
                            "\n" + datum.email +
                            "\n" + datum.avatar);
                }

            }
        });
        viewModel.fetchUsersDetails();
    }
    private void startCamera(PreviewView previewView) {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider, previewView);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void bindPreview(ProcessCameraProvider cameraProvider, PreviewView previewView) {
        Preview preview = new Preview.Builder().build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();

        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, preview, imageCapture);
    }

    private void takePhoto() {
        File photoFile = new File(outputDirectory, new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(System.currentTimeMillis()) + ".jpg");

        ImageCapture.OutputFileOptions outputFileOptions =
                new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        imageCapture.takePicture(outputFileOptions, cameraExecutor,
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        runOnUiThread(() -> {
                            Toast.makeText(CaptureImage.this, "Photo saved: " + photoFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
                        });
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        exception.printStackTrace();
                    }
                });
    }

    private File getOutputDirectory() {
        File mediaDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File directory = new File(mediaDir, "NarwaMissionProject");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }

}