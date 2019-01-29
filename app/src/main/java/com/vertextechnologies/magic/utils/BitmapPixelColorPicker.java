package com.vertextechnologies.magic.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Size;
import android.view.PointerIcon;


public class BitmapPixelColorPicker {

    private Bitmap bitmap;
    public BitmapPixelColorPicker(Bitmap bitmap){

        this.bitmap = bitmap;
    }

    public int [] getColorFromPixel(int x, int y){

        Point point = transformPoint(x,y);

        int pixel = bitmap.getPixel( point.x,point.y );

        int redValue = Color.red(pixel);

        int blueValue = Color.blue(pixel);

        int greenValue = Color.green(pixel);

        return new int[]{redValue, greenValue, blueValue};

    }

    public Point transformPoint(int x, int y) {

        int ratioX  =  bitmap.getWidth()/viewWidth;

        int ratioY = bitmap.getHeight()/viewHeight;

        return new Point(ratioX * x, ratioY*y);

    }

    private int viewWidth;

    public int getViewWidth() {

        return viewWidth;

    }

    public void setViewWidth(int viewWidth) {

        this.viewWidth = viewWidth;

    }

    private int viewHeight;

    public int getViewHeight() {

        return viewHeight;

    }

    public void setViewHeight(int viewHeight) {

        this.viewHeight = viewHeight;

    }
}
