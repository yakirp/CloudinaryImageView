package com.cloudinary.android;

import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;

import com.facebook.device.yearclass.YearClass;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpNetworkFetcher;
import com.facebook.imagepipeline.common.BytesRange;
import com.facebook.network.connectionclass.ConnectionClassManager;

import java.util.Map;
import java.util.concurrent.Executor;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by yakirperlin on 1/9/18.
 */

public class CloudinaryHttpNetworkFetcher extends OkHttpNetworkFetcher {
    public CloudinaryHttpNetworkFetcher(OkHttpClient okHttpClient) {
        super(okHttpClient);
    }
 
    @Override
    public void fetch(OkHttpNetworkFetchState fetchState, Callback callback) {

        fetchState.submitTime = SystemClock.elapsedRealtime();
        final Uri uri = fetchState.getUri();


        try {
            final Request.Builder requestBuilder = new Request.Builder()
                    .cacheControl(new CacheControl.Builder().noStore().build())
                    .header("User-Agent", "CloudinaryImageView / 0.1 ()" + String.valueOf(YearClass.get(MediaManager.get().getContext())))
                    .url(uri.toString())
                    .get();

            final BytesRange bytesRange = fetchState.getContext().getImageRequest().getBytesRange();
            if (bytesRange != null) {
                requestBuilder.addHeader("Range", bytesRange.toHttpRangeHeaderValue());
            }

            fetchWithRequest(fetchState, callback, requestBuilder.build());
        } catch (Exception e) {
            // handle error while creating the request
            callback.onFailure(e);
        }

    }

    @Override
    public void onFetchCompletion(OkHttpNetworkFetchState fetchState, int byteSize) {
        super.onFetchCompletion(fetchState, byteSize);
        ConnectionClassManager.getInstance().addBandwidth(byteSize, fetchState.fetchCompleteTime - fetchState.submitTime);
    }

    @Override
    public Map<String, String> getExtraMap(OkHttpNetworkFetchState fetchState, int byteSize) {
        return super.getExtraMap(fetchState, byteSize);
    }
}
