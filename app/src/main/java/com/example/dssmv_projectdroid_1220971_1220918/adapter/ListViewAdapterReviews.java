package com.example.dssmv_projectdroid_1220971_1220918.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import com.example.dssmv_projectdroid_1220971_1220918.R;
import com.example.dssmv_projectdroid_1220971_1220918.models.Review;


public class ListViewAdapterReviews extends BaseAdapter {

    private Context context;
    private List<Review> items;

    public ListViewAdapterReviews(Context context, List<Review> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public String getReviewer(int position) {
        return this.items.get(position).getReviewer();
    }

    public String getReview(int position) {
        return this.items.get(position).getReview();
    }

    public boolean isRecommended(int position) {
        return this.items.get(position).isRecommended();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.list_item_review, null);
        }

        TextView tvReviewer = itemView.findViewById(R.id.textview_reviewer);
        TextView tvReview = itemView.findViewById(R.id.textview_review);
        TextView tvRecommended = itemView.findViewById(R.id.textview_recommended);

        tvReviewer.setText("Reviewer: " + getReviewer(position));
        tvReview.setText(getReview(position));
        tvRecommended.setText("Recommended: " + isRecommended(position));

        return itemView;
    }

}
