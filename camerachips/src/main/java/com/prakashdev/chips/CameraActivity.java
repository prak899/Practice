package com.prakashdev.chips;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;
import com.prakashdev.chips.custom.Preview;
import com.prakashdev.chips.databinding.ActivityCameraBinding;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CameraActivity extends AppCompatActivity {
    public static String TAG = "CameraActivity";
    private ActivityCameraBinding binding;
    private ExecutorService cameraExecutor;
    private ImageCapture imageCapture;
    private File outputDirectory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCameraBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


//        initView();
    }

    public void initView(Context context, int resourceId) {
        cameraExecutor = Executors.newSingleThreadExecutor();
        outputDirectory = getOutputDirectory(context);

        Preview customPreviewContainer = findViewById(resourceId);
        PreviewView customPreview = customPreviewContainer.getPreviewView();
        // Now you can use 'previewView' in your code

        startCamera(customPreview, context);
        binding.btnCapture.setOnClickListener(view -> {
//                takePhoto(this)
            Log.d(TAG, "initView: start");
        });
    }

    public void startCamera(PreviewView previewView, Context context) {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider
                .getInstance(context);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider, previewView);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(context));
    }

    public void bindPreview(ProcessCameraProvider cameraProvider, PreviewView previewView) {
        androidx.camera.core.Preview preview = new androidx.camera.core.Preview.Builder().build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();

//        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, imageCapture);
    }

    public String takePhoto(Context context) {
        File photoFile = new File(outputDirectory, new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(System.currentTimeMillis()) + ".jpg");

        ImageCapture.OutputFileOptions outputFileOptions =
                new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        imageCapture.takePicture(outputFileOptions, cameraExecutor,
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        runOnUiThread(() -> {
                           /* try {
                                Intent intent = new Intent(CameraActivity.this, Class.forName("com.example.practice.activity.CameraPreview"));
                                intent.putExtra("imagePath", photoFile.getAbsolutePath());
                                startActivity(intent);
                            } catch (ClassNotFoundException e) {
                                Toast.makeText(CameraActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }*/
                            Toast.makeText(context, photoFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                        });
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        exception.printStackTrace();
                    }
                });
        return photoFile.getAbsolutePath();
    }

    private File getOutputDirectory(Context context) {
        File mediaDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File directory = new File(mediaDir, "NarwaMissionProject");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }
}