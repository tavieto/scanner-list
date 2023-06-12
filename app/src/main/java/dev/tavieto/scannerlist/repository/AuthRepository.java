package dev.tavieto.scannerlist.repository;

import androidx.annotation.NonNull;

import dev.tavieto.scannerlist.entity.User;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface AuthRepository {
    Single<User> login(@NonNull String email, @NonNull String password);
    Completable logout();
}
