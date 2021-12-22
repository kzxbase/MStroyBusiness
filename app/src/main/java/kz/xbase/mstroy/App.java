package kz.xbase.mstroy;

import android.app.Application;
import android.content.Context;

import kz.xbase.mstroy.network.APIBuilder;


public class App extends Application {

    public static APIBuilder api;
    public final static int interfal = 5000;
    protected static App instance;
    public static volatile Context applicationContext;
    private static volatile boolean applicationInited = false;
    public static App getInstance() {
        return instance;
    }
    public static String mapKey = "AIzaSyDZMqir18KW_23860YO0KTQiDQIzhy-pP4";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        applicationContext = getApplicationContext();
        initApi();
        postInitApp();
    }


    private void initApi() {
        api = new APIBuilder(applicationContext);
    }

    public void postInitApp() {
        if (applicationInited) {
            return;
        }
        applicationInited = true;
    }

}
