package jp.co.cyberagent.android.gpuimage;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;


@RunWith(RobolectricTestRunner.class)
public class BufferFlipperTest {

    @Test
    public void shouldGenerateBufferCoordinates(){

        arrayFloatFlipper bufferFlipper = new arrayFloatFlipper();
        bufferFlipper.setRotation(Rotation.NORMAL);
        bufferFlipper.setRatioHeightMax(2);
        bufferFlipper.setRatioWidthMax(2);

        Assert.assertEquals(bufferFlipper.getBufferCoordinates().length,8);

    }

    @Test
    public void shouldGenerateBufferCoordinatesForNoRotationNormal(){

        arrayFloatFlipper bufferFlipper = new arrayFloatFlipper();
        bufferFlipper.setRotation(Rotation.NORMAL);
        bufferFlipper.setRatioHeightMax(2);
        bufferFlipper.setRatioWidthMax(2);
        bufferFlipper.setFlipHorizontal(false);
        bufferFlipper.setFlipVertical(false);

        float[] floatArrayTest =bufferFlipper.getBufferCoordinates();

        Assert.assertEquals(floatArrayTest[0],0,0);
        Assert.assertEquals(floatArrayTest[1],1,0);
        Assert.assertEquals(floatArrayTest[2],1,0);
        Assert.assertEquals(floatArrayTest[3],1,0);
        Assert.assertEquals(floatArrayTest[4],0,0);
        Assert.assertEquals(floatArrayTest[5],0,0);
        Assert.assertEquals(floatArrayTest[6],1,0);
        Assert.assertEquals(floatArrayTest[7],0,0);

    }

    @Test
    public void shouldGenerateBufferCoordinatesForNoRotation90(){

        arrayFloatFlipper bufferFlipper = new arrayFloatFlipper();
        bufferFlipper.setRotation(Rotation.ROTATION_90);
        bufferFlipper.setRatioHeightMax(2);
        bufferFlipper.setRatioWidthMax(2);


        float[] floatArrayTest =bufferFlipper.getBufferCoordinates();

        Assert.assertEquals(floatArrayTest[0],1,0);
        Assert.assertEquals(floatArrayTest[1],1,0);
        Assert.assertEquals(floatArrayTest[2],1,0);
        Assert.assertEquals(floatArrayTest[3],0,0);
        Assert.assertEquals(floatArrayTest[4],0,0);
        Assert.assertEquals(floatArrayTest[5],1,0);
        Assert.assertEquals(floatArrayTest[6],0,0);
        Assert.assertEquals(floatArrayTest[7],0,0);

    }


    @Test
    public void shouldGenerateBufferCoordinatesForNoRotation180(){

        arrayFloatFlipper bufferFlipper = new arrayFloatFlipper();
        bufferFlipper.setRotation(Rotation.ROTATION_180);
        bufferFlipper.setRatioHeightMax(2);
        bufferFlipper.setRatioWidthMax(2);

        float[] floatArrayTest =bufferFlipper.getBufferCoordinates();

        Assert.assertEquals(floatArrayTest[0],1,0);
        Assert.assertEquals(floatArrayTest[1],0,0);
        Assert.assertEquals(floatArrayTest[2],0,0);
        Assert.assertEquals(floatArrayTest[3],0,0);
        Assert.assertEquals(floatArrayTest[4],1,0);
        Assert.assertEquals(floatArrayTest[5],1,0);
        Assert.assertEquals(floatArrayTest[6],0,0);
        Assert.assertEquals(floatArrayTest[7],1,0);

    }


    @Test
    public void shouldGenerateBufferCoordinatesForNoRotation270(){

        arrayFloatFlipper bufferFlipper = new arrayFloatFlipper();
        bufferFlipper.setRotation(Rotation.ROTATION_270);
        bufferFlipper.setRatioHeightMax(2);
        bufferFlipper.setRatioWidthMax(2);

        float[] floatArrayTest =bufferFlipper.getBufferCoordinates();

        Assert.assertEquals(floatArrayTest[0],0,0);
        Assert.assertEquals(floatArrayTest[1],0,0);
        Assert.assertEquals(floatArrayTest[2],0,0);
        Assert.assertEquals(floatArrayTest[3],1,0);
        Assert.assertEquals(floatArrayTest[4],1,0);
        Assert.assertEquals(floatArrayTest[5],0,0);
        Assert.assertEquals(floatArrayTest[6],1,0);
        Assert.assertEquals(floatArrayTest[7],1,0);

    }

    @Test
    public void shouldGenerateCoordinatesForScaleTypeNonCrop(){


        arrayFloatFlipper bufferFlipper = new arrayFloatFlipper();
        bufferFlipper.setRotation(Rotation.NORMAL);
        bufferFlipper.setScaleType(GPUImage.ScaleType.CENTER_INSIDE);
        bufferFlipper.setRatioHeightMax(2);
        bufferFlipper.setRatioWidthMax(2);
        float[] floatArrayTest =bufferFlipper.getBufferCoordinates();

        Assert.assertEquals(floatArrayTest[0],0,0);
        Assert.assertEquals(floatArrayTest[1],1,0);
        Assert.assertEquals(floatArrayTest[2],1,0);
        Assert.assertEquals(floatArrayTest[3],1,0);
        Assert.assertEquals(floatArrayTest[4],0,0);
        Assert.assertEquals(floatArrayTest[5],0,0);
        Assert.assertEquals(floatArrayTest[6],1,0);
        Assert.assertEquals(floatArrayTest[7],0,0);

    }


    @Test
    public void shouldGenerateCoordinatesForScaleTypeCrop(){


        arrayFloatFlipper bufferFlipper = new arrayFloatFlipper();
        bufferFlipper.setRotation(Rotation.NORMAL);
        bufferFlipper.setScaleType(GPUImage.ScaleType.CENTER_CROP);
        bufferFlipper.setRatioHeightMax(2);
        bufferFlipper.setRatioWidthMax(2);


        float distHorizontal = (float) ((1 - 1 / 2.0) / 2);
        float distVertical = (float) ((1 - 1 / 2.0) / 2);


        float[] floatArrayTest =bufferFlipper.getBufferCoordinates();

        Assert.assertEquals(floatArrayTest[0],distHorizontal,0);
        Assert.assertEquals(floatArrayTest[1],1-distVertical,0);
        Assert.assertEquals(floatArrayTest[2],1-distHorizontal,0);
        Assert.assertEquals(floatArrayTest[3],1-distVertical,0);
        Assert.assertEquals(floatArrayTest[4],distHorizontal,0);
        Assert.assertEquals(floatArrayTest[5],distVertical,0);
        Assert.assertEquals(floatArrayTest[6],1-distHorizontal,0);
        Assert.assertEquals(floatArrayTest[7],distVertical,0);

    }



}