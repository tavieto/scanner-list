package dev.tavieto.scannerlist.fragments.scanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.budiyev.android.codescanner.CodeScanner;

import org.jetbrains.annotations.NotNull;

import dev.tavieto.scannerlist.databinding.FragmentScannerBinding;
import dev.tavieto.scannerlist.fragments.scanner.ScannerFragmentDirections;

public class ScannerFragment extends Fragment {
    private FragmentScannerBinding binding = null;
    private CodeScanner codeScanner;

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentScannerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        codeScanner = new CodeScanner(requireActivity(), binding.codeScannerView);
        codeScanner.setDecodeCallback(result -> {
            requireActivity().runOnUiThread(() -> {
                NavDirections action = ScannerFragmentDirections.actionScannerFragmentToTaskFragment(result.getText());
                Navigation.findNavController(binding.getRoot()).navigate(action);
            });
        });
        binding.codeScannerView.setOnClickListener(v -> codeScanner.startPreview());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        codeScanner.releaseResources();
    }
}
