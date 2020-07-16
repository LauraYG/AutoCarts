package com.example.lsi_app;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomChooseTopicItemAdapter extends RecyclerView.Adapter<CustomChooseTopicItemAdapter.ItemViewHolder> {

    private List<String> topicList;
    private Dialog dialog;

    public CustomChooseTopicItemAdapter(List<String> topicList, Dialog dialog) {
        this.topicList = topicList;
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_row_layout, parent, false);
        TextView textView = view.findViewById(R.id.topic_item_text_view);
        ImageView imageView = view.findViewById(R.id.topic_item_image_view);
        return new ItemViewHolder(view, textView, imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        if(topicList.get(position) != null) {
            final String topic = topicList.get(position);
            holder.textView.setText(topic);
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VisualizationActivity.subscribeToTopic(topic);
                    dialog.dismiss();
                }
            });
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VisualizationActivity.subscribeToTopic(topic);
                    dialog.dismiss();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    static class ItemViewHolder extends  RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public ItemViewHolder(@NonNull View itemView, TextView textView, ImageView imageView) {
            super(itemView);
            this.textView = textView;
            this.imageView = imageView;
        }
    }
}
