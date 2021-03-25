package com.example.biraj.daysapp;

import android.graphics.Bitmap;
import android.view.View;

public class Screenshot {

    public static Bitmap takeScreenShot(View view){
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }
    public static Bitmap takeScreenShotOfRootView(View view){
        return takeScreenShot(view.getRootView());
    }
}
