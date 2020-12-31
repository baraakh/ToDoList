package com.bik.todolist.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bik.todolist.R;
import com.bik.todolist.model.ListsModel;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.ViewHolder> {
    private List<ListsModel> listsModels;
    private OnCardviewClickListener listener;

    public ListsAdapter(List<ListsModel> listsModels, OnCardviewClickListener listener) {
        this.listsModels = listsModels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return listsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView count;
        private MaterialCardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_list_name);
            count = itemView.findViewById(R.id.tv_lists_count);
            cardView = itemView.findViewById(R.id.cv_lists);
        }

        public void bind(int position) {
            ListsModel model = listsModels.get(position);
            name.setText(model.getListName());
            count.setText(String.valueOf(model.getListcount()));
            cardView.setOnClickListener(v -> {
                listener.onCardClick(model);
            });
        }

    }

    public interface OnCardviewClickListener {
        void onCardClick(ListsModel listsModel);
    }
}
