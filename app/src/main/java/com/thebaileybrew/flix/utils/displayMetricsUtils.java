package com.thebaileybrew.flix.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class displayMetricsUtils {

    public static int calculateGridColumn(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int columnCount = (int) (dpWidth / scalingFactor);
        if (columnCount < 2) {
            columnCount = 2;
        }
        return columnCount;
    }
}
