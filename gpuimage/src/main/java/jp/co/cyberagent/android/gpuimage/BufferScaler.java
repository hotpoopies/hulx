package jp.co.cyberagent.android.gpuimage;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import jp.co.cyberagent.android.gpuimage.util.TextureRotationUtil;

public class BufferScaler {

    public FloatBuffer scaleFloatBuffer(FloatBuffer floatBuffer) {

        FloatBuffer scaleFloatBuffer = ByteBuffer
                .allocate(floatBuffer.limit()*Constants.BYTES_PER_FLOAT) .order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        
        float ratioWidth  = outputWidth/inputWidth;
        float ratioHeight = outputHeight/inputHeight;

        float ratioMax = Math.max(ratioWidth, ratioHeight);

        float [] scaleFloatBufferArray = new float[floatBuffer.limit()];

        if(scaleType != GPUImage.ScaleType.CENTER_CROP) {

            scaleFloatBufferArray = new float[]{
                floatBuffer.get(0) / ratioMax, floatBuffer.get(1) / ratioMax,
                        floatBuffer.get(2) / ratioMax, floatBuffer.get(3) / ratioMax,
                        floatBuffer.get(4) / ratioMax, floatBuffer.get(5) / ratioMax,
                        floatBuffer.get(6) / ratioMax, floatBuffer.get(7) / ratioMax,
            };

        }
        else{

            scaleFloatBufferArray= new float[]{
                    floatBuffer.get(0) , floatBuffer.get(1) ,
                    floatBuffer.get(2) , floatBuffer.get(3) ,
                    floatBuffer.get(4) , floatBuffer.get(5) ,
                    floatBuffer.get(6) , floatBuffer.get(7) ,
            };

        }


        scaleFloatBuffer.put(scaleFloatBufferArray);


        return scaleFloatBuffer;

    }

    private int inputHeight;

    public void setInputHeight(int height) {

        this.inputHeight = height;
    }

    public int getInputHeight() {

        return inputHeight;
    }

    private int outputHeight;

    public void setOutputHeight(int height) {

        this.outputHeight = height;
    }

    public int getOutputHeight() {

        return outputHeight;

    }

    private int inputWidth;

    public void setInputWidth(int width) {

        inputWidth = width;
    }

    public int getInputWidth() {
        return inputWidth;
    }

    private int outputWidth;

    public void setOutputWidth(int width) {

        outputWidth = width;
    }

    public int getOutputWidth() {
        return outputWidth;
    }


    private GPUImage.ScaleType scaleType;

    public void setScaleType(GPUImage.ScaleType scaleType) {

        this.scaleType = scaleType;

    }

    public GPUImage.ScaleType getScaleType() {

        return scaleType;

    }
}
