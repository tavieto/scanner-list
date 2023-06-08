package dev.tavieto.scannerlist.fragments.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.Map;
import java.util.Objects;

import dev.tavieto.scannerlist.R;
import dev.tavieto.scannerlist.databinding.FragmentLoginBinding;
import dev.tavieto.scannerlist.model.Task;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding = null;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return (View) binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("list")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Gson gson = new Gson();
                            String json = Objects.requireNonNull(document.getData().get("tasks")).toString();
                            Task[] tasks = gson.fromJson(json, Task[].class);
                            Log.d("TAG", document.getId() + " => " + json);
                            for (Task listTask : tasks) {
                                Log.d("TAG", "title: " + listTask.getTitle());
                                Log.d("TAG", "isDone: " + listTask.isDone());
                            }
                            Log.d("TAG", "size: " + tasks.length);
                        }
                    } else {
                        Log.w("TAG", "Error getting documents. " + task.getException());
                    }
                });

        binding.btnNavigateScanner.setOnClickListener( view -> {
            NavDirections action = LoginFragmentDirections.actionLoginFragmentToScannerFragment();
            Navigation.findNavController(view).navigate(action);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}