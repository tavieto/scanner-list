package dev.tavieto.scannerlist.data.remote;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import javax.inject.Inject;

import dev.tavieto.scannerlist.entity.User;
import io.reactivex.Completable;
import io.reactivex.Single;

class AuthRemoteDataSourceImpl implements AuthRemoteDataSource {

    private final FirebaseAuth auth;

    @Inject
    public AuthRemoteDataSourceImpl() {
        this.auth = FirebaseAuth.getInstance();
    }

    @Override
    public Single<User> signIn(@NonNull String email, @NonNull String password) {
        return Single.create(emitter -> {
            Task<AuthResult> result = auth.signInWithEmailAndPassword(email, password);
            result.addOnSuccessListener(data -> {
                FirebaseUser firebaseUser = data.getUser();
                if (firebaseUser == null) {
                    String errMsg = "This id is not registered on Firebase Authentication";
                    emitter.onError(new IllegalArgumentException(errMsg));
                } else {
                    String emailValue;
                    String firebaseEmail = firebaseUser.getEmail();
                    if (firebaseEmail == null) {
                        emailValue = "";
                    } else {
                        emailValue = firebaseEmail;
                    }
                    emitter.onSuccess(
                            new User(firebaseUser.getUid(), emailValue, firebaseUser.getDisplayName()));
                }
            });
        });
    }

    @Override
    public Completable singOut() {
        return Completable.create(emitter -> {
            auth.signOut();
            emitter.onComplete();
        });
    }

    @Override
    public Completable signUp(@NonNull String name, @NonNull String email, @NonNull String password) {
        return Completable.create(emitter -> {
            Task<AuthResult> result = auth.createUserWithEmailAndPassword(email, password);
            result.addOnSuccessListener(data -> {
                UserProfileChangeRequest userProfileChangeRequest =
                        new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build();
                FirebaseUser user = data.getUser();
                if (user == null) {
                    emitter.onError(new RuntimeException("FirebaseUser must not be null."));
                } else {
                    Task<Void> updateResult = data.getUser().updateProfile(userProfileChangeRequest);
                    updateResult.addOnCompleteListener(empty -> {
                        emitter.onComplete();
                    });
                }
            });
        });
    }
}
