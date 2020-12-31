package com.bik.todolist.Adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bik.todolist.R;
import com.bik.todolist.model.TaskModel;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<TaskModel> taskModels;
    private OnCheckBoxListener listener;

    public TaskAdapter(List<TaskModel> taskModels, OnCheckBoxListener listener) {
        this.taskModels = taskModels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_card_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return taskModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private CheckBox checkBox;
        private MaterialCardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_task_name);
            checkBox = itemView.findViewById(R.id.cb_task);
            cardView = itemView.findViewById(R.id.cv_task);
        }

        public void bind(int position) {
            TaskModel taskModel = taskModels.get(position);
            textView.setText(taskModel.getTaskName());
            if (taskModel.isChecked()) {
                textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                checkBox.setChecked(true);
            }

            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                boolean b = listener.onCheck(taskModel.getTaskName(), isChecked);
                if (!b) {
                    textView.setPaintFlags(0);
                }
            });

            cardView.setOnClickListener(v -> listener.onClick(taskModel));
        }
    }

    public interface OnCheckBoxListener {
        boolean onCheck(String name, boolean b);

        void onClick(TaskModel taskModel);
    }
}
