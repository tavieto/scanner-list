package dev.tavieto.scannerlist.data.remote;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class DataRemoteModule {
    @Binds
    public abstract AuthRemoteDataSource bindAuthRemoteDataSource(AuthRemoteDataSourceImpl impl);
}
