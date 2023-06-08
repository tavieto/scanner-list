package dev.tavieto.scannerlist.fragments.task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import dev.tavieto.scannerlist.databinding.FragmentTaskBinding;

public class TaskFragment extends Fragment {
    private FragmentTaskBinding binding = null;
    private TaskFragmentArgs args;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentTaskBinding.inflate(inflater, container, false);
        args = TaskFragmentArgs.fromBundle(getArguments());
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.txtCode.setText(args.getListId());
        Toast.makeText(requireActivity(), args.getListId(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
