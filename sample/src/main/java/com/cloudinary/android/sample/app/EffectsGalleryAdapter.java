package com.cloudinary.android.sample.app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.cloudinary.android.CloudinaryImageView;
import com.cloudinary.android.sample.R;
import com.cloudinary.android.sample.model.EffectData;


import java.util.List;

class EffectsGalleryAdapter extends RecyclerView.Adapter<EffectsGalleryAdapter.ImageViewHolder> {
    private final int requiredSize;
    private final ItemClickListener listener;
    private List<EffectData> images;
    private Context context;
    private EffectData selected = null;

    EffectsGalleryAdapter(Context context, List<EffectData> images, int requiredSize, ItemClickListener listener) {
        this.context = context;
        this.images = images;
        this.requiredSize = requiredSize;
        this.listener = listener;

        if (images.size() > 0) {
            selected = images.get(0);
        }
    }

    @Override
    public EffectsGalleryAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_effects_gallery, parent, false);
        viewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = (EffectData) v.getTag();
                if (listener != null) {
                    listener.onClick(selected);
                }

                notifyDataSetChanged();
            }
        });

        CloudinaryImageView imageView = (CloudinaryImageView) viewGroup.findViewById(R.id.image_view);
        imageView.getLayoutParams().height = requiredSize;
        return new EffectsGalleryAdapter.ImageViewHolder(viewGroup, imageView, viewGroup.findViewById(R.id.selected_indicator), (TextView) viewGroup.findViewById(R.id.effectName));
    }

    @Override
    public void onBindViewHolder(final EffectsGalleryAdapter.ImageViewHolder holder, int position) {
        EffectData data = images.get(position);
        String url = data.getThumbUrl();
        holder.itemView.setTag(images.get(position));
        holder.nameTextView.setText(data.getName());

        holder.imageView.setImagePublicId(data.getPublicId(),data.getTransformation());


       // Picasso.with(context).load(url).placeholder(R.drawable.placeholder).into(holder.imageView);

        if (selected != null && selected.equals(data)) {
            holder.selection.setVisibility(View.VISIBLE);
        } else {
            holder.selection.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public interface ItemClickListener {
        void onClick(EffectData data);
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        private final CloudinaryImageView imageView;
        private final View selection;
        private final TextView nameTextView;

        ImageViewHolder(final View itemView, final CloudinaryImageView imageView, View selection, TextView nameTextView) {
            super(itemView);
            this.imageView = imageView;
            this.selection = selection;
            this.nameTextView = nameTextView;
        }
    }
}