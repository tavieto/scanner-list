package dev.tavieto.scannerlist.repository;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import dev.tavieto.scannerlist.data.remote.AuthRemoteDataSource;
import dev.tavieto.scannerlist.entity.User;
import io.reactivex.Completable;
import io.reactivex.Single;

class AuthRepositoryImpl implements AuthRepository {

    private final AuthRemoteDataSource remote;

    @Inject
    public AuthRepositoryImpl(AuthRemoteDataSource remote) {
        this.remote = remote;
    }

    @Override
    public Single<User> login(@NonNull String email, @NonNull String password) {
        return remote.signIn(email, password);
    }

    @Override
    public Completable logout() {
        return remote.singOut();
    }
}
