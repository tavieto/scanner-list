package dev.tavieto.scannerlist.data.remote;

import androidx.annotation.NonNull;

import dev.tavieto.scannerlist.entity.User;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface AuthRemoteDataSource {
    Single<User> signIn(@NonNull String email, @NonNull String password);
    Completable singOut();
    Completable signUp(@NonNull String name, @NonNull String email, @NonNull String password);
}
