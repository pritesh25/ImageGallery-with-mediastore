package com.selfie.life.imagegallery.API;

import android.content.Context;
import android.util.DisplayMetrics;

public class UtilityRecyclerViewColumn
{

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 80);
        return noOfColumns;
    }
}
