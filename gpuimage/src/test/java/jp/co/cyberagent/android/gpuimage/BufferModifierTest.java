package jp.co.cyberagent.android.gpuimage;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

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

        
    }

    @Test
    public void shouldScaleFloatBufferIfDifferentHeightInputAndOutput(){

        ArrayFloatScaler bufferModifier = getBufferModifier(5, 10,5,5);

        Assert.assertNotEquals(bufferModifier.getInputHeight(),bufferModifier.getOutputHeight());

        float[] outFloatBuffer =bufferModifier.scaleFloatBuffer(CUBE);

        Assert.assertNotEquals(outFloatBuffer[1],CUBE[1]);

    }


    @Test
    public void shouldScaleFloatBufferIfDifferentWidthInputAndOutput(){

        ArrayFloatScaler bufferModifier = getBufferModifier(5, 5,5,10);

        Assert.assertNotEquals(bufferModifier.getInputWidth(),bufferModifier.getOutputWidth());

        float[] outFloatBuffer =bufferModifier.scaleFloatBuffer(CUBE);

        Assert.assertNotEquals(outFloatBuffer[0],CUBE[0]);

    }

    @Test
    public void shouldNotScaleFloatBuffer(){

        ArrayFloatScaler bufferModifier = getBufferModifier(5, 5,5,5);

        Assert.assertEquals(bufferModifier.getInputHeight(),bufferModifier.getOutputHeight());
        Assert.assertEquals(bufferModifier.getInputWidth(),bufferModifier.getOutputWidth());

        float[] outFloatBuffer =bufferModifier.scaleFloatBuffer(CUBE);

        Assert.assertEquals(outFloatBuffer[0],CUBE[0],0.0);

    }

    private ArrayFloatScaler getBufferModifier(int inputHeight, int outputHeight, int inputWidth , int outputWidth) {

        ArrayFloatScaler bufferModifier = new ArrayFloatScaler();

        bufferModifier.setInputHeight(inputHeight);
        bufferModifier.setOutputHeight(outputHeight);
        bufferModifier.setInputWidth(inputWidth);
        bufferModifier.setOutputWidth(outputWidth);

        return bufferModifier;

    }



    @Test
    public void shouldNotScaleFloatBufferIfScaleTypeCenterCrop(){

        ArrayFloatScaler bufferModifier = getBufferModifier(2, 4,4,8);

        Assert.assertNotEquals(bufferModifier.getInputHeight(),bufferModifier.getOutputHeight());
        Assert.assertNotEquals(bufferModifier.getInputWidth(),bufferModifier.getOutputWidth());

        bufferModifier.setScaleType(GPUImage.ScaleType.CENTER_CROP);

        float[] outFloatBuffer =bufferModifier.scaleFloatBuffer(CUBE);

        Assert.assertThat(outFloatBuffer[0],equalTo(CUBE[0]));
        Assert.assertThat(outFloatBuffer[1],equalTo(CUBE[1]));

    }


}