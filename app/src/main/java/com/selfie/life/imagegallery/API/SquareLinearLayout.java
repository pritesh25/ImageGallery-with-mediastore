package com.selfie.life.imagegallery.API;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * Created by Pritesh on 3/30/2018.
 */

public class SquareLinearLayout extends LinearLayout
{

    public SquareLinearLayout(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
