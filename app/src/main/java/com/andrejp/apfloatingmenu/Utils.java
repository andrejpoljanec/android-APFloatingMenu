package com.andrejp.apfloatingmenu;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Collection;

/**
 * Created by Andrej Poljanec on 9/21/2016.
 */
public class Utils {

    public static float dpToPx(Context context, int d) {
        Resources r = context.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, d, r.getDisplayMetrics());
    }

    public static ViewGroup.LayoutParams getMPHorizontalWCVerticalParams() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public static ViewGroup.LayoutParams getWCParams() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public static ViewGroup.LayoutParams getExactParams(Context context, int w, int h) {
        return new ViewGroup.LayoutParams((int) dpToPx(context, w), (int) dpToPx(context, h));
    }

    public static LinearLayout.LayoutParams getExactLinearParams(Context context, int w, int h) {
        return new LinearLayout.LayoutParams((int) dpToPx(context, w), (int) dpToPx(context, h));
    }

    public static boolean isNotEmpty(Collection collection) {
        return collection != null && collection.size() > 0;
    }

}
