package com.andrejp.apfloatingmenu;

import java.util.List;

/**
 * Created by Andrej Poljanec on 9/19/2016.
 */
public class FloatingItem {

    private int iconRes;
    private int titleRes;
    private List<FloatingListener> listeners;

    public FloatingItem(int iconRes, int titleRes, List<FloatingListener> listeners) {
        this.iconRes = iconRes;
        this.titleRes = titleRes;
        this.listeners = listeners;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public int getTitleRes() {
        return titleRes;
    }

    public void setTitleRes(int titleRes) {
        this.titleRes = titleRes;
    }

    public List<FloatingListener> getListeners() {
        return listeners;
    }

    public void setListeners(List<FloatingListener> listeners) {
        this.listeners = listeners;
    }
}
