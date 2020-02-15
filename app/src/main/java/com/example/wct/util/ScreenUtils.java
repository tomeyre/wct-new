package com.example.wct.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by thomaseyre on 04/01/2018.
 */

public class ScreenUtils {

    public static int getScreenHeight(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int height = metrics.heightPixels;

        return height;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static float getMeasuredHeight(ViewGroup view, float titleHeight, float adHeight){
        float measuredHeight = 0;

        for (int i = 0; i < view.getChildCount(); i++) {
            if(view.getVisibility() == View.VISIBLE) {
                measuredHeight += view.getChildAt(i).getHeight();// + convertDpToPixel(10, view.getContext());
            }
        }

        return measuredHeight + titleHeight + adHeight;
    }

    public static float getMeasuredHeight(ViewGroup view){
        float measuredHeight = 0;

        for (int i = 0; i < view.getChildCount(); i++) {
            if(view.getVisibility() == View.VISIBLE) {
                measuredHeight += view.getChildAt(i).getHeight() + convertDpToPixel(10, view.getContext());
            }
        }

        return measuredHeight;
    }

    public static int getScreenWidth(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;

        return width;
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

//    public Integer getStatusBarHeight(Activity activity){
//        Rect rectangle = new Rect();
//        Window window = activity.getWindow();
//        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
//        int statusBarHeight = rectangle.top;
//        int contentViewTop =
//                window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
//        int titleBarHeight= contentViewTop - statusBarHeight;
//
//        return titleBarHeight;
//    }
}
