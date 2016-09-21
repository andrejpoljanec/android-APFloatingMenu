package com.andrejp.apfloatingmenu;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrej Poljanec on 9/16/2016.
 */
public class FloatingMenu extends ViewGroup implements FloatingListener {

    public static final int FAB_ELEVATION = 12;
    public static final int FAB_SIZE = 56;
    public static final int FAB_MARGIN = 16;

    private ImageView menuButton;

    private int menuButtonMargin = 0;

    private List<FloatingButton> floatingButtonList;

    private boolean expanded = false;

    private ObjectAnimator rotateClockwiseAnimator;
    private ObjectAnimator rotateAnticlockwiseAnimator;

    public FloatingMenu(Context context) {
        super(context);
        init(context);
    }

    public FloatingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FloatingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public FloatingMenu(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {

        floatingButtonList = new ArrayList<>();

        menuButton = new ImageView(context);
        menuButton.setBackgroundResource(R.drawable.fab_drawable);
        menuButton.setImageResource(R.drawable.ic_add_white);
        menuButton.setScaleType(ImageView.ScaleType.CENTER);
        menuButton.setElevation(Utils.dpToPx(context, FAB_ELEVATION));
        menuButtonMargin = (int) Utils.dpToPx(context, FAB_MARGIN);
        addView(menuButton, Utils.getExactParams(context, FAB_SIZE, FAB_SIZE));
        menuButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        setClipChildren(false);

        loadAnimations();

    }

    private void loadAnimations() {
        rotateClockwiseAnimator = ObjectAnimator.ofFloat(menuButton, "rotation", 0, 135);
        rotateClockwiseAnimator.setInterpolator(new OvershootInterpolator());
        rotateClockwiseAnimator.setDuration(200);
        rotateAnticlockwiseAnimator = ObjectAnimator.ofFloat(menuButton, "rotation", 135, 0);
        rotateAnticlockwiseAnimator.setInterpolator(new AnticipateInterpolator());
        rotateAnticlockwiseAnimator.setDuration(200);
    }

    public void addItem(int iconRes, int titleRes, FloatingListener listener) {
        List<FloatingListener> listeners = new ArrayList<>();
        listeners.add(this);
        if (listener != null) {
            listeners.add(listener);
        }
        FloatingItem floatingItem = new FloatingItem(iconRes, titleRes, listeners);
        FloatingButton floatingButton = new FloatingButton(getContext());
        floatingButton.setItem(floatingItem);
        floatingButton.setVisibility(GONE);
        addView(floatingButton);

        floatingButtonList.add(floatingButton);

        requestLayout();
    }

    private void toggle() {
        if (!expanded) {
            rotateClockwiseAnimator.start();
            int delay = 0;
            int delayInc = 40;
            for (final FloatingButton floatingButton : floatingButtonList) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        floatingButton.show();
                    }
                }, delay);
                delay += delayInc;
            }
        } else {
            rotateAnticlockwiseAnimator.start();
            for (FloatingButton floatingButton : floatingButtonList) {
                floatingButton.hide();
            }
        }

        expanded = !expanded;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int menuButtonWidth = menuButton.getMeasuredWidth();
        int menuButtonHeight = menuButton.getMeasuredHeight();
        int left = r - menuButtonWidth - menuButtonMargin;
        int top = b - menuButtonHeight - menuButtonMargin;
        menuButton.layout(left, top, left + menuButtonWidth, top + menuButtonHeight);

        for (FloatingButton floatingButton : floatingButtonList) {
            int floatingButtonHeight = floatingButton.getMeasuredHeight();
            int floatingButtonWidth = floatingButton.getMeasuredWidth();
            int floatingButtonLeft = r - menuButtonMargin - floatingButtonWidth;
            top -= floatingButtonHeight + menuButtonMargin;
            floatingButton.layout(floatingButtonLeft, top, floatingButtonLeft + floatingButtonWidth, top + floatingButtonHeight);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    @Override
    public void onFloatingButttonClicked() {
        hide();
    }

    public void hide() {
        if (expanded) {
            toggle();
        }
    }
}
