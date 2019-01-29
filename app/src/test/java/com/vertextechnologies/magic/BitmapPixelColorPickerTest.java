package com.vertextechnologies.magic;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.util.Size;

import com.vertextechnologies.magic.utils.BitmapPixelColorPicker;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@RunWith(RobolectricTestRunner.class)
public class BitmapPixelColorPickerTest {

    private Bitmap bitmap = Bitmap.createBitmap(6000,4000, Bitmap.Config.ARGB_8888);

    private int viewWidth = 3000;
    private int viewHeight = 2000;

    private  BitmapPixelColorPicker bitmapPixelColorPicker;


    public BitmapPixelColorPickerTest(){

        bitmap = createTestBitmap(6000,4000,Color.GREEN);
        bitmapPixelColorPicker = new BitmapPixelColorPicker(bitmap);
        bitmapPixelColorPicker.setViewHeight(viewHeight);
        bitmapPixelColorPicker.setViewWidth(viewWidth);

    }

    public Bitmap createTestBitmap(int width, int height,int color){

        Bitmap bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);

        bitmap.eraseColor(color);

        return bitmap;

    }

    @Test
    public void shouldTransformPointFromScreenToBitmapRealPoint() {

        Point point = new Point(300, 200);

        Point transformPoint = bitmapPixelColorPicker.transformPoint(300, 200);

        int ratioY = 4000 / viewHeight;

        int ratioX = 6000/ viewWidth;

        Assert.assertEquals(transformPoint.x, ratioX * point.x, 0);

        Assert.assertEquals(transformPoint.y, ratioY * point.y, 0);

    }

    @Test
    public void shouldReturnGreenRGBIntegerArray(){

        bitmap = createTestBitmap(6000,4000,Color.GREEN);

        BitmapPixelColorPicker bitmapPixelColorPicker = new BitmapPixelColorPicker(bitmap);

        bitmapPixelColorPicker = new BitmapPixelColorPicker(bitmap);
        bitmapPixelColorPicker.setViewHeight(viewHeight);
        bitmapPixelColorPicker.setViewWidth(viewWidth);

        Point point = new Point(200, 300);

        int[] rgb  = new int[3];

        rgb = bitmapPixelColorPicker.getColorFromPixel(point.x,point.y);

        Assert.assertEquals(rgb[0],0,0);
        Assert.assertEquals(rgb[1],255,0);
        Assert.assertEquals(rgb[2],0,0);

    }


    @Test
    public void shouldReturnREDRGBIntegerArray(){

        bitmap = createTestBitmap(6000,4000,Color.RED);

        BitmapPixelColorPicker bitmapPixelColorPicker = new BitmapPixelColorPicker(bitmap);

        bitmapPixelColorPicker = new BitmapPixelColorPicker(bitmap);
        bitmapPixelColorPicker.setViewHeight(viewHeight);
        bitmapPixelColorPicker.setViewWidth(viewWidth);

        Point point = new Point(200, 300);

        int[] rgb  = new int[3];

        rgb = bitmapPixelColorPicker.getColorFromPixel(point.x,point.y);

        Assert.assertEquals(rgb[0],255,0);
        Assert.assertEquals(rgb[1],0,0);
        Assert.assertEquals(rgb[2],0,0);

    }




}
