package com.example.dssmv_projectdroid_1220971_1220918.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dssmv_projectdroid_1220971_1220918.R;

import java.util.List;

public class RecyclerViewAdapterLibraryBook extends RecyclerView.Adapter<RecyclerViewAdapterLibraryBook.ViewHolder> {
    private List<String> libraryBooksList;
    private RecyclerViewAdapterLibraryBook.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public RecyclerViewAdapterLibraryBook(List<String> libraryBooksList, RecyclerViewAdapterLibraryBook.OnItemClickListener onItemClickListener) {
        this.libraryBooksList = libraryBooksList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterLibraryBook.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new RecyclerViewAdapterLibraryBook.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterLibraryBook.ViewHolder holder, int position) {
        holder.textView.setText(libraryBooksList.get(position));

        // Set click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return libraryBooksList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recyclerview_item);
        }
    }

    public void filterList(List<String> filteredList) {
        libraryBooksList = filteredList;
        notifyDataSetChanged();
    }
}
