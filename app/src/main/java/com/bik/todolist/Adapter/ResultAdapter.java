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
import com.bik.todolist.model.ResultModel;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
    private List<ResultModel> resultModels;
    private OnCheckBoxListener listener;

    public ResultAdapter(List<ResultModel> resultModels, OnCheckBoxListener listener) {
        this.resultModels = resultModels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.result_card_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return resultModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private TextView listTv;
        private CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_task_name);
            listTv = itemView.findViewById(R.id.tv_list_name);
            checkBox = itemView.findViewById(R.id.cb_task);
        }

        public void bind(int position) {
            ResultModel resultModel = resultModels.get(position);
            listTv.setText(resultModel.getListsModel().getListName());
            textView.setText(resultModel.getTaskModel().getTaskName());
            if (resultModel.getTaskModel().isChecked()) {
                textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                checkBox.setChecked(true);
            }
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                boolean b = listener.onCheck(resultModel.getTaskModel().getTaskName(), isChecked , resultModel.getListsModel().getListName());
                if (!b) {
                    textView.setPaintFlags(0);
                }
            });
        }
    }

    public interface OnCheckBoxListener {
        boolean onCheck(String name, boolean b , String lisName);
    }
}
