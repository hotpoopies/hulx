package jp.co.cyberagent.android.gpuimage;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static org.hamcrest.Matchers.equalTo;

@RunWith(RobolectricTestRunner.class)
public class BufferModifierTest {


    private static final float CUBE[] = {
            -1.0f, -1.0f,
            1.0f, -1.0f,
            -1.0f, 1.0f,
            1.0f, 1.0f,
    };

    private FloatBuffer floatBuffer;

    @Before
    public void setUp() throws Exception{

        floatBuffer = ByteBuffer
                .allocate(CUBE.length*4) .order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        floatBuffer.put(CUBE).position(0);


    }

    @Test
    public void shouldScaleFloatBufferIfDifferentHeightInputAndOutput(){

        BufferScaler bufferModifier = getBufferModifier(5, 10,5,5);

        Assert.assertNotEquals(bufferModifier.getInputHeight(),bufferModifier.getOutputHeight());

        FloatBuffer outFloatBuffer =bufferModifier.scaleFloatBuffer(floatBuffer);

        Assert.assertNotEquals(outFloatBuffer.get(0),floatBuffer.get(0));

    }


    @Test
    public void shouldScaleFloatBufferIfDifferentWidthInputAndOutput(){

        BufferScaler bufferModifier = getBufferModifier(5, 5,5,10);

        Assert.assertNotEquals(bufferModifier.getInputWidth(),bufferModifier.getOutputWidth());

        FloatBuffer outFloatBuffer =bufferModifier.scaleFloatBuffer(floatBuffer);

        Assert.assertNotEquals(outFloatBuffer.get(1),floatBuffer.get(1));

    }

    @Test
    public void shouldNotScaleFloatBuffer(){

        BufferScaler bufferModifier = getBufferModifier(5, 5,5,5);

        Assert.assertEquals(bufferModifier.getInputHeight(),bufferModifier.getOutputHeight());
        Assert.assertEquals(bufferModifier.getInputWidth(),bufferModifier.getOutputWidth());

        FloatBuffer outFloatBuffer =bufferModifier.scaleFloatBuffer(floatBuffer);

        Assert.assertEquals(outFloatBuffer.get(0),floatBuffer.get(0),0.0);

    }

    private BufferScaler getBufferModifier(int inputHeight, int outputHeight, int inputWidth , int outputWidth) {

        BufferScaler bufferModifier = new BufferScaler();

        bufferModifier.setInputHeight(inputHeight);
        bufferModifier.setOutputHeight(outputHeight);
        bufferModifier.setInputWidth(inputWidth);
        bufferModifier.setOutputWidth(outputWidth);

        return bufferModifier;

    }

    @Test
    public void shouldScaleFloatBufferWithMaxRatio(){

        BufferScaler bufferModifier = getBufferModifier(2, 4,4,16);

        Assert.assertNotEquals(bufferModifier.getInputHeight(),bufferModifier.getOutputHeight());
        Assert.assertNotEquals(bufferModifier.getInputWidth(),bufferModifier.getOutputWidth());

        FloatBuffer outFloatBuffer =bufferModifier.scaleFloatBuffer(floatBuffer);

        float ratioHeight = outFloatBuffer.get(0)/floatBuffer.get(0);
        float ratioWidth = outFloatBuffer.get(1)/floatBuffer.get(1);

        Assert.assertThat(ratioHeight,equalTo(ratioWidth));

    }

    @Test
    public void shouldNotScaleFloatBufferIfScaleTypeCenterCrop(){

        BufferScaler bufferModifier = getBufferModifier(2, 4,4,16);

        Assert.assertNotEquals(bufferModifier.getInputHeight(),bufferModifier.getOutputHeight());
        Assert.assertNotEquals(bufferModifier.getInputWidth(),bufferModifier.getOutputWidth());

        bufferModifier.setScaleType(GPUImage.ScaleType.CENTER_CROP);

        FloatBuffer outFloatBuffer =bufferModifier.scaleFloatBuffer(floatBuffer);

        Assert.assertThat(outFloatBuffer.get(0),equalTo(floatBuffer.get(0)));
        Assert.assertThat(outFloatBuffer.get(1),equalTo(floatBuffer.get(1)));

    }


}