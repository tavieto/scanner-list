package dev.tavieto.scannerlist.feature.task;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.tavieto.scannerlist.databinding.FragmentTaskBinding;
import dev.tavieto.scannerlist.entity.Task;

public class TaskFragment extends Fragment {
    private FragmentTaskBinding binding = null;
    private TaskFragmentArgs args;
    private TaskAdapter adapter;
    private final List<Task> tasks = new ArrayList<>();
    private static Gson gson = null;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        gson = new Gson();
        binding = FragmentTaskBinding.inflate(inflater, container, false);
        assert getArguments() != null;
        args = TaskFragmentArgs.fromBundle(getArguments());
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        String listId = args.getListId();
        setupRecycler(listId);
        getTasks(listId);
        setupButton(listId);
    }

    private void setupButton(String listId) {
        binding.btnAdd.setOnClickListener(v -> {
            addTask(listId);
        });
    }

    private void getTasks(String listId) {
        db.collection("list")
                .document(listId)
                .get()
                .addOnSuccessListener(data -> {
                    String title = data.getString("title");
                    String tasksJson = data.getString("tasks");
                    if (tasksJson != null) {
                        ArrayList<Task> aux = new ArrayList<>();
                        for (Task item : gson.fromJson(tasksJson, Task[].class)) {
                            aux.add(item);
                        }
                        tasks.clear();
                        tasks.addAll(aux);
                        adapter.submitList(tasks);
                        adapter.notifyDataSetChanged();
                    }
                    binding.txtCode.setText(title);
                });
    }

    private void updateFirebase(String listId) {
        String newJson = gson.toJson(tasks);
        Map<String, String> data = new HashMap<>();
        data.put("tasks", newJson);
        db.collection("list")
                .document(listId)
                .set(data)
                .addOnCompleteListener(nothing -> {
                    getTasks(listId);
                });
    }

    private void addTask(String listId) {
        Editable textEditable = binding.edtTask.getText();
        if (textEditable == null) {
            Toast.makeText(requireContext(), "Type something", Toast.LENGTH_SHORT).show();
        } else {
            int id = getNextId(tasks);
            Task task = new Task(id, textEditable.toString(), false);
            tasks.add(task);
            updateFirebase(listId);
            binding.edtTask.setText("");
        }
    }

    private int getNextId(List<Task> tasks) {
        int biggestId = 0;
        if (tasks != null && tasks.size() > 0) {
            biggestId = tasks.get(0).getId();
            for (Task task : tasks) {
                int currentId = task.getId();
                if (currentId > biggestId) {
                    biggestId = task.getId();
                }
            }
        }
        return biggestId + 1;
    }

    private void setupRecycler(String listId) {
        TaskAdapter.OnItemCheckedChange callback = (index, isChecked) -> {
            Task task = tasks.get(index);
            task.setDone(isChecked);
            tasks.set(index, task);
            updateFirebase(listId);
            adapter.notifyItemChanged(index);
        };
        adapter = new TaskAdapter(callback);
        binding.recycler.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        gson = null;
    }
}
