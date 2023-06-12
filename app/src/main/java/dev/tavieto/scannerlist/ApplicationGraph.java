package dev.tavieto.scannerlist;

import javax.inject.Singleton;

import dagger.Component;
import dev.tavieto.scannerlist.data.remote.DataRemoteModule;
import dev.tavieto.scannerlist.feature.di.FeatureModule;
import dev.tavieto.scannerlist.feature.login.LoginFragment;
import dev.tavieto.scannerlist.feature.login.LoginViewModel;
import dev.tavieto.scannerlist.repository.RepositoryModule;

@Singleton
@Component(modules = {
        FeatureModule.class,
        RepositoryModule.class,
        DataRemoteModule.class
})
public interface ApplicationGraph {
    void injectLoginFragment(LoginFragment fragment);
    void injectLoginViewModel(LoginViewModel viewModel);
}
