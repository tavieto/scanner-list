package dev.tavieto.scannerlist.feature.task;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dev.tavieto.scannerlist.databinding.ItemTaskBinding;
import dev.tavieto.scannerlist.model.Task;

class TaskViewHolder extends RecyclerView.ViewHolder {
    private final ItemTaskBinding binding;

    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = ItemTaskBinding.bind(itemView);
    }

    public void bind(int index, Task task, TaskAdapter.OnItemCheckedChange callback) {
        binding.checkbox.setText(task.getTitle());
        binding.checkbox.setChecked(task.isDone());
        binding.checkbox.setOnClickListener(buttonView -> {
            callback.setOnCheckedChangeListener(index, !task.isDone());
        });
    }
}