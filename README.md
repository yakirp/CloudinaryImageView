![Image](https://res.cloudinary.com/cloudinary/image/upload/b_rgb:ffffff,c_scale,w_200/v1/logo/for_white_bg/cloudinary_logo_for_white_bg.png)
---	
[Cloudinary](https://cloudinary.com/) is a Software-as-a-Service (SaaS) solution for managing all your web or mobile application’s media assets in the cloud. Cloudinary offers an end-to-end solution for all your image and video needs, including upload, storage, administration, manipulation and delivery. Media upload, processing, and delivery are done on Cloudinary’s servers and automatically scale for handling high load and bursts of traffic.

# Cloudinary Image View
---	
CloudinaryImageView simplifies the the process of selecting the optimal resource by dynamically determine the pixel density of the Image View and manipulate the image to serve the most appropriate version according to the current downstream rate and conectivity quality.

## LQIP (Low Quality Image Placeholder)
CloudinaryImageView automate the process of creatign LQIP mechanizim wich could increase your app UX.


Usage
----
Add the CloudinaryImageView to the layout:

```
 <com.cloudinary.android.CloudinaryImageView
            android:id="@+id/image_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
```

To show an image, you need only do this:

```
CloudinaryImageView imageView = (CloudinaryImageView) findViewById(R.id.image_view);
imageView.setImagePublicId("<public_id>", <transformation>);
```

