package dev.tavieto.scannerlist.repository;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class RepositoryModule {
    @Binds
    public abstract AuthRepository bindAuthRepository(AuthRepositoryImpl impl);
}
