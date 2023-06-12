package dev.tavieto.scannerlist.feature.di;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class ViewModelFactory implements ViewModelProvider.Factory {
    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModelsMap;

    @Inject
    public ViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModelsMap) {
        this.viewModelsMap = viewModelsMap;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Provider<ViewModel> viewModelProvider = viewModelsMap.get(modelClass);

        if (viewModelProvider == null) {
            throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
        }

        ViewModel viewModel = viewModelProvider.get();

        try {
            return Objects.requireNonNull(modelClass.cast(viewModel));
        } catch (ClassCastException e) {
            throw new RuntimeException("Unable to cast ViewModel to " + modelClass.getName(), e);
        }
    }
}
