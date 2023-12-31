package dev.tavieto.scannerlist.feature.scanner;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.budiyev.android.codescanner.CodeScanner;

import org.jetbrains.annotations.NotNull;

import dev.tavieto.scannerlist.databinding.FragmentScannerBinding;

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
        requestCameraPermission();

        codeScanner = new CodeScanner(requireActivity(), binding.codeScannerView);
        codeScanner.setDecodeCallback(result -> {
            requireActivity().runOnUiThread(() -> {
                NavDirections action = ScannerFragmentDirections.actionScannerFragmentToTaskFragment(result.getText());
                Navigation.findNavController(binding.getRoot()).navigate(action);
            });
        });
        binding.btnStart.setOnClickListener(v -> codeScanner.startPreview());
    }

    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, 123);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        codeScanner.releaseResources();
    }
}
