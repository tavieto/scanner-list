package dev.tavieto.scannerlist.feature.task;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import dev.tavieto.scannerlist.R;
import dev.tavieto.scannerlist.model.Task;

class TaskAdapter extends ListAdapter<Task, TaskViewHolder> {
    private final OnItemCheckedChange callback;

    public TaskAdapter(OnItemCheckedChange callback) {
        super(DIFF_CALLBACK);
        this.callback = callback;
    }

    private static final DiffUtil.ItemCallback<Task> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Task>() {
                @Override
                public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
                    return false;
                }

                @Override
                public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
                    return false;
                }
            };

    public interface OnItemCheckedChange {
        void setOnCheckedChangeListener(int index, Boolean isChecked);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bind(position, this.getItem(position), callback);
    }
}
