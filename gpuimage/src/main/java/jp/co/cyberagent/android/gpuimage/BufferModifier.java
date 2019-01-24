package jp.co.cyberagent.android.gpuimage;

import jp.co.cyberagent.android.gpuimage.util.TextureRotationUtil;

public class BufferModifier {

    public static void scaleBuffer(ImageBufferModifyParameters imageBufferModifyParameters) {

        float outputWidth = imageBufferModifyParameters.getOutputWidth();
        float outputHeight = imageBufferModifyParameters.getOutputHeight();

        if (imageBufferModifyParameters.getRotation() == Rotation.ROTATION_270 ||
                imageBufferModifyParameters.getRotation() == Rotation.ROTATION_90) {
            outputWidth = imageBufferModifyParameters.getOutputHeight();
            outputHeight = imageBufferModifyParameters.getOutputWidth();
        }

        float ratio1 = outputWidth / imageBufferModifyParameters.getImageWidth();
        float ratio2 = outputHeight / imageBufferModifyParameters.getImageHeight();
        float ratioMax = Math.max(ratio1, ratio2);

        int imageWidthNew = Math.round(imageBufferModifyParameters.getImageWidth() * ratioMax);
        int imageHeightNew = Math.round(imageBufferModifyParameters.getImageHeight() * ratioMax);

        float ratioWidth = imageWidthNew / outputWidth;
        float ratioHeight = imageHeightNew / outputHeight;

        float[] cube = imageBufferModifyParameters.getCUBE();
        float[] textureCords = TextureRotationUtil.getRotation(imageBufferModifyParameters.getRotation(),
                imageBufferModifyParameters.getFlipHorizontal(), imageBufferModifyParameters.getFlipVertical());

        if (imageBufferModifyParameters.getScaleType() == GPUImage.ScaleType.CENTER_CROP) {

            float distHorizontal = (1 - 1 / ratioWidth) / 2;
            float distVertical = (1 - 1 / ratioHeight) / 2;

            textureCords = new float[]{
                    addDistance(textureCords[0], distHorizontal), addDistance(textureCords[1], distVertical),
                    addDistance(textureCords[2], distHorizontal), addDistance(textureCords[3], distVertical),
                    addDistance(textureCords[4], distHorizontal), addDistance(textureCords[5], distVertical),
                    addDistance(textureCords[6], distHorizontal), addDistance(textureCords[7], distVertical),

            };

        } else {

            cube = new float[]{
                    cube[0] / ratioHeight, cube[1] / ratioWidth,
                    cube[2] / ratioHeight, cube[3] / ratioWidth,
                    cube[4] / ratioHeight, cube[5] / ratioWidth,
                    cube[6] / ratioHeight, cube[7] / ratioWidth,

            };

        }

        imageBufferModifyParameters.getGLCubeBuffer().clear();
        imageBufferModifyParameters.getGLCubeBuffer().put(cube).position(0);
        imageBufferModifyParameters.getGLTextureBuffer().clear();
        imageBufferModifyParameters.getGLTextureBuffer().put(textureCords).position(0);

    }

    private static float addDistance(float coordinate, float distance) {

        return coordinate == 0.0f ? distance : 1 - distance;

    }

}
