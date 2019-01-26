package jp.co.cyberagent.android.gpuimage;

import android.media.Image;
import android.support.annotation.NonNull;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;


@RunWith(RobolectricTestRunner.class)
public class ImageBufferScalerTest {

    private static final float CUBE[] = {
            -1.0f, -1.0f,
            1.0f, -1.0f,
            -1.0f, 1.0f,
            1.0f, 1.0f,
    };

    @Test
    public void shouldScaleCordinatesBuffer() {

        ImageBufferScaler imageBufferScaler = getImageBufferScaler(Rotation.NORMAL);

        ArrayFloatScaler arrayFloatScaller = new ArrayFloatScaler();
        arrayFloatScaller.setInputHeight(2);
        arrayFloatScaller.setInputWidth(4);
        arrayFloatScaller.setOutputHeight(4);
        arrayFloatScaller.setOutputWidth(16);
        arrayFloatScaller.setScaleType(GPUImage.ScaleType.CENTER_INSIDE);
        arrayFloatScaller.setRotation(Rotation.NORMAL);

        Assert.assertEquals(imageBufferScaler.getPointFloatBuffer().get(0), arrayFloatScaller.scaleFloatBuffer(CUBE)[0],0);

        Assert.assertEquals(imageBufferScaler.getPointFloatBuffer().get(1), arrayFloatScaller.scaleFloatBuffer(CUBE)[1],0);

    }

    @NonNull
    private ImageBufferScaler getImageBufferScaler(Rotation rotation) {
        ImageBufferScaler imageBufferScaler = new ImageBufferScaler(CUBE);

        imageBufferScaler.setInputHeight(2);
        imageBufferScaler.setInputWidth(4);
        imageBufferScaler.setOutputHeight(4);
        imageBufferScaler.setOutputWidth(16);
        imageBufferScaler.setScaleType(GPUImage.ScaleType.CENTER_INSIDE);
        imageBufferScaler.setRotation(rotation);

        imageBufferScaler.ScaleImageBuffer();
        return imageBufferScaler;
    }

    @Test
    public void shouldScaleTextureCoordinatesNormal(){

        getTest(Rotation.NORMAL);


    }

    private void getTest(Rotation normal) {
        ImageBufferScaler imageBufferScaler = getImageBufferScaler(normal);

        ArrayFloatScaler arrayFloatScaller = new ArrayFloatScaler();
        arrayFloatScaller.setInputHeight(imageBufferScaler.getInputHeight());
        arrayFloatScaller.setInputWidth(imageBufferScaler.getInputWidth());
        arrayFloatScaller.setOutputHeight(imageBufferScaler.getOutputHeight());
        arrayFloatScaller.setOutputWidth(imageBufferScaler.getOutputWidth());
        arrayFloatScaller.setScaleType(imageBufferScaler.getScaleType());
        arrayFloatScaller.setRotation(imageBufferScaler.getRotation());

        arrayFloatFlipper arrayFloatFlipper = new arrayFloatFlipper();
        arrayFloatFlipper.setRotation(arrayFloatScaller.getRotation());
        arrayFloatFlipper.setRatioHeightMax(arrayFloatScaller.getRatioHeightMax());
        arrayFloatFlipper.setRatioWidthMax(arrayFloatScaller.getRatioWidthMax());

        float[] floatArrayTest = arrayFloatFlipper.getBufferCoordinates();


        Assert.assertEquals(floatArrayTest[0], imageBufferScaler.getTextureFloatBuffer().get(0), 0);
        Assert.assertEquals(floatArrayTest[1], imageBufferScaler.getTextureFloatBuffer().get(1), 0);
        Assert.assertEquals(floatArrayTest[2], imageBufferScaler.getTextureFloatBuffer().get(2), 0);
        Assert.assertEquals(floatArrayTest[3], imageBufferScaler.getTextureFloatBuffer().get(3), 0);
        Assert.assertEquals(floatArrayTest[4], imageBufferScaler.getTextureFloatBuffer().get(4), 0);
        Assert.assertEquals(floatArrayTest[5], imageBufferScaler.getTextureFloatBuffer().get(5), 0);
        Assert.assertEquals(floatArrayTest[6], imageBufferScaler.getTextureFloatBuffer().get(6), 0);
        Assert.assertEquals(floatArrayTest[7], imageBufferScaler.getTextureFloatBuffer().get(7), 0);
    }


    @Test
    public void shouldScaleTextureCoordinates90(){

        getTest(Rotation.ROTATION_90);


    }

}