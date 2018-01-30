package com.cloudinary.android;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewTreeObserver;
import com.cloudinary.Transformation;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.network.connectionclass.ConnectionClassManager;
import com.facebook.network.connectionclass.ConnectionQuality;

/**
 * Cloudinary Image View
 *
 * @author Yakir Perlin
 * @version 0.1
 * @since 08/01/2017
 */

public class CloudinaryImageView  extends SimpleDraweeView{

    private static final String TAG = "cld_img_view";
 
    public CloudinaryImageView(Context context) {
        super(context);
    }

    public CloudinaryImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CloudinaryImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CloudinaryImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }



    public void setImagePublicId(final String publicId) {
        setImagePublicId(publicId, new Transformation());


    }

    public void setImagePublicId(final String publicId , final Transformation transformation) {

        if (publicId==null) return;

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

                setMultiUrls(getPlaceholder(publicId, transformation),getHighResUrl(publicId, transformation));


            }

        });

    }

    private String getPlaceholder(String publicId, Transformation transformation) {

        if (publicId==null) return "";

        //Set default low res placeholder
        String placeHolderUrl = MediaManager.get().getCloudinary().url()
                .secure(true)
                .resourceType("image")
                .transformation(new Transformation(transformation).width(42).height(42).quality("auto:low").effect("blur",300))
                .format("webp")
                .generate(publicId);


        ImagePipeline imagePipeline = Fresco.getImagePipeline();

        //Check for already cached image that could be as low quality placeholder
        for (ConnectionQuality quality:ConnectionQuality.values()) {

            String tmpUrl =  MediaManager.get().getCloudinary().url()
                    .secure(true)
                    .resourceType("image")
                    .transformation(new Transformation(transformation).crop("fill").width("auto:100:"+getWidth()).height(getHeight()).quality(getImageQualityBaseOnBandwidthQuality(quality)))
                    .format("webp")
                    .generate(publicId);


              if (imagePipeline.isInBitmapMemoryCache(Uri.parse(tmpUrl))) {
                placeHolderUrl = tmpUrl;
            }

        }

        Logger.d(TAG, placeHolderUrl);
        return placeHolderUrl;

    }

    private String getHighResUrl(String publicId, Transformation transformation) {

        if (publicId==null) return "";

        ConnectionQuality currentBandwidthQuality = ConnectionClassManager.getInstance().getCurrentBandwidthQuality();
        //Set the current best image according the bandwidth quality
        String highResImageUrl =   MediaManager.get().getCloudinary().url()
                .secure(true)
                .resourceType("image")
                .transformation(new Transformation(transformation).crop("fill").width("auto:100:"+getWidth()).height(getHeight()).quality(getImageQualityBaseOnBandwidthQuality()))
                .format("webp")
                .generate(publicId);

        //Check if we have more high res image that already cached
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        for (ConnectionQuality quality:ConnectionQuality.values()) {

              String tmpUrl =  MediaManager.get().getCloudinary().url()
                    .secure(true)
                    .resourceType("image")
                    .transformation(new Transformation(transformation).crop("fill").width("auto:100:"+getWidth()).height(getHeight()).quality(getImageQualityBaseOnBandwidthQuality(quality)))
                    .format("webp")
                    .generate(publicId);

              if (imagePipeline.isInBitmapMemoryCache(Uri.parse(tmpUrl)) && quality.ordinal() >= currentBandwidthQuality.ordinal()) {

                  highResImageUrl = tmpUrl;
              }



        }

        Logger.d(TAG, "Current bandwidth quality is "+ currentBandwidthQuality +", URL: "+highResImageUrl);
        return highResImageUrl;


    }

    private String getImageQualityBaseOnBandwidthQuality() {
        return getImageQualityBaseOnBandwidthQuality(ConnectionClassManager.getInstance().getCurrentBandwidthQuality());
    }

    private String getImageQualityBaseOnBandwidthQuality(ConnectionQuality connectionQuality) {

        String quality= "";



        switch (connectionQuality) {

            case POOR:
                quality = "auto:low";
                break;
            case MODERATE:
                quality = "auto:eco";
                break;

            case UNKNOWN:
            case GOOD:
            case EXCELLENT:
                quality = "auto";
                break;

        }

        return quality;
    }

    private void getRelevantURL () {

    }

    public void setMultiUrls(String l, String h) {


        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setLowResImageRequest(ImageRequest.fromUri(l))
                .setImageRequest(ImageRequest.fromUri(h))

                .setOldController(getController())
                .build();

        setController(controller);

    }

    @Override
    public void setController(@javax.annotation.Nullable DraweeController draweeController) {



        super.setController(draweeController);
    }
}
