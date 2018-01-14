![Image](https://res.cloudinary.com/cloudinary/image/upload/b_rgb:ffffff,c_scale,w_200/v1/logo/for_white_bg/cloudinary_logo_for_white_bg.png)
---	
[Cloudinary](https://cloudinary.com/) is a Software-as-a-Service (SaaS) solution for managing all your web or mobile application’s media assets in the cloud. Cloudinary offers an end-to-end solution for all your image and video needs, including upload, storage, administration, manipulation and delivery. Media upload, processing, and delivery are done on Cloudinary’s servers and automatically scale for handling high load and bursts of traffic.

# Cloudinary Image View
CloudinaryImageView simplifies and automate the process of selecting the optimal resource by dynamically determine the pixel density of the Image View and manipulate the image to serve the most appropriate version according to the current downstream rate and conectivity quality.

## LQIP (Low Quality Image Placeholder)
First impressions matter.
CloudinaryImageView also automate the process of creating LQIP display mechanism which could increase your application UX, by generating extremely low-size image with approximately size of 200 byte. 
Images are an integral part of any moobile app experience, but sometimes they can be slow to download and display. 
This is especially true on low-connectivity mobile networks, which often leave you staring at an empty placeholder or a loading spinner as you wait for images to download. 
This is a problem in developing markets such as India, where many users are primarily using 2G networks.

## Usage
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

