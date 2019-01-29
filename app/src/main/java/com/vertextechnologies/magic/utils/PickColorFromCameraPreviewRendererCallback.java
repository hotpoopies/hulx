package com.vertextechnologies.magic.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageRenderer;

public class PickColorFromCameraPreviewRendererCallback implements Camera.PictureCallback {

    public static final int MEDIA_TYPE_IMAGE = 1;

    public static final int MEDIA_TYPE_VIDEO = 2;

    private final GPUImage gpuImage;

    private final Camera camera;

    private GLSurfaceView view;
    private GPUImageRenderer gpuImageRenderer;
    private float x;
    private float y;

    public PickColorFromCameraPreviewRendererCallback(GPUImage gpuImage,
                                                      Camera camera,
                                                      GLSurfaceView view,
                                                      GPUImageRenderer gpuImageRenderer,
                                                      float x, float y){

        this.gpuImage = gpuImage;

        this.camera = camera;

        this.view = view;

        this.gpuImageRenderer = gpuImageRenderer;

        this.x = x;

        this.y = y;

    }

    @Override
    public void onPictureTaken(byte[] data, final Camera camera) {

        final File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);

        if (pictureFile == null) {

            Log.d("ASDF", "Error creating media file, check storage permissions");

            return;

        }

        try {

            FileOutputStream fos = new FileOutputStream(pictureFile);

            fos.write(data);

            fos.close();

        } catch (FileNotFoundException e) {

            Log.d("ASDF", "File not found: " + e.getMessage());

        } catch (IOException e) {

            Log.d("ASDF", "Error accessing file: " + e.getMessage());

        }

        data = null;

        Bitmap bitmap = BitmapFactory.decodeFile(pictureFile.getAbsolutePath());

        BitmapPixelColorPicker bitmapPixelColorPicker = new BitmapPixelColorPicker(bitmap);

        bitmapPixelColorPicker.setViewWidth(gpuImageRenderer.getmOutputWidth());

        bitmapPixelColorPicker.setViewHeight(gpuImageRenderer.getmOutputHeight());

        pictureFile.delete();
        gpuImageRenderer.setSelectedColorFromCameraPreview(bitmapPixelColorPicker.getColorFromPixel((int)x,(int)y));

       /* view.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        gpuImage.saveToPictures(bitmap, "GPUImage",
                System.currentTimeMillis() + ".jpg",
                new GPUImage.OnPictureSavedListener() {

                    @Override
                    public void onPictureSaved(final Uri uri) {

                        pictureFile.delete();

                        camera.startPreview();

                        view.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

                    }

                });*/

    }

    private static File getOutputMediaFile(final int type) {

        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {

            if (!mediaStorageDir.mkdirs()) {

                Log.d("MyCameraApp", "failed to create directory");

                return null;

            }

        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        File mediaFile;

        if (type == MEDIA_TYPE_IMAGE) {

            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");

        } else if (type == MEDIA_TYPE_VIDEO) {

            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");

        } else {

            return null;

        }

        return mediaFile;

    }

}
