/*
 * Copyright (C) 2012 CyberAgent
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vertextechnologies.magic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.opengl.Matrix;

import jp.co.cyberagent.android.gpuimage.*;

import java.util.LinkedList;
import java.util.List;


public class GPUImageFilterTools {


    public static void showDialog(final Context context,
                                  final OnGpuImageFilterChosenListener listener) {
        final FilterList filters = new FilterList();
        filters.addFilter("Contrast Camera", FilterType.CONTRAST);
        filters.addFilter("Brightness Camera", FilterType.BRIGHTNESS);

        filters.addFilter("Zoom Camera", FilterType.TRANSFORM2D);
        filters.addFilter("Contrast Background", FilterType.BACKGROUNDCONTRAST);
        filters.addFilter("Brightness Background", FilterType.BACKGROUNDBRIGHTNESS);

        filters.addFilter("Zoom Background", FilterType.BACKGROUNDZOOM);



        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose a filter");
        builder.setItems(filters.names.toArray(new String[filters.names.size()]),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int item) {
                        listener.onGpuImageFilterChosenListener(
                                createFilterForType(context, filters.filters.get(item)),filters.names.get(item));
                    }
                });
        builder.create().show();
    }

    private static GPUImageFilter createFilterForType(final Context context, final FilterType type) {
        switch (type) {


            case CONTRAST:
                return new GPUImageContrastFilter(2.0f);

            case BACKGROUNDCONTRAST:
                return new GPUImageContrastFilter(2.0f);
            case TRANSFORM2D:
                return new GPUImageTransformFilter();
            case BACKGROUNDZOOM:
                return new GPUImageTransformFilter();
            case BRIGHTNESS:
                return new GPUImageBrightnessFilter(1.5f);
            case BACKGROUNDBRIGHTNESS:
                return new GPUImageBrightnessFilter(1.5f);
            case CHANGEBACKGROUND:
                return new GPUImageTransformFilter();

            case GAMMA:
                return new GPUImageGammaFilter(2.0f);
            case INVERT:
                return new GPUImageColorInvertFilter();
            case PIXELATION:
                return new GPUImagePixelationFilter();
            case HUE:
                return new GPUImageHueFilter(90.0f);
            case GRAYSCALE:
                return new GPUImageGrayscaleFilter();
            case SEPIA:
                return new GPUImageSepiaFilter();
            case SHARPEN:
                GPUImageSharpenFilter sharpness = new GPUImageSharpenFilter();
                sharpness.setSharpness(2.0f);
                return sharpness;
            case SOBEL_EDGE_DETECTION:
                return new GPUImageSobelEdgeDetection();
            case THREE_X_THREE_CONVOLUTION:
                GPUImage3x3ConvolutionFilter convolution = new GPUImage3x3ConvolutionFilter();
                convolution.setConvolutionKernel(new float[] {
                        -1.0f, 0.0f, 1.0f,
                        -2.0f, 0.0f, 2.0f,
                        -1.0f, 0.0f, 1.0f
                });
                return convolution;
            case EMBOSS:
                return new GPUImageEmbossFilter();
            case POSTERIZE:
                return new GPUImagePosterizeFilter();
            case FILTER_GROUP:
                List<GPUImageFilter> filters = new LinkedList<GPUImageFilter>();
                filters.add(new GPUImageContrastFilter());
                filters.add(new GPUImageDirectionalSobelEdgeDetectionFilter());
                filters.add(new GPUImageGrayscaleFilter());
                return new GPUImageFilterGroup(filters);
            case SATURATION:
                return new GPUImageSaturationFilter(1.0f);
            case EXPOSURE:
                return new GPUImageExposureFilter(0.0f);
            case HIGHLIGHT_SHADOW:
                return new GPUImageHighlightShadowFilter(0.0f, 1.0f);
            case MONOCHROME:
                return new GPUImageMonochromeFilter(1.0f, new float[]{0.6f, 0.45f, 0.3f, 1.0f});
            case OPACITY:
                return new GPUImageOpacityFilter(1.0f);
            case RGB:
                return new GPUImageRGBFilter(1.0f, 1.0f, 1.0f);
            case WHITE_BALANCE:
                return new GPUImageWhiteBalanceFilter(5000.0f, 0.0f);
            case VIGNETTE:
                PointF centerPoint = new PointF();
                centerPoint.x = 0.5f;
                centerPoint.y = 0.5f;
                return new GPUImageVignetteFilter(centerPoint, new float[] {0.0f, 0.0f, 0.0f}, 0.3f, 0.75f);
            case TONE_CURVE:
                GPUImageToneCurveFilter toneCurveFilter = new GPUImageToneCurveFilter();
                toneCurveFilter.setFromCurveFileInputStream(
                        context.getResources().openRawResource(R.raw.tone_cuver_sample));
                return toneCurveFilter;
            case BLEND_DIFFERENCE:
                return createBlendFilter(context, GPUImageDifferenceBlendFilter.class,null);
            case BLEND_SOURCE_OVER:
                return createBlendFilter(context, GPUImageSourceOverBlendFilter.class,null);
            case BLEND_COLOR_BURN:
                return createBlendFilter(context, GPUImageColorBurnBlendFilter.class,null);
            case BLEND_COLOR_DODGE:
                return createBlendFilter(context, GPUImageColorDodgeBlendFilter.class,null);
            case BLEND_DARKEN:
                return createBlendFilter(context, GPUImageDarkenBlendFilter.class,null);
            case BLEND_DISSOLVE:
                return createBlendFilter(context, GPUImageDissolveBlendFilter.class,null);
            case BLEND_EXCLUSION:
                return createBlendFilter(context, GPUImageExclusionBlendFilter.class,null);


            case BLEND_HARD_LIGHT:
                return createBlendFilter(context, GPUImageHardLightBlendFilter.class,null);
            case BLEND_LIGHTEN:
                return createBlendFilter(context, GPUImageLightenBlendFilter.class,null);
            case BLEND_ADD:
                return createBlendFilter(context, GPUImageAddBlendFilter.class,null);
            case BLEND_DIVIDE:
                return createBlendFilter(context, GPUImageDivideBlendFilter.class,null);
            case BLEND_MULTIPLY:
                return createBlendFilter(context, GPUImageMultiplyBlendFilter.class,null);
            case BLEND_OVERLAY:
                return createBlendFilter(context, GPUImageOverlayBlendFilter.class,null);
            case BLEND_SCREEN:
                return createBlendFilter(context, GPUImageScreenBlendFilter.class,null);
            case BLEND_ALPHA:
                return createBlendFilter(context, GPUImageAlphaBlendFilter.class,null);
            case BLEND_COLOR:
                return createBlendFilter(context, GPUImageColorBlendFilter.class,null);
            case BLEND_HUE:
                return createBlendFilter(context, GPUImageHueBlendFilter.class,null);
            case BLEND_SATURATION:
                return createBlendFilter(context, GPUImageSaturationBlendFilter.class,null);
            case BLEND_LUMINOSITY:
                return createBlendFilter(context, GPUImageLuminosityBlendFilter.class,null);
            case BLEND_LINEAR_BURN:
                return createBlendFilter(context, GPUImageLinearBurnBlendFilter.class,null);
            case BLEND_SOFT_LIGHT:
                return createBlendFilter(context, GPUImageSoftLightBlendFilter.class,null);
            case BLEND_SUBTRACT:
                return createBlendFilter(context, GPUImageSubtractBlendFilter.class,null);
            case BLEND_CHROMA_KEY:
                return createBlendFilter(context, GPUImageChromaKeyBlendFilter.class,null);
            case BLEND_NORMAL:
                return createBlendFilter(context, GPUImageNormalBlendFilter.class,null);

            case LOOKUP_AMATORKA:
                GPUImageLookupFilter amatorka = new GPUImageLookupFilter();
                //amatorka.setBitmap(BitmapFactory.decodeResource(context.getResources(), jp.co.cyberagent.android.gpuimage.R.drawable.lookup_amatorka));
                return amatorka;
            case GAUSSIAN_BLUR:
                return new GPUImageGaussianBlurFilter();
            case CROSSHATCH:
                return new GPUImageCrosshatchFilter();

            case BOX_BLUR:
                return new GPUImageBoxBlurFilter();
            case CGA_COLORSPACE:
                return new GPUImageCGAColorspaceFilter();
            case DILATION:
                return new GPUImageDilationFilter();
            case KUWAHARA:
                return new GPUImageKuwaharaFilter();
            case RGB_DILATION:
                return new GPUImageRGBDilationFilter();
            case SKETCH:
                return new GPUImageSketchFilter();
            case TOON:
                return new GPUImageToonFilter();
            case SMOOTH_TOON:
                return new GPUImageSmoothToonFilter();

            case BULGE_DISTORTION:
                return new GPUImageBulgeDistortionFilter();
            case GLASS_SPHERE:
                return new GPUImageGlassSphereFilter();
            case HAZE:
                return new GPUImageHazeFilter();
            case LAPLACIAN:
                return new GPUImageLaplacianFilter();
            case NON_MAXIMUM_SUPPRESSION:
                return new GPUImageNonMaximumSuppressionFilter();
            case SPHERE_REFRACTION:
                return new GPUImageSphereRefractionFilter();
            case SWIRL:
                return new GPUImageSwirlFilter();
            case WEAK_PIXEL_INCLUSION:
                return new GPUImageWeakPixelInclusionFilter();
            case FALSE_COLOR:
                return new GPUImageFalseColorFilter();
            case COLOR_BALANCE:
                return new GPUImageColorBalanceFilter();
            case LEVELS_FILTER_MIN:
                GPUImageLevelsFilter levelsFilter = new GPUImageLevelsFilter();
                levelsFilter.setMin(0.0f, 3.0f, 1.0f);
                return levelsFilter;
            case HALFTONE:
                return new GPUImageHalftoneFilter();

            case BILATERAL_BLUR:
                return new GPUImageBilateralFilter();

            default:
                throw new IllegalStateException("No filter of that type!");
        }

    }

    private static Bitmap bitmapOri;
    public static GPUImageFilter createBlendFilter(Context context, Class<? extends GPUImageTwoInputFilter> filterClass, Bitmap bitmap) {
        try {
            GPUImageTwoInputFilter filter = filterClass.newInstance();


            if(bitmap==null) {
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);

                //android.graphics.Matrix mat = new android.graphics.Matrix();
                //mat.postRotate(90);
                //bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true);

            }

            bitmapOri = bitmap;

            filter.setBitmap(bitmap);

            return filter;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public interface OnGpuImageFilterChosenListener {
        void onGpuImageFilterChosenListener(GPUImageFilter filter, String filterType);
    }

    private enum FilterType {
        CONTRAST, GRAYSCALE, SHARPEN, SEPIA, SOBEL_EDGE_DETECTION, THREE_X_THREE_CONVOLUTION, FILTER_GROUP, EMBOSS, POSTERIZE, GAMMA, BRIGHTNESS, INVERT, HUE, PIXELATION,
        SATURATION, EXPOSURE, HIGHLIGHT_SHADOW, MONOCHROME, OPACITY, RGB, WHITE_BALANCE, VIGNETTE, TONE_CURVE, BLEND_COLOR_BURN, BLEND_COLOR_DODGE, BLEND_DARKEN, BLEND_DIFFERENCE,
        BLEND_DISSOLVE, BLEND_EXCLUSION, BLEND_SOURCE_OVER, BLEND_HARD_LIGHT, BLEND_LIGHTEN, BLEND_ADD, BLEND_DIVIDE, BLEND_MULTIPLY, BLEND_OVERLAY, BLEND_SCREEN, BLEND_ALPHA,
        BLEND_COLOR, BLEND_HUE, BLEND_SATURATION, BLEND_LUMINOSITY, BLEND_LINEAR_BURN, BLEND_SOFT_LIGHT, BLEND_SUBTRACT, BLEND_CHROMA_KEY, BLEND_NORMAL, LOOKUP_AMATORKA,
        GAUSSIAN_BLUR, CROSSHATCH, BOX_BLUR, CGA_COLORSPACE, DILATION, KUWAHARA, RGB_DILATION, SKETCH, TOON, SMOOTH_TOON, BULGE_DISTORTION, GLASS_SPHERE, HAZE, LAPLACIAN, NON_MAXIMUM_SUPPRESSION,
        SPHERE_REFRACTION, SWIRL, WEAK_PIXEL_INCLUSION, FALSE_COLOR, COLOR_BALANCE, LEVELS_FILTER_MIN, BILATERAL_BLUR, HALFTONE, TRANSFORM2D,BACKGROUNDCONTRAST,BACKGROUNDBRIGHTNESS,BACKGROUNDZOOM,CHANGEBACKGROUND
    }

    private static class FilterList {
        public List<String> names = new LinkedList<String>();
        public List<FilterType> filters = new LinkedList<FilterType>();

        public void addFilter(final String name, final FilterType filter) {
            names.add(name);
            filters.add(filter);
        }
    }

    public static class FilterAdjuster {

        private final Adjuster<? extends GPUImageFilter> adjuster;

        public FilterAdjuster(final GPUImageFilter filter,Context context) {
            if (filter instanceof GPUImageSharpenFilter) {
                adjuster = new SharpnessAdjuster().filter(filter);
            } else if (filter instanceof GPUImageSepiaFilter) {
                adjuster = new SepiaAdjuster().filter(filter);
            } else if (filter instanceof GPUImageContrastFilter) {
                adjuster = new ContrastAdjuster().filter(filter);
            } else if (filter instanceof GPUImageGammaFilter) {
                adjuster = new GammaAdjuster().filter(filter);
            } else if (filter instanceof GPUImageBrightnessFilter) {
                adjuster = new BrightnessAdjuster().filter(filter);
            } else if (filter instanceof GPUImageSobelEdgeDetection) {
                adjuster = new SobelAdjuster().filter(filter);
            } else if (filter instanceof GPUImageEmbossFilter) {
                adjuster = new EmbossAdjuster().filter(filter);
            } else if (filter instanceof GPUImage3x3TextureSamplingFilter) {
                adjuster = new GPU3x3TextureAdjuster().filter(filter);
            } else if (filter instanceof GPUImageHueFilter) {
                adjuster = new HueAdjuster().filter(filter);
            } else if (filter instanceof GPUImagePosterizeFilter) {
                adjuster = new PosterizeAdjuster().filter(filter);
            } else if (filter instanceof GPUImagePixelationFilter) {
                adjuster = new PixelationAdjuster().filter(filter);
            } else if (filter instanceof GPUImageSaturationFilter) {
                adjuster = new SaturationAdjuster().filter(filter);
            } else if (filter instanceof GPUImageExposureFilter) {
                adjuster = new ExposureAdjuster().filter(filter);
            } else if (filter instanceof GPUImageHighlightShadowFilter) {
                adjuster = new HighlightShadowAdjuster().filter(filter);
            } else if (filter instanceof GPUImageMonochromeFilter) {
                adjuster = new MonochromeAdjuster().filter(filter);
            } else if (filter instanceof GPUImageOpacityFilter) {
                adjuster = new OpacityAdjuster().filter(filter);
            } else if (filter instanceof GPUImageRGBFilter) {
                adjuster = new RGBAdjuster().filter(filter);
            } else if (filter instanceof GPUImageWhiteBalanceFilter) {
                adjuster = new WhiteBalanceAdjuster().filter(filter);
            } else if (filter instanceof GPUImageVignetteFilter) {
                adjuster = new VignetteAdjuster().filter(filter);
            } else if (filter instanceof GPUImageDissolveBlendFilter) {
                adjuster = new DissolveBlendAdjuster().filter(filter);
            } else if (filter instanceof GPUImageGaussianBlurFilter) {
                adjuster = new GaussianBlurAdjuster().filter(filter);
            } else if (filter instanceof GPUImageCrosshatchFilter) {
                adjuster = new CrosshatchBlurAdjuster().filter(filter);
            } else if (filter instanceof GPUImageBulgeDistortionFilter) {
                adjuster = new BulgeDistortionAdjuster().filter(filter);
            } else if (filter instanceof GPUImageGlassSphereFilter) {
                adjuster = new GlassSphereAdjuster().filter(filter);
            } else if (filter instanceof GPUImageHazeFilter) {
                adjuster = new HazeAdjuster().filter(filter);
            } else if (filter instanceof GPUImageSphereRefractionFilter) {
                adjuster = new SphereRefractionAdjuster().filter(filter);
            } else if (filter instanceof GPUImageSwirlFilter) {
                adjuster = new SwirlAdjuster().filter(filter);
            } else if (filter instanceof GPUImageColorBalanceFilter) {
                adjuster = new ColorBalanceAdjuster().filter(filter);
            } else if (filter instanceof GPUImageLevelsFilter) {
                adjuster = new LevelsMinMidAdjuster().filter(filter);
            } else if (filter instanceof GPUImageBilateralFilter) {
                adjuster = new BilateralAdjuster().filter(filter);
            } else if (filter instanceof GPUImageTransformFilter) {
                adjuster = new RotateAdjuster().filter(filter);
            }
            else if (filter instanceof GPUImageFilterGroup) {

                GroupAdjuster groupAdjuster = new GroupAdjuster();

                groupAdjuster.setContext(context);
                adjuster = groupAdjuster.filter(filter);
            }
            else {

                adjuster = null;
            }
        }

        public boolean canAdjust() {
            return adjuster != null;
        }

        public void adjust(final int percentage) {
            if (adjuster != null) {
                adjuster.adjust(percentage);
            }
        }

        private abstract class Adjuster<T extends GPUImageFilter> {
            private T filter;

            @SuppressWarnings("unchecked")
            public Adjuster<T> filter(final GPUImageFilter filter) {
                this.filter = (T) filter;
                return this;
            }

            public T getFilter() {
                return filter;
            }

            public abstract void adjust(int percentage);

            protected float range(final int percentage, final float start, final float end) {
                return (end - start) * percentage / 100.0f + start;
            }

            protected int range(final int percentage, final int start, final int end) {
                return (end - start) * percentage / 100 + start;
            }
        }

        private class SharpnessAdjuster extends Adjuster<GPUImageSharpenFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setSharpness(range(percentage, -4.0f, 4.0f));
            }
        }

        private class PixelationAdjuster extends Adjuster<GPUImagePixelationFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setPixel(range(percentage, 1.0f, 100.0f));
            }
        }

        private class HueAdjuster extends Adjuster<GPUImageHueFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setHue(range(percentage, 0.0f, 360.0f));
            }
        }

        private class ContrastAdjuster extends Adjuster<GPUImageContrastFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setContrast(range(percentage, 0.0f, 2.0f));
            }
        }

        private int zoomBackground=50;

        private class GroupAdjuster extends Adjuster<GPUImageFilterGroup> {

            public Context  context;

            public void setContext(Context context){

                this.context = context;
            }
            @Override
            public void adjust(final int percentage) {

                switch (getFilter().getSelectedFiltersIndex()){

                    case 0:
                        GPUImageContrastFilter contrastFilter = (GPUImageContrastFilter) getFilter().getFilters().get(0);

                        contrastFilter .setContrast(range(percentage, 0.0f, 2.0f));

                        break;

                    case 1:
                        GPUImageBrightnessFilter brightnessFilter = (GPUImageBrightnessFilter) getFilter().getFilters().get(1);

                        brightnessFilter .setBrightness(range(percentage, -1.0f, 1.0f));

                        break;

                    case 2:

                        GPUImageTransformFilter transformFilter = (GPUImageTransformFilter) getFilter().getFilters().get(2);

                        float[] transform = new float[16];

                        float scaleX = 1;
                        float scaleY = 1;

                        scaleX = scaleX/((float) (100-percentage)/50);

                        scaleY = scaleY/ ((float)(100-percentage)/50);

                        Matrix.setIdentityM(transform, 0);

                        Matrix.scaleM(transform,0,scaleX,scaleY,1);

                        transformFilter.setTransform3D(transform);





                        break;

                    case 3:

                        GPUImageChromaKeyBlendFilter zoomContrastBackgroundFilter = (GPUImageChromaKeyBlendFilter) getFilter().getFilters().get(3);


                        float zoomContrastBackgroundScaleX = 1;
                        float zoomContrastBackgroundScaleY = 1;

                        zoomContrastBackgroundScaleX = zoomContrastBackgroundScaleX/((float) zoomBackground/50);

                        zoomContrastBackgroundScaleY = zoomContrastBackgroundScaleY/ ((float)zoomBackground/50);


                        GPUImageTwoInputFilter  zoomContrastBackgroundGpuImageTwoInputFilter = (GPUImageTwoInputFilter)zoomContrastBackgroundFilter;


                        int zoomContrastBackgroundX = (int) ((bitmapOri.getWidth()*(1-zoomContrastBackgroundScaleX))/2);
                        int zoomContrastBackgroundY = (int) ((bitmapOri.getWidth()*(1-zoomContrastBackgroundScaleY))/2);
                        int zoomContrastBackgroundWidth = (int) (bitmapOri.getWidth() * zoomContrastBackgroundScaleX);
                        int zoomContrastBackgroundHeight = (int) (bitmapOri.getHeight()* zoomContrastBackgroundScaleY);


                        Bitmap zoomContrastBackgroundCroppedBmp = Bitmap.createBitmap(bitmapOri,zoomContrastBackgroundX ,zoomContrastBackgroundY ,
                                zoomContrastBackgroundWidth, zoomContrastBackgroundHeight);


                        GPUImageContrastFilter contrastFilterBackground = new GPUImageContrastFilter(range(percentage, 0.0f, 2.0f));


                        GPUImage gpuImage = new GPUImage(context);

                        gpuImage.setFilter(contrastFilterBackground);



                        Bitmap bitmapRender =gpuImage.getBitmapWithFilterApplied(zoomContrastBackgroundCroppedBmp);
                        zoomContrastBackgroundFilter.setBitmap(bitmapRender);
                        break;

                    case 4:


                        GPUImageChromaKeyBlendFilter zoomBrightnessBackgroundFilter = (GPUImageChromaKeyBlendFilter) getFilter().getFilters().get(3);


                        float zoomBrightnessBackgroundScaleX = 1;
                        float zoomBrightnessBackgroundScaleY = 1;

                        zoomBrightnessBackgroundScaleX = zoomBrightnessBackgroundScaleX/((float) zoomBackground/50);

                        zoomBrightnessBackgroundScaleY = zoomBrightnessBackgroundScaleY/ ((float)zoomBackground/50);


                        GPUImageTwoInputFilter  zoomBrightnessBackgroundGpuImageTwoInputFilter = (GPUImageTwoInputFilter)zoomBrightnessBackgroundFilter;


                        int zoomBrightnessBackgroundX = (int) ((bitmapOri.getWidth()*(1-zoomBrightnessBackgroundScaleX))/2);
                        int zoomBrightnessBackgroundY = (int) ((bitmapOri.getWidth()*(1-zoomBrightnessBackgroundScaleY))/2);
                        int zoomBrightnessBackgroundWidth = (int) (bitmapOri.getWidth() * zoomBrightnessBackgroundScaleX);
                        int zoomBrightnessBackgroundHeight = (int) (bitmapOri.getHeight()* zoomBrightnessBackgroundScaleY);


                        Bitmap zoomBrightnessBackgroundCroppedBmp = Bitmap.createBitmap(bitmapOri,zoomBrightnessBackgroundX ,zoomBrightnessBackgroundY ,
                                zoomBrightnessBackgroundWidth, zoomBrightnessBackgroundHeight);



                        GPUImageBrightnessFilter brightnessFilter1 = new GPUImageBrightnessFilter(range(percentage, -1.0f, 1.0f));

                        GPUImage gpuImage1 = new GPUImage(context);

                        gpuImage1.setImage(zoomBrightnessBackgroundCroppedBmp);

                        gpuImage1.setFilter(brightnessFilter1);

                        zoomBrightnessBackgroundGpuImageTwoInputFilter.setBitmap(gpuImage1.getBitmapWithFilterApplied());


                        break;

                    case 5:
                        GPUImageChromaKeyBlendFilter zoomBackgroundFilter = (GPUImageChromaKeyBlendFilter) getFilter().getFilters().get(3);


                        zoomBackground =percentage;
                        float zoomBackgroundScaleX = 1;
                        float zoomBackgroundScaleY = 1;

                        zoomBackgroundScaleX = zoomBackgroundScaleX/((float) zoomBackground/50);

                        zoomBackgroundScaleY = zoomBackgroundScaleY/ ((float)zoomBackground/50);


                        GPUImageTwoInputFilter  zoomBackgroundGpuImageTwoInputFilter = (GPUImageTwoInputFilter)zoomBackgroundFilter;


                        int zoomBackgroundX = (int) ((bitmapOri.getWidth()*(1-zoomBackgroundScaleX))/2);
                        int zoomBackgroundY = (int) ((bitmapOri.getWidth()*(1-zoomBackgroundScaleY))/2);
                        int zoomBackgroundWidth = (int) (bitmapOri.getWidth() * zoomBackgroundScaleX);
                        int zoomBackgroundHeight = (int) (bitmapOri.getHeight()* zoomBackgroundScaleY);


                        Bitmap zoomBackgroundCroppedBmp = Bitmap.createBitmap(bitmapOri,zoomBackgroundX ,zoomBackgroundY ,
                                zoomBackgroundWidth, zoomBackgroundHeight);

                        zoomBackgroundGpuImageTwoInputFilter.setBitmap(zoomBackgroundCroppedBmp);

                        break;

                    case 6:
                        break;


                }
            }
        }



        private class GammaAdjuster extends Adjuster<GPUImageGammaFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setGamma(range(percentage, 0.0f, 3.0f));
            }
        }

        private class BrightnessAdjuster extends Adjuster<GPUImageBrightnessFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setBrightness(range(percentage, -1.0f, 1.0f));
            }
        }

        private class SepiaAdjuster extends Adjuster<GPUImageSepiaFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setIntensity(range(percentage, 0.0f, 2.0f));
            }
        }

        private class SobelAdjuster extends Adjuster<GPUImageSobelEdgeDetection> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setLineSize(range(percentage, 0.0f, 5.0f));
            }
        }

        private class EmbossAdjuster extends Adjuster<GPUImageEmbossFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setIntensity(range(percentage, 0.0f, 4.0f));
            }
        }

        private class PosterizeAdjuster extends Adjuster<GPUImagePosterizeFilter> {
            @Override
            public void adjust(final int percentage) {
                // In theorie to 256, but only first 50 are interesting
                getFilter().setColorLevels(range(percentage, 1, 50));
            }
        }

        private class GPU3x3TextureAdjuster extends Adjuster<GPUImage3x3TextureSamplingFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setLineSize(range(percentage, 0.0f, 5.0f));
            }
        }

        private class SaturationAdjuster extends Adjuster<GPUImageSaturationFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setSaturation(range(percentage, 0.0f, 2.0f));
            }
        }

        private class ExposureAdjuster extends Adjuster<GPUImageExposureFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setExposure(range(percentage, -10.0f, 10.0f));
            }
        }

        private class HighlightShadowAdjuster extends Adjuster<GPUImageHighlightShadowFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setShadows(range(percentage, 0.0f, 1.0f));
                getFilter().setHighlights(range(percentage, 0.0f, 1.0f));
            }
        }

        private class MonochromeAdjuster extends Adjuster<GPUImageMonochromeFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setIntensity(range(percentage, 0.0f, 1.0f));
                //getFilter().setColor(new float[]{0.6f, 0.45f, 0.3f, 1.0f});
            }
        }

        private class OpacityAdjuster extends Adjuster<GPUImageOpacityFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setOpacity(range(percentage, 0.0f, 1.0f));
            }
        }

        private class RGBAdjuster extends Adjuster<GPUImageRGBFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setRed(range(percentage, 0.0f, 1.0f));
                //getFilter().setGreen(range(percentage, 0.0f, 1.0f));
                //getFilter().setBlue(range(percentage, 0.0f, 1.0f));
            }
        }

        private class WhiteBalanceAdjuster extends Adjuster<GPUImageWhiteBalanceFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setTemperature(range(percentage, 2000.0f, 8000.0f));
                //getFilter().setTint(range(percentage, -100.0f, 100.0f));
            }
        }

        private class VignetteAdjuster extends Adjuster<GPUImageVignetteFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setVignetteStart(range(percentage, 0.0f, 1.0f));
            }
        }

        private class DissolveBlendAdjuster extends Adjuster<GPUImageDissolveBlendFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setMix(range(percentage, 0.0f, 1.0f));
            }
        }

        private class GaussianBlurAdjuster extends Adjuster<GPUImageGaussianBlurFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setBlurSize(range(percentage, 0.0f, 1.0f));
            }
        }

        private class CrosshatchBlurAdjuster extends Adjuster<GPUImageCrosshatchFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setCrossHatchSpacing(range(percentage, 0.0f, 0.06f));
                getFilter().setLineWidth(range(percentage, 0.0f, 0.006f));
            }
        }

        private class BulgeDistortionAdjuster extends Adjuster<GPUImageBulgeDistortionFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setRadius(range(percentage, 0.0f, 1.0f));
                getFilter().setScale(range(percentage, -1.0f, 1.0f));
            }
        }

        private class GlassSphereAdjuster extends Adjuster<GPUImageGlassSphereFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setRadius(range(percentage, 0.0f, 1.0f));
            }
        }

        private class HazeAdjuster extends Adjuster<GPUImageHazeFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setDistance(range(percentage, -0.3f, 0.3f));
                getFilter().setSlope(range(percentage, -0.3f, 0.3f));
            }
        }

        private class SphereRefractionAdjuster extends Adjuster<GPUImageSphereRefractionFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setRadius(range(percentage, 0.0f, 1.0f));
            }
        }

        private class SwirlAdjuster extends Adjuster<GPUImageSwirlFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setAngle(range(percentage, 0.0f, 2.0f));
            }
        }

        private class ColorBalanceAdjuster extends Adjuster<GPUImageColorBalanceFilter> {

            @Override
            public void adjust(int percentage) {
                getFilter().setMidtones(new float[]{
                        range(percentage, 0.0f, 1.0f),
                        range(percentage / 2, 0.0f, 1.0f),
                        range(percentage / 3, 0.0f, 1.0f)});
            }
        }

        private class LevelsMinMidAdjuster extends Adjuster<GPUImageLevelsFilter> {
            @Override
            public void adjust(int percentage) {
                getFilter().setMin(0.0f, range(percentage, 0.0f, 1.0f), 1.0f);
            }
        }

        private class BilateralAdjuster extends Adjuster<GPUImageBilateralFilter> {
            @Override
            public void adjust(final int percentage) {
                getFilter().setDistanceNormalizationFactor(range(percentage, 0.0f, 15.0f));
            }
        }

        private class RotateAdjuster extends Adjuster<GPUImageTransformFilter> {
            @Override
            public void adjust(final int percentage) {
                float[] transform = new float[16];
                Matrix.setRotateM(transform, 0, 360 * percentage / 100, 0, 0, 1.0f);


                getFilter().setTransform3D(transform);
            }
        }

    }
}

