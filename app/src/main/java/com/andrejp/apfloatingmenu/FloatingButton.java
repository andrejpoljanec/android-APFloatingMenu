package com.andrejp.apfloatingmenu;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Andrej Poljanec on 9/16/2016.
 */
public class FloatingButton extends LinearLayout {

    private static final int FAB_SMALL_SIZE = 32;

    private FloatingItem floatingItem;

    private TextView titleText;
    private ImageView floatingButton;

    private Animation showAnimation;
    private Animation showTextAnimation;
    private Animation hideAnimation;
    private Animation hideTextAnimation;

    public FloatingButton(Context context) {
        super(context);
        init(context);
    }

    public FloatingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FloatingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public FloatingButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {

        setOrientation(HORIZONTAL);
        setGravity(Gravity.END | Gravity.CENTER_VERTICAL);

        floatingButton = new ImageView(context);
        floatingButton.setBackgroundResource(R.drawable.fab_drawable);
        floatingButton.setImageResource(R.drawable.ic_add_white);
        floatingButton.setScaleType(ImageView.ScaleType.CENTER);
        float elevation = Utils.dpToPx(context, FloatingMenu.FAB_ELEVATION);
        floatingButton.setElevation(elevation);
        LinearLayout.LayoutParams layoutParams = Utils.getExactLinearParams(context, FAB_SMALL_SIZE, FAB_SMALL_SIZE);
        int horizontalMargin = (int) Utils.dpToPx(context, (FloatingMenu.FAB_SIZE - FAB_SMALL_SIZE) / 2);
        layoutParams.setMargins(horizontalMargin, 0, horizontalMargin, 0);
        addView(floatingButton, layoutParams);

        titleText = new TextView(context);
        titleText.setBackgroundResource(R.drawable.background_rounded_primary);
        titleText.setTextColor(Color.WHITE);
        titleText.setElevation(elevation);
        int horizontalPadding = (int) Utils.dpToPx(context, (int) getResources().getDimension(R.dimen.padding_very_tiny));
        int verticalPadding = (int) Utils.dpToPx(context, (int) getResources().getDimension(R.dimen.padding_one));
        titleText.setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);
        addView(titleText, 0, Utils.getWCParams());
        titleText.setVisibility(GONE);

        loadAnimations(context);
    }

    private void loadAnimations(Context context) {
        showAnimation = AnimationUtils.loadAnimation(context, R.anim.scale_up);
        showTextAnimation = AnimationUtils.loadAnimation(context, R.anim.scale_up_from_right);
        hideTextAnimation = AnimationUtils.loadAnimation(context, R.anim.scale_down_to_right);
        hideAnimation = AnimationUtils.loadAnimation(context, R.anim.scale_down);
        hideAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void setItem(final FloatingItem floatingItem) {
        this.floatingItem = floatingItem;
        if (floatingItem.getTitleRes() != 0) {
            titleText.setVisibility(VISIBLE);
            titleText.setText(floatingItem.getTitleRes());
        }
        if (floatingItem.getIconRes() != 0) {
            floatingButton.setImageResource(floatingItem.getIconRes());
        }
        if (Utils.isNotEmpty(floatingItem.getListeners())) {
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (FloatingListener listener : floatingItem.getListeners()) {
                        listener.onFloatingButttonClicked();
                    }
                }
            });
        }
    }

    public void show() {
        setVisibility(VISIBLE);
        floatingButton.startAnimation(showAnimation);
        titleText.startAnimation(showTextAnimation);
    }

    public void hide() {
        floatingButton.startAnimation(hideAnimation);
        titleText.startAnimation(hideTextAnimation);
    }
}
