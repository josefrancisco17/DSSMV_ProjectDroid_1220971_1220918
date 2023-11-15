package com.example.dssmv_projectdroid_1220971_1220918.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.dssmv_projectdroid_1220971_1220918.R;
import com.example.dssmv_projectdroid_1220971_1220918.models.Checkout;
import com.example.dssmv_projectdroid_1220971_1220918.models.Library;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ListViewAdapterCheckOut extends BaseAdapter {

    private Context context;
    private List<Checkout> items;

    public ListViewAdapterCheckOut(Context context, List<Checkout> items) {
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

    public String getName(int position) {
        return this.items.get(position).getBook().getBook().getTitle();
    }

    public String getDueDate(int position) {
        String timestamp = this.items.get(position).getDueDate();
        LocalDateTime dateTime = LocalDateTime.parse(timestamp);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = dateTime.format(formatter);
        return formattedDateTime;
    }

    public boolean isActive(int position) {
        return this.items.get(position).isActive();
    }

    public String getLibraryName(int position) {
        return this.items.get(position).getBook().getLibrary().getName();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.list_item_checkout, null);
        } else {
            itemView = convertView;
        }

        TextView tvBookName = itemView.findViewById(R.id.textview_BookName);
        TextView tvLibraryName = itemView.findViewById(R.id.textview_LibraryName);
        TextView tvDueDate = itemView.findViewById(R.id.textview_dueDate);
        TextView tvActive = itemView.findViewById(R.id.textview_active);

        tvBookName.setText(getName(position));
        tvLibraryName.setText(getLibraryName(position));
        tvDueDate.setText("   Due Date: " + getDueDate(position));
        tvActive.setText("   Active: " + isActive(position));

        return itemView;
    }
}
