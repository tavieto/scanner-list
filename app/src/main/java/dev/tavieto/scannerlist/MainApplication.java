package dev.tavieto.scannerlist;

import android.app.Application;

public class MainApplication extends Application {
    public ApplicationGraph appComponent = DaggerApplicationGraph.create();
}
