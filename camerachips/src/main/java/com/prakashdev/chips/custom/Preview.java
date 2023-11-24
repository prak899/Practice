package com.prakashdev.chips.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.camera.view.PreviewView;

public class Preview extends FrameLayout {

    private PreviewView previewView;

    public Preview(Context context) {
        super(context);
        init();
    }

    public Preview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Preview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Inflate your custom layout or create the PreviewView programmatically
        previewView = new PreviewView(getContext());
        // Add the PreviewView to the container
        addView(previewView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    public PreviewView getPreviewView() {
        return previewView;
    }
}
