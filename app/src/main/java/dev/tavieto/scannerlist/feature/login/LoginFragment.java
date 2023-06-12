package dev.tavieto.scannerlist.feature.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import javax.inject.Inject;

import dev.tavieto.scannerlist.MainApplication;
import dev.tavieto.scannerlist.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private LoginViewModel viewModel;
    private FragmentLoginBinding binding = null;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication application = (MainApplication) requireActivity().getApplication();
        application.appComponent.injectLoginFragment(this);
        ViewModelProvider viewModelProvider = new ViewModelProvider(this, viewModelFactory);
        viewModel = viewModelProvider.get(LoginViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        setupTextField();
        setupButton();
        setupObservers();
    }

    private void setupTextField() {
        TextWatcher emailWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.setEmail(s.toString());
            }
        };
        TextWatcher passwordWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.setPassword(s.toString());
            }
        };
        binding.tfEmail.addTextChangedListener(emailWatcher);
    }

    private void setupButton() {
        binding.btnLogin.setOnClickListener(view -> {
            binding.progressCircular.setVisibility(View.VISIBLE);
            viewModel.singIn();
        });
    }

    private void setupObservers() {
        Observer<String> emailObserver = email -> {
            binding.tfEmail.setText(email);
        };
        Observer<String> passwordObserver = password -> {
            binding.tfPassword.setText(password);
        };
        Observer<Boolean> isLoggedObserver = isLogged -> {
            binding.progressCircular.setVisibility(View.INVISIBLE);
            if (isLogged) {
                NavDirections direction =
                        LoginFragmentDirections.actionLoginFragmentToScannerFragment();
                Navigation.findNavController(binding.getRoot()).navigate(direction);
            }
        };
        viewModel.email.observe(this, emailObserver);
        viewModel.password.observe(this, passwordObserver);
        viewModel.isLogged.observe(this, isLoggedObserver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}