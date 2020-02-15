package com.example.wct.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.example.wct.R;

public class BitmapGenerator {

    public Bitmap getMarkerBitmapFromView(int mapColour, View v, Context context) {
        CardView cardView = v.findViewById(R.id.custom_marker_view);
        CardView innerCardView = v.findViewById(R.id.innerCV);
        TextView textView = v.findViewById(R.id.txtCrimeCount);
        textView.setText(Integer.toString(mapColour));

        if (mapColour <= 4) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color1));
        } else if (mapColour > 4 && mapColour <= 8) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color2));
        } else if (mapColour > 8 && mapColour <= 12) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color3));
        } else if (mapColour > 12 && mapColour <= 16) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color4));
        } else if (mapColour > 16 && mapColour <= 20) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color5));
        } else if (mapColour > 20 && mapColour <= 24) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color6));
        } else if (mapColour > 24 && mapColour <= 28) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color7));
        } else if (mapColour > 28 && mapColour <= 32) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color8));
        } else if (mapColour > 32 && mapColour <= 36) {
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color9));
        } else {
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color10));
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        v.setLayoutParams(new ActionMenuView.LayoutParams(ActionMenuView.LayoutParams.WRAP_CONTENT, ActionMenuView.LayoutParams.WRAP_CONTENT));
        v.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        v.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        v.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);

        return bitmap;
    }
}