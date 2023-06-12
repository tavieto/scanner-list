package dev.tavieto.scannerlist.feature.login;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dev.tavieto.scannerlist.repository.AuthRepository;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {
    private final CompositeDisposable compositeDisposable;
    private final AuthRepository repository;
    @Inject
    public LoginViewModel(AuthRepository repository) {
        this.repository = repository;
        this.compositeDisposable = new CompositeDisposable();
    }
    private final MutableLiveData<String> _email = new MutableLiveData<>("user@test.com");
    final LiveData<String> email = _email;
    private final MutableLiveData<String> _password = new MutableLiveData<>("123abc");
    final LiveData<String> password = _password;
    private final MutableLiveData<Boolean> _isLogged = new MutableLiveData<>(false);
    final LiveData<Boolean> isLogged = _isLogged;

    public void setEmail(String email) {
        this._email.postValue(email);
    }

    public void setPassword(String password) {
        this._password.postValue(password);
    }

    public void singIn() {
        String emailValue = "";
        String passwordValue = "";

        if (_email.getValue() != null) {
            emailValue = _email.getValue();
        }
        if (_password.getValue() != null) {
            passwordValue = _password.getValue();
        }

        Disposable loginObs = repository.login(emailValue, passwordValue)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .subscribe(result -> {
                    _isLogged.postValue(result != null);
                });
        compositeDisposable.add(loginObs);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
